package by.training.dao;

import lombok.extern.log4j.Log4j;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.Objects;
import java.util.Properties;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Log4j
public class ConnectionPoolImpl implements ConnectionPool {
    private static final Lock SINGLETON_LOCK = new ReentrantLock();
    private static ConnectionPoolImpl instance;

    private final Lock CONNECTION_LOCK = new ReentrantLock();
    private Condition emptyPool = CONNECTION_LOCK.newCondition();
    private Queue<Connection> availableConnections = new LinkedList<>();
    private Queue<Connection> takenConnections = new LinkedList<>();
    private Driver driver;

    private ConnectionPoolImpl() {
        Properties property = new Properties();
        try {
            FileReader propertyFile = new FileReader(new File(Objects.requireNonNull(this.getClass().getClassLoader()
                    .getResource("datasource.properties")).getFile()));
            property.load(propertyFile);
            String driverClassName = property.getProperty("dataSource.driver");
            String url = property.getProperty("dataSource.url");
            String user = property.getProperty("dataSource.user");
            String password = property.getProperty("dataSource.password");
            int poolSize = Integer.parseInt(property.getProperty("dataSource.pool_size"));
            Class driverClass = Class.forName(driverClassName);
            driver = (Driver) driverClass.newInstance();
            DriverManager.registerDriver(driver);
            for (int i = 0; i < poolSize; i++) {
                Connection connection = DriverManager.getConnection(url, user, password);
                availableConnections.add(connection);
            }
        } catch (SQLException | IOException | ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            throw new ConnectionPoolException("Exception was thrown during creation of connection pool: " + e.getMessage(), e);
        }
    }

    public static ConnectionPoolImpl getInstance() {
        if (instance == null) {
            SINGLETON_LOCK.lock();
            try {
                if (instance == null) {
                    instance = new ConnectionPoolImpl();
                }
            } finally {
                SINGLETON_LOCK.unlock();
            }
        }
        return instance;
    }

    @Override
    public Connection getConnection() {
        CONNECTION_LOCK.lock();
        try {
            while (availableConnections.isEmpty()) {
                emptyPool.await();
            }
            Connection connection = availableConnections.poll();
            takenConnections.add(connection);
            return createProxyConnection(connection);
        } catch (InterruptedException e) {
            log.error("Failed to get connection: " + e.getMessage());
            throw new ConnectionPoolException(e.getMessage());
        } finally {
            CONNECTION_LOCK.unlock();
        }
    }

    @Override
    public void releaseConnection(Connection connection) {
        CONNECTION_LOCK.lock();
        try {
            if (!takenConnections.contains(connection)) {
                throw new ConnectionPoolException("Attempt to release connection from other connection pool!");
            }
            takenConnections.remove(connection);
            availableConnections.add(connection);
            emptyPool.signal();
        } finally {
            CONNECTION_LOCK.unlock();
        }
    }

    @Override
    public void close() {
        try {
            for (Connection connection : availableConnections) {
                connection.close();
            }
            for (Connection connection : takenConnections) {
                connection.close();
            }
            instance = null;
            DriverManager.deregisterDriver(driver);
        } catch (SQLException e) {
            throw new ConnectionPoolException("Failed to close connection pool", e);
        }
    }

    private Connection createProxyConnection(Connection connection) {
        return (Connection) Proxy.newProxyInstance(connection.getClass().getClassLoader(),
                new Class[]{Connection.class},
                (proxy, method, args) -> {
                    if ("close".equals(method.getName())) {
                        releaseConnection(connection);
                        return null;
                    } else if ("hashCode".equals(method.getName())) {
                        return connection.hashCode();
                    } else {
                        return method.invoke(connection, args);
                    }
                });
    }
}

package by.training.dao;

import by.training.core.Bean;
import lombok.extern.log4j.Log4j;

import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.sql.SQLException;

@Log4j
@Bean
public class TransactionManagerImpl implements TransactionManager {
    private DataSource dataSource;
    private ThreadLocal<Connection> localConnection = new ThreadLocal<>();

    public TransactionManagerImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void beginTransaction() throws SQLException {
        if (localConnection.get() != null) {
            Connection connection = dataSource.getConnection();
            connection.setAutoCommit(false);
            localConnection.set(connection);
        } else {
            log.warn("Transaction already started");
        }
    }

    @Override
    public void commitTransaction() throws SQLException {
        Connection connection = localConnection.get();
        if (connection != null) {
            connection.commit();
            connection.setAutoCommit(true);
            connection.close();
        }
        localConnection.remove();
    }

    @Override
    public void rollbackTransaction() throws SQLException {
        Connection connection = localConnection.get();
        if (connection != null) {
            connection.rollback();
            connection.setAutoCommit(true);
            connection.close();
        }
        localConnection.remove();
    }

    @Override
    public Connection getConnection() throws SQLException {
        if (localConnection.get() != null) {
            return (Connection) Proxy.newProxyInstance(getClass().getClassLoader(), new Class[]{Connection.class},
                    (proxy, method, args) -> {
                        if (method.getName().equals("close")) {
                            return null;
                        } else {
                            Connection realConnection = localConnection.get();
                            return method.invoke(realConnection, args);
                        }
                    });
        } else {
            return dataSource.getConnection();
        }
    }
}
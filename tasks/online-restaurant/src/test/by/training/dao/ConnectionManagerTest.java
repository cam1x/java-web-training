package by.training.dao;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.sql.Connection;
import java.sql.SQLException;

@RunWith(JUnit4.class)
public class ConnectionManagerTest {
    private static ConnectionPool connectionPool;
    private ConnectionManager connectionManager;

    @BeforeClass
    public static void initConnectionPool() {
        connectionPool = ConnectionPoolImpl.getInstance();
    }

    @Before
    public void prepare() {
        DataSource dataSource = new DataSourceImpl();
        TransactionManager transactionManager = new TransactionManagerImpl(dataSource);
        connectionManager = new ConnectionManagerImpl(transactionManager, dataSource);
    }

    @Test
    public void shouldGetConnectionFromConnectionPool() throws SQLException {
        Connection connection = connectionManager.getConnection();
        Assert.assertNotNull(connection);
        connection.close();
    }

    @AfterClass
    public static void closeConnectionPool(){
        connectionPool.close();
    }
}

package by.training.dao;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@RunWith(JUnit4.class)
public class ConnectionPoolTest {
    private ConnectionPool connectionPool;

    @Before
    public void prepare() {
        connectionPool = ConnectionPoolImpl.getInstance();
    }

    @Test
    public void shouldGetConnection() {
        Connection connection = connectionPool.getConnection();
        Assert.assertNotNull(connection);
    }

    @Test(expected = ConnectionPoolException.class)
    public void shouldThrowExceptionAfterReleasingWrongConnection() throws SQLException {
        Connection connection =
                DriverManager.getConnection("jdbc:hsqldb:mem:restaurant_db", "test", "test");
        connectionPool.releaseConnection(connection);
        Assert.fail("Throw ConnectionPoolException");
    }

    @After
    public void destroy() {
        connectionPool.close();
    }
}

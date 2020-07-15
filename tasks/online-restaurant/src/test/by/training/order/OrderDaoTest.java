package by.training.order;

import by.training.dao.ConnectionManager;
import by.training.dao.DaoException;
import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mockito;

import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

@RunWith(JUnit4.class)
public class OrderDaoTest {
    private static Connection connection;
    private OrderDao orderDao;

    @BeforeClass
    public static void initConnection() throws SQLException {
        connection =
                DriverManager.getConnection("jdbc:hsqldb:mem:restaurant_db", "test", "test");
    }

    @AfterClass
    public static void closeConnection() throws SQLException {
        connection.close();
    }

    @Before
    public void createTable() throws SQLException {
        ConnectionManager mockConnectionManager = Mockito.mock(ConnectionManager.class);
        orderDao = new OrderDaoImpl(mockConnectionManager);
        Mockito.when(mockConnectionManager.getConnection()).thenReturn(createConnectionProxy(connection));
        String createTable = "CREATE TABLE food_order (\n" +
                "  order_id BIGINT GENERATED BY DEFAULT AS IDENTITY(START WITH 1, INCREMENT BY 1) PRIMARY KEY,\n" +
                "  order_date timestamp NOT NULL,\n" +
                "  booking_date timestamp NOT NULL,\n" +
                "  order_status varchar(150),\n" +
                "  customer_id BIGINT NOT NULL,\n" +
                ")";
        executeSql(createTable);

        String insertOrder1 = "insert into food_order (order_date, booking_date, order_status, customer_id)" +
                "values(CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'Processing', 15)";
        executeSql(insertOrder1);

        String insertOrder2 = "insert into food_order (order_date, booking_date, order_status, customer_id)" +
                "values(CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'Processing', 21)";
        executeSql(insertOrder2);

        String insertOrder3 = "insert into food_order (order_date, booking_date, order_status, customer_id)" +
                "values(CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'Processing', 15)";
        executeSql(insertOrder3);
    }

    private void executeSql(String sql) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.executeUpdate();
        statement.close();
    }

    @Test
    public void shouldInsert() throws DaoException {
        List<OrderDto> beforeInsert = orderDao.findAll();
        OrderDto orderDto = OrderDto.builder()
                .bookingDate(new Date())
                .orderDate(new Date())
                .status("Processing")
                .customerId(98)
                .build();
        orderDao.save(orderDto);
        List<OrderDto> afterInsert = orderDao.findAll();
        Assert.assertEquals(1, afterInsert.size() - beforeInsert.size());
    }

    @Test
    public void shouldRemove() throws DaoException {
        List<OrderDto> beforeRemove = orderDao.findAll();
        orderDao.delete(1L);
        List<OrderDto> afterRemove = orderDao.findAll();
        Assert.assertEquals(1, beforeRemove.size() - afterRemove.size());
    }

    @Test
    public void shouldGetById() throws DaoException {
        OrderDto orderDto = orderDao.getById(1L);
        Assert.assertNotNull(orderDto);
    }

    @Test(expected = OrderDaoException.class)
    public void shouldThrowExceptionAfterGetById() throws DaoException {
        OrderDto orderDto = orderDao.getById(100L);
        Assert.fail("Should throw exception cause wrong id");
    }

    @Test
    public void shouldFindAllCustomerOrders() throws OrderDaoException {
        List<OrderDto> customerOrders = orderDao.findUserOrders(15L);
        Assert.assertEquals(2, customerOrders.size());
    }

    private Connection createConnectionProxy(Connection realConnection) {
        return (Connection) Proxy.newProxyInstance(getClass().getClassLoader(), new Class[]{Connection.class},
                (proxy, method, args) -> {
                    if (method.getName().equals("close")) {
                        return null;
                    } else {
                        return method.invoke(realConnection, args);
                    }
                });
    }

    @After
    public void dropTable() throws SQLException {
        String sql = "drop table food_order";
        executeSql(sql);
    }
}

package by.training.discount;

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
import java.util.List;

@RunWith(JUnit4.class)
public class DiscountDaoTest {
    private static Connection connection;
    private DiscountDao discountDao;

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
        discountDao = new DiscountDaoImpl(mockConnectionManager);
        Mockito.when(mockConnectionManager.getConnection()).thenReturn(createConnectionProxy(connection));
        String createTable = "CREATE TABLE user_discount (\n" +
                "  discount_id BIGINT GENERATED BY DEFAULT AS IDENTITY(START WITH 1, INCREMENT BY 1) PRIMARY KEY,\n" +
                "  discount_amount BIGINT NOT NULL,\n" +
                ")";
        executeSql(createTable);

        String insertDiscount1 = "insert into user_discount (discount_amount) values(10)";
        executeSql(insertDiscount1);

        String insertDiscount2 = "insert into user_discount (discount_amount) values(98)";
        executeSql(insertDiscount2);

        String insertDiscount3 = "insert into user_discount (discount_amount) values(15)";
        executeSql(insertDiscount3);

        String insertDiscount4 = "insert into user_discount (discount_amount) values(15)";
        executeSql(insertDiscount4);
    }

    @Test
    public void shouldInsert() throws DaoException {
        List<DiscountDto> beforeInsert = discountDao.findAll();
        DiscountDto discountDto = DiscountDto.builder()
                .amount(67)
                .build();
        discountDao.save(discountDto);
        List<DiscountDto> afterInsert = discountDao.findAll();
        Assert.assertEquals(1, afterInsert.size() - beforeInsert.size());
    }

    @Test
    public void shouldRemove() throws DaoException {
        List<DiscountDto> beforeRemove = discountDao.findAll();
        discountDao.delete(1L);
        List<DiscountDto> afterRemove = discountDao.findAll();
        Assert.assertEquals(1, beforeRemove.size() - afterRemove.size());
    }

    @Test
    public void shouldGetByID() throws DaoException {
        DiscountDto discountDto = discountDao.getById(2L);
        Assert.assertNotNull(discountDto);
    }

    @Test(expected = DiscountDaoException.class)
    public void shouldThrowExceptionAfterGetById() throws DaoException {
        DiscountDto discountDto = discountDao.getById(200L);
        Assert.fail("Should throw exception cause wrong id");
    }

    private void executeSql(String sql) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.executeUpdate();
        statement.close();
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
        String sql = "drop table user_discount";
        executeSql(sql);
    }
}

package by.training.user;

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
import java.util.Optional;

@RunWith(JUnit4.class)
public class UserDaoTest {
    private static Connection connection;
    private UserDao userDao;

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
        userDao = new UserDaoImpl(mockConnectionManager);
        Mockito.when(mockConnectionManager.getConnection()).thenReturn(createConnectionProxy(connection));
        String createTable = "CREATE TABLE user_account (\n" +
                "  user_id BIGINT GENERATED BY DEFAULT AS IDENTITY(START WITH 1, INCREMENT BY 1) PRIMARY KEY,\n" +
                "  login varchar(150) NOT NULL,\n" +
                "  password varchar(150) NOT NULL,\n" +
                "  is_locked tinyint,\n" +
                "  discount_id BIGINT,\n" +
                "  user_avatar varchar(150),\n" +
                ")";
        executeSql(createTable);

        String insertUser1 = "insert into user_account (login, password) values ('max', '123')";
        executeSql(insertUser1);

        String insertUser2 = "insert into user_account (login, password) values ('vasya', '321')";
        executeSql(insertUser2);

        String insertUser3 = "insert into user_account (login, password) values ('tolya', 'qwerty')";
        executeSql(insertUser3);

        String insertUser4 = "insert into user_account (login, password) values ('john', 'qqq')";
        executeSql(insertUser4);
    }

    @Test
    public void shouldInsertUser() throws DaoException {
        List<UserDto> beforeInsert = userDao.findAll();
        UserDto userDto = UserDto.builder()
                .login("mike")
                .password("usa")
                .build();
        userDao.save(userDto);
        List<UserDto> afterInsert = userDao.findAll();
        Assert.assertEquals(1, afterInsert.size() - beforeInsert.size());
    }

    @Test
    public void shouldRemove() throws DaoException {
        List<UserDto> beforeRemove = userDao.findAll();
        userDao.delete(1L);
        List<UserDto> afterRemove = userDao.findAll();
        Assert.assertEquals(1, beforeRemove.size() - afterRemove.size());
    }

    @Test
    public void shouldFindByLogin() throws UserDaoException {
        Optional<UserDto> foundUser = userDao.findByLogin("max");
        Assert.assertTrue(foundUser.isPresent());
    }

    @Test
    public void shouldNotFindByLogin() throws UserDaoException {
        Optional<UserDto> foundUser = userDao.findByLogin("maxx");
        Assert.assertFalse(foundUser.isPresent());
    }

    @Test
    public void shouldNotifyThatLoginIsTaken() throws UserDaoException {
        boolean isTaken = userDao.isLoginTaken("john");
        Assert.assertTrue(isTaken);
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
        String sql = "drop table user_account";
        executeSql(sql);
    }
}

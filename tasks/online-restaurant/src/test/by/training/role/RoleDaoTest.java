package by.training.role;

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
public class RoleDaoTest {
    private static Connection connection;
    private RoleDao roleDao;

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
        roleDao = new RoleDaoImpl(mockConnectionManager);
        Mockito.when(mockConnectionManager.getConnection()).thenReturn(createConnectionProxy(connection));
        String createTable1 = "CREATE TABLE user_role (\n" +
                "  role_id BIGINT GENERATED BY DEFAULT AS IDENTITY(START WITH 1, INCREMENT BY 1) PRIMARY KEY,\n" +
                "  role varchar(150) NOT NULL,\n" +
                "  default_role tinyint,\n" +
                ")";
        executeSql(createTable1);

        String createTable2 = "CREATE TABLE user_role_relation (\n" +
                "  user_id BIGINT NOT NULL,\n" +
                "  role_id BIGINT NOT NULL,\n" +
                ")";
        executeSql(createTable2);

        String insertRole1 = "insert into user_role (role, default_role) values ('admin', 0)";
        executeSql(insertRole1);

        String insertRole2 = "insert into user_role (role, default_role) values ('manager', 0)";
        executeSql(insertRole2);

        String insertRole3 = "insert into user_role (role, default_role) values ('customer', 1)";
        executeSql(insertRole3);

        String insertRelation1 = "insert into user_role_relation (user_id, role_id) values (15, 1)";
        executeSql(insertRelation1);

        String insertRelation2 = "insert into user_role_relation (user_id, role_id) values (15, 2)";
        executeSql(insertRelation2);

        String insertRelation3 = "insert into user_role_relation (user_id, role_id) values (21, 3)";
        executeSql(insertRelation3);
    }

    @Test
    public void shouldInsertRole() throws DaoException {
        List<RoleDto> beforeInsert = roleDao.findAll();
        RoleDto roleDto = RoleDto.builder()
                .role("customRole")
                .defaultRole(false)
                .build();
        roleDao.save(roleDto);
        List<RoleDto> afterInsert = roleDao.findAll();
        Assert.assertEquals(1, afterInsert.size() - beforeInsert.size());
    }

    @Test
    public void shouldRemoveRole() throws DaoException {
        List<RoleDto> beforeRemove = roleDao.findAll();
        roleDao.delete(1L);
        List<RoleDto> afterRemove = roleDao.findAll();
        Assert.assertEquals(1, beforeRemove.size() - afterRemove.size());
    }

    @Test
    public void shouldFindAllUserRoles() throws RoleDaoException {
        List<RoleDto> userRoles = roleDao.findUserRoles(15L);
        Assert.assertEquals(2, userRoles.size());
    }

    @Test
    public void shouldAssignUserRole() throws DaoException {
        List<RoleDto> beforeInsert = roleDao.findUserRoles(10L);
        roleDao.assignRole(10L, 1L);
        List<RoleDto> afterInsert = roleDao.findUserRoles(10L);
        Assert.assertEquals(1, afterInsert.size() - beforeInsert.size());
    }

    @Test
    public void shouldDeleteUserRole() throws RoleDaoException {
        List<RoleDto> beforeRemove = roleDao.findUserRoles(15L);
        roleDao.deleteUserRole(15L, 1L);
        List<RoleDto> afterRemove = roleDao.findUserRoles(15L);
        Assert.assertEquals(1, beforeRemove.size() - afterRemove.size());
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
        String sql1 = "drop table user_role";
        executeSql(sql1);

        String sql2 = "drop table user_role_relation";
        executeSql(sql2);
    }
}

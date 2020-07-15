package by.training.dao;

import by.training.core.Bean;

import java.sql.Connection;

@Bean
public class DataSourceImpl implements DataSource {
    private final ConnectionPool connectionPool;

    public DataSourceImpl() {
        connectionPool = ConnectionPoolImpl.getInstance();
    }

    @Override
    public Connection getConnection() {
        return connectionPool.getConnection();
    }

    @Override
    public void close() {
        connectionPool.close();
    }
}
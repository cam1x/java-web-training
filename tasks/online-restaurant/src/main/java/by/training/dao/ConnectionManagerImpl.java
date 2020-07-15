package by.training.dao;

import by.training.core.Bean;

import java.sql.Connection;
import java.sql.SQLException;

@Bean
public class ConnectionManagerImpl implements ConnectionManager {
    private TransactionManager transactionManager;
    private DataSource dataSource;

    public ConnectionManagerImpl(TransactionManager transactionManager, DataSource dataSource) {
        this.transactionManager = transactionManager;
        this.dataSource = dataSource;
    }

    @Override
    public Connection getConnection() throws SQLException {
        Connection managerConnection = transactionManager.getConnection();
        return managerConnection != null ? managerConnection : dataSource.getConnection();
    }
}
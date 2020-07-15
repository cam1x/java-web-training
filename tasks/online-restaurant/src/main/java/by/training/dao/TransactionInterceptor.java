package by.training.dao;

import by.training.core.Bean;
import by.training.core.BeanInterceptor;
import by.training.core.Interceptor;

import java.lang.reflect.Method;
import java.sql.SQLException;

@Bean
@Interceptor(clazz = TransactionSupport.class)
public class TransactionInterceptor implements BeanInterceptor {
    private TransactionManager transactionManager;

    public TransactionInterceptor(TransactionManager transactionManager) {
        this.transactionManager = transactionManager;
    }

    @Override
    public void before(Object proxy, Object service, Method method, Object[] args) {
        if (method.getAnnotation(Transactional.class) == null) {
            return;
        }
        try {
            transactionManager.beginTransaction();
        } catch (SQLException e) {
            throw new TransactionException("Failed to begin transaction", e);
        }
    }

    @Override
    public void success(Object proxy, Object service, Method method, Object[] args) {
        if (method.getAnnotation(Transactional.class) == null) {
            return;
        }
        try {
            transactionManager.commitTransaction();
        } catch (SQLException e) {
            throw new TransactionException("Failed to commit transaction", e);
        }
    }

    @Override
    public void fail(Object proxy, Object service, Method method, Object[] args) {
        if (method.getAnnotation(Transactional.class) == null) {
            return;
        }
        try {
            transactionManager.rollbackTransaction();
        } catch (SQLException e) {
            throw new TransactionException("Failed to rollback transaction", e);
        }
    }
}
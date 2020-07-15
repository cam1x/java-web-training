package by.training.order;

import by.training.dao.DaoException;

public class OrderDaoException extends DaoException {
    public OrderDaoException(String message) {
        super(message);
    }

    public OrderDaoException(String message, Throwable cause) {
        super(message, cause);
    }

    public OrderDaoException(Throwable cause) {
        super(cause);
    }

    public OrderDaoException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

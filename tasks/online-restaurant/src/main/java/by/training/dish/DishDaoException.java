package by.training.dish;

import by.training.dao.DaoException;

public class DishDaoException extends DaoException {
    public DishDaoException(String message) {
        super(message);
    }

    public DishDaoException(String message, Throwable cause) {
        super(message, cause);
    }

    public DishDaoException(Throwable cause) {
        super(cause);
    }

    public DishDaoException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

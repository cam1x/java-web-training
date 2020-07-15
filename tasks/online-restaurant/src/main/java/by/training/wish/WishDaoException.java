package by.training.wish;

import by.training.dao.DaoException;

public class WishDaoException extends DaoException {
    public WishDaoException(String message) {
        super(message);
    }

    public WishDaoException(String message, Throwable cause) {
        super(message, cause);
    }

    public WishDaoException(Throwable cause) {
        super(cause);
    }

    public WishDaoException(String message, Throwable cause,
                            boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

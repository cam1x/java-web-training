package by.training.user;

import by.training.dao.DaoException;

public class UserDaoException extends DaoException {
    public UserDaoException(String message) {
        super(message);
    }

    public UserDaoException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserDaoException(Throwable cause) {
        super(cause);
    }

    public UserDaoException(String message, Throwable cause,
                            boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

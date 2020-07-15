package by.training.role;

import by.training.dao.DaoException;

public class RoleDaoException extends DaoException {
    public RoleDaoException(String message) {
        super(message);
    }

    public RoleDaoException(String message, Throwable cause) {
        super(message, cause);
    }

    public RoleDaoException(Throwable cause) {
        super(cause);
    }

    public RoleDaoException(String message, Throwable cause,
                            boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

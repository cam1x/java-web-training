package by.training.contact;

import by.training.dao.DaoException;

public class ContactDaoException extends DaoException {
    public ContactDaoException(String message) {
        super(message);
    }

    public ContactDaoException(String message, Throwable cause) {
        super(message, cause);
    }

    public ContactDaoException(Throwable cause) {
        super(cause);
    }

    public ContactDaoException(String message, Throwable cause,
                               boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

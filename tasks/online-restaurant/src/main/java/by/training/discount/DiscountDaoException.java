package by.training.discount;

import by.training.dao.DaoException;

public class DiscountDaoException extends DaoException {
    public DiscountDaoException(String message) {
        super(message);
    }

    public DiscountDaoException(String message, Throwable cause) {
        super(message, cause);
    }

    public DiscountDaoException(Throwable cause) {
        super(cause);
    }

    public DiscountDaoException(String message, Throwable cause,
                                boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

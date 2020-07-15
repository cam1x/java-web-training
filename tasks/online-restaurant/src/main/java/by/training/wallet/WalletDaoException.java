package by.training.wallet;

import by.training.dao.DaoException;

public class WalletDaoException extends DaoException {
    public WalletDaoException(String message) {
        super(message);
    }

    public WalletDaoException(String message, Throwable cause) {
        super(message, cause);
    }

    public WalletDaoException(Throwable cause) {
        super(cause);
    }

    public WalletDaoException(String message, Throwable cause,
                              boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

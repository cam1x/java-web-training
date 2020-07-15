package by.training.wallet;

public class WalletNotSavedException extends WalletServiceException {
    public WalletNotSavedException(String message) {
        super(message);
    }

    public WalletNotSavedException(String message, Throwable cause) {
        super(message, cause);
    }

    public WalletNotSavedException(Throwable cause) {
        super(cause);
    }

    public WalletNotSavedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

package by.training.wallet;

public class WalletNotDeletedException extends WalletServiceException {
    public WalletNotDeletedException(String message) {
        super(message);
    }

    public WalletNotDeletedException(String message, Throwable cause) {
        super(message, cause);
    }

    public WalletNotDeletedException(Throwable cause) {
        super(cause);
    }

    public WalletNotDeletedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

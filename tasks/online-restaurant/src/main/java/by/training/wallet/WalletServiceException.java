package by.training.wallet;

public class WalletServiceException extends Exception {
    public WalletServiceException(String message) {
        super(message);
    }

    public WalletServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public WalletServiceException(Throwable cause) {
        super(cause);
    }

    public WalletServiceException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

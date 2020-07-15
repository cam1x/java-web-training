package by.training.wallet;

public class WalletNotUpdated extends WalletServiceException {
    public WalletNotUpdated(String message) {
        super(message);
    }

    public WalletNotUpdated(String message, Throwable cause) {
        super(message, cause);
    }

    public WalletNotUpdated(Throwable cause) {
        super(cause);
    }

    public WalletNotUpdated(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

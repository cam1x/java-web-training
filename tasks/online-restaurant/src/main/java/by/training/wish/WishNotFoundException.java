package by.training.wish;

public class WishNotFoundException extends WishServiceException {
    public WishNotFoundException(String message) {
        super(message);
    }

    public WishNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public WishNotFoundException(Throwable cause) {
        super(cause);
    }

    public WishNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

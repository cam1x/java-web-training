package by.training.wish;

public class WishNotDeletedException extends WishServiceException {
    public WishNotDeletedException(String message) {
        super(message);
    }

    public WishNotDeletedException(String message, Throwable cause) {
        super(message, cause);
    }

    public WishNotDeletedException(Throwable cause) {
        super(cause);
    }

    public WishNotDeletedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

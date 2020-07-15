package by.training.wish;

public class WishNotSavedException extends WishServiceException {
    public WishNotSavedException(String message) {
        super(message);
    }

    public WishNotSavedException(String message, Throwable cause) {
        super(message, cause);
    }

    public WishNotSavedException(Throwable cause) {
        super(cause);
    }

    public WishNotSavedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

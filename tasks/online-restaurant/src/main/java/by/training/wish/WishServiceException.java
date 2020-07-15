package by.training.wish;

public class WishServiceException extends Exception {
    public WishServiceException(String message) {
        super(message);
    }

    public WishServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public WishServiceException(Throwable cause) {
        super(cause);
    }

    public WishServiceException(String message, Throwable cause,
                                boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

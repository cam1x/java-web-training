package by.training.dish;

public class DishNotDeletedException extends DishServiceException {
    public DishNotDeletedException(String message) {
        super(message);
    }

    public DishNotDeletedException(String message, Throwable cause) {
        super(message, cause);
    }

    public DishNotDeletedException(Throwable cause) {
        super(cause);
    }

    public DishNotDeletedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

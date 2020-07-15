package by.training.dish;

public class DishNotUpdatedException extends DishServiceException {
    public DishNotUpdatedException(String message) {
        super(message);
    }

    public DishNotUpdatedException(String message, Throwable cause) {
        super(message, cause);
    }

    public DishNotUpdatedException(Throwable cause) {
        super(cause);
    }

    public DishNotUpdatedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

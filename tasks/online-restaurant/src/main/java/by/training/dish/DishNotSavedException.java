package by.training.dish;

public class DishNotSavedException extends DishServiceException {
    public DishNotSavedException(String message) {
        super(message);
    }

    public DishNotSavedException(String message, Throwable cause) {
        super(message, cause);
    }

    public DishNotSavedException(Throwable cause) {
        super(cause);
    }

    public DishNotSavedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

package by.training.dish;

public class DishNotAssignedException extends DishServiceException {
    public DishNotAssignedException(String message) {
        super(message);
    }

    public DishNotAssignedException(String message, Throwable cause) {
        super(message, cause);
    }

    public DishNotAssignedException(Throwable cause) {
        super(cause);
    }

    public DishNotAssignedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

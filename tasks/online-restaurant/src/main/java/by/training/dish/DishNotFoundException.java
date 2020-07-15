package by.training.dish;

public class DishNotFoundException extends DishServiceException {
    public DishNotFoundException(String message) {
        super(message);
    }

    public DishNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public DishNotFoundException(Throwable cause) {
        super(cause);
    }

    public DishNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

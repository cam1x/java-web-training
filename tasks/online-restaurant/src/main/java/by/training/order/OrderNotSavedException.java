package by.training.order;

public class OrderNotSavedException extends OrderServiceException {
    public OrderNotSavedException(String message) {
        super(message);
    }

    public OrderNotSavedException(String message, Throwable cause) {
        super(message, cause);
    }

    public OrderNotSavedException(Throwable cause) {
        super(cause);
    }

    public OrderNotSavedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

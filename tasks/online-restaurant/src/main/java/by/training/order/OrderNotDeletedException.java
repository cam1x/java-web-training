package by.training.order;

public class OrderNotDeletedException extends OrderServiceException {
    public OrderNotDeletedException(String message) {
        super(message);
    }

    public OrderNotDeletedException(String message, Throwable cause) {
        super(message, cause);
    }

    public OrderNotDeletedException(Throwable cause) {
        super(cause);
    }

    public OrderNotDeletedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

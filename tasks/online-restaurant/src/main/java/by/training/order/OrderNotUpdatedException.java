package by.training.order;

public class OrderNotUpdatedException extends OrderServiceException {
    public OrderNotUpdatedException(String message) {
        super(message);
    }

    public OrderNotUpdatedException(String message, Throwable cause) {
        super(message, cause);
    }

    public OrderNotUpdatedException(Throwable cause) {
        super(cause);
    }

    public OrderNotUpdatedException(String message, Throwable cause,
                                    boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

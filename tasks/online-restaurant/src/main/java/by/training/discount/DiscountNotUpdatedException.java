package by.training.discount;

public class DiscountNotUpdatedException extends DiscountServiceException {
    public DiscountNotUpdatedException(String message) {
        super(message);
    }

    public DiscountNotUpdatedException(String message, Throwable cause) {
        super(message, cause);
    }

    public DiscountNotUpdatedException(Throwable cause) {
        super(cause);
    }

    public DiscountNotUpdatedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

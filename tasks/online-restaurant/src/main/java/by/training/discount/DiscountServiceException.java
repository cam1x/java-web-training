package by.training.discount;

public class DiscountServiceException extends Exception {
    public DiscountServiceException(String message) {
        super(message);
    }

    public DiscountServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public DiscountServiceException(Throwable cause) {
        super(cause);
    }

    public DiscountServiceException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

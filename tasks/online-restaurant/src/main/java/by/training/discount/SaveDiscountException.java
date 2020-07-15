package by.training.discount;

public class SaveDiscountException extends DiscountServiceException {
    public SaveDiscountException(String message) {
        super(message);
    }

    public SaveDiscountException(String message, Throwable cause) {
        super(message, cause);
    }

    public SaveDiscountException(Throwable cause) {
        super(cause);
    }

    public SaveDiscountException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

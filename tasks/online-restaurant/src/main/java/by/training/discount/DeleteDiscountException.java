package by.training.discount;

public class DeleteDiscountException extends DiscountServiceException {
    public DeleteDiscountException(String message) {
        super(message);
    }

    public DeleteDiscountException(String message, Throwable cause) {
        super(message, cause);
    }

    public DeleteDiscountException(Throwable cause) {
        super(cause);
    }

    public DeleteDiscountException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

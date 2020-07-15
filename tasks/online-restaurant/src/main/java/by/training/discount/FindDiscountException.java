package by.training.discount;

public class FindDiscountException extends DiscountServiceException {
    public FindDiscountException(String message) {
        super(message);
    }

    public FindDiscountException(String message, Throwable cause) {
        super(message, cause);
    }

    public FindDiscountException(Throwable cause) {
        super(cause);
    }

    public FindDiscountException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

package by.training.core;

public class NotUniqueBeanException extends BeanRegistrationException {
    public NotUniqueBeanException(String message) {
        super(message);
    }

    public NotUniqueBeanException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotUniqueBeanException(Throwable cause) {
        super(cause);
    }

    public NotUniqueBeanException(String message, Throwable cause,
                                  boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
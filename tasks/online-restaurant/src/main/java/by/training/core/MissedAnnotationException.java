package by.training.core;

public class MissedAnnotationException extends BeanRegistrationException {
    public MissedAnnotationException(String message) {
        super(message);
    }

    public MissedAnnotationException(String message, Throwable cause) {
        super(message, cause);
    }

    public MissedAnnotationException(Throwable cause) {
        super(cause);
    }

    public MissedAnnotationException(String message, Throwable cause,
                                     boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
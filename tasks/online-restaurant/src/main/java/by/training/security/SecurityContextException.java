package by.training.security;

public class SecurityContextException extends RuntimeException {
    public SecurityContextException(String message) {
        super(message);
    }

    public SecurityContextException(String message, Throwable cause) {
        super(message, cause);
    }

    public SecurityContextException(Throwable cause) {
        super(cause);
    }

    public SecurityContextException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

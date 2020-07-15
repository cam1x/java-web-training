package by.training.role;

public class RoleNotSavedException extends RoleServiceException {
    public RoleNotSavedException(String message) {
        super(message);
    }

    public RoleNotSavedException(String message, Throwable cause) {
        super(message, cause);
    }

    public RoleNotSavedException(Throwable cause) {
        super(cause);
    }

    public RoleNotSavedException(String message, Throwable cause,
                                 boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

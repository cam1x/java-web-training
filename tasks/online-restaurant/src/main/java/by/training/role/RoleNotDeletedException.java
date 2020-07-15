package by.training.role;

public class RoleNotDeletedException extends RoleServiceException {
    public RoleNotDeletedException(String message) {
        super(message);
    }

    public RoleNotDeletedException(String message, Throwable cause) {
        super(message, cause);
    }

    public RoleNotDeletedException(Throwable cause) {
        super(cause);
    }

    public RoleNotDeletedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

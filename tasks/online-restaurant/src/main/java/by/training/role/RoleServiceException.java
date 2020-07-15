package by.training.role;

public class RoleServiceException extends Exception {
    public RoleServiceException(String message) {
        super(message);
    }

    public RoleServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public RoleServiceException(Throwable cause) {
        super(cause);
    }

    public RoleServiceException(String message, Throwable cause,
                                boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

package by.training.role;

public class RoleNotAssignedException extends RoleServiceException {
    public RoleNotAssignedException(String message) {
        super(message);
    }

    public RoleNotAssignedException(String message, Throwable cause) {
        super(message, cause);
    }

    public RoleNotAssignedException(Throwable cause) {
        super(cause);
    }

    public RoleNotAssignedException(String message, Throwable cause,
                                    boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
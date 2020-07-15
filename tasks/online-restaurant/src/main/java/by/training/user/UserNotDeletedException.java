package by.training.user;

public class UserNotDeletedException extends UserServiceException {
    public UserNotDeletedException(String message) {
        super(message);
    }

    public UserNotDeletedException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserNotDeletedException(Throwable cause) {
        super(cause);
    }

    public UserNotDeletedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

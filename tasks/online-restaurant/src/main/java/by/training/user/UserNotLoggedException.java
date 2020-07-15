package by.training.user;

public class UserNotLoggedException extends UserServiceException {
    public UserNotLoggedException(String message) {
        super(message);
    }

    public UserNotLoggedException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserNotLoggedException(Throwable cause) {
        super(cause);
    }

    public UserNotLoggedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
package by.training.user;

public class UserNotRegisteredException extends UserServiceException {
    public UserNotRegisteredException(String message) {
        super(message);
    }

    public UserNotRegisteredException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserNotRegisteredException(Throwable cause) {
        super(cause);
    }

    public UserNotRegisteredException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

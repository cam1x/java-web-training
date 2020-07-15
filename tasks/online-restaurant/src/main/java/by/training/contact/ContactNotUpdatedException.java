package by.training.contact;

public class ContactNotUpdatedException extends ContactServiceException {
    public ContactNotUpdatedException(String message) {
        super(message);
    }

    public ContactNotUpdatedException(String message, Throwable cause) {
        super(message, cause);
    }

    public ContactNotUpdatedException(Throwable cause) {
        super(cause);
    }

    public ContactNotUpdatedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

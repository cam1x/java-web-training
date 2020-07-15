package by.training.contact;

public class ContactNotDeletedException extends ContactServiceException {
    public ContactNotDeletedException(String message) {
        super(message);
    }

    public ContactNotDeletedException(String message, Throwable cause) {
        super(message, cause);
    }

    public ContactNotDeletedException(Throwable cause) {
        super(cause);
    }

    public ContactNotDeletedException(String message, Throwable cause,
                                      boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

package by.training.contact;

public class ContactNotSavedException extends ContactServiceException {
    public ContactNotSavedException(String message) {
        super(message);
    }

    public ContactNotSavedException(String message, Throwable cause) {
        super(message, cause);
    }

    public ContactNotSavedException(Throwable cause) {
        super(cause);
    }

    public ContactNotSavedException(String message, Throwable cause,
                                    boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

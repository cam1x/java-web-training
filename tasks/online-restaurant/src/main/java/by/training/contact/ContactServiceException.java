package by.training.contact;

public class ContactServiceException extends Exception {
    public ContactServiceException(String message) {
        super(message);
    }

    public ContactServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public ContactServiceException(Throwable cause) {
        super(cause);
    }

    public ContactServiceException(String message, Throwable cause,
                                      boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

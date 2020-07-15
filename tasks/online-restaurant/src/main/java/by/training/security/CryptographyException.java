package by.training.security;

public class CryptographyException extends RuntimeException {
    public CryptographyException(String message) {
        super(message);
    }

    public CryptographyException(String message, Throwable cause) {
        super(message, cause);
    }

    public CryptographyException(Throwable cause) {
        super(cause);
    }

    public CryptographyException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

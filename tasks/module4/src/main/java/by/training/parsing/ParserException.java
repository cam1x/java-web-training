package by.training.parsing;

public class ParserException extends Exception {
    public ParserException(String msg) {
        super(msg);
    }

    public ParserException(String message, Throwable cause) {
        super(message, cause);
    }

    public ParserException(Throwable cause) {
        super(cause);
    }
}


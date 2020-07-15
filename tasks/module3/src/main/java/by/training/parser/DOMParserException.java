package by.training.parser;

public class DOMParserException extends ParserException {

    public DOMParserException(String msg) {
        super(msg);
    }

    public DOMParserException(String message, Throwable cause) {
        super(message, cause);
    }

    public DOMParserException(Throwable cause) {
        super(cause);
    }
}

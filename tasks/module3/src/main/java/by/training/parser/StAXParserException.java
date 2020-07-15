package by.training.parser;

public class StAXParserException extends ParserException {

    public StAXParserException(String msg) {
        super(msg);
    }

    public StAXParserException(String message, Throwable cause) {
        super(message, cause);
    }

    public StAXParserException(Throwable cause) {
        super(cause);
    }
}

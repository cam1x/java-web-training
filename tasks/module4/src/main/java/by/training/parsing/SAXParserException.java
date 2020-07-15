package by.training.parsing;

public class SAXParserException extends ParserException {
    public SAXParserException(String msg) {
        super(msg);
    }

    public SAXParserException(String message, Throwable cause) {
        super(message, cause);
    }

    public SAXParserException(Throwable cause) {
        super(cause);
    }
}

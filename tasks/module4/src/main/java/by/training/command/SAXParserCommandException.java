package by.training.command;

public class SAXParserCommandException extends CommandException {
    public SAXParserCommandException(String msg) {
        super(msg);
    }

    public SAXParserCommandException(String message, Throwable cause) {
        super(message, cause);
    }

    public SAXParserCommandException(Throwable cause) {
        super(cause);
    }
}
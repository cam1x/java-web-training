package by.training.command;

public class StAXParserCommandException extends CommandException {

    public StAXParserCommandException(String msg) {
        super(msg);
    }

    public StAXParserCommandException(String message, Throwable cause) {
        super(message, cause);
    }

    public StAXParserCommandException(Throwable cause) {
        super(cause);
    }
}

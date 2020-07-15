package by.training.command;

public class CommandException extends Exception {
    public CommandException(String msg) {
        super(msg);
    }

    public CommandException(String message, Throwable cause) {
        super(message, cause);
    }

    public CommandException(Throwable cause) {
        super(cause);
    }
}
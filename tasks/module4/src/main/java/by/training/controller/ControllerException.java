package by.training.controller;

public class ControllerException extends RuntimeException {
    public ControllerException(String msg) {
        super(msg);
    }

    public ControllerException(String message, Throwable cause) {
        super(message, cause);
    }

    public ControllerException(Throwable cause) {
        super(cause);
    }
}

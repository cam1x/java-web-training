package by.training.command;

public interface CommandProvider<T> {
    void register(CommandType type, Command<T> command);
    Command<T> getCommand(CommandType type);
}

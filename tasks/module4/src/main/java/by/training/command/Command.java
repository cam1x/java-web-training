package by.training.command;

import java.util.List;

@FunctionalInterface
public interface Command<T> {
    List<T> build(String shipXMLPath, String poolXMLPath) throws CommandException;
}

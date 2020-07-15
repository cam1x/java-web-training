package by.training.parser;

import java.util.List;

@FunctionalInterface
public interface Parser<T> {
    List<T> parse(String xmlPath) throws ParserException;
}

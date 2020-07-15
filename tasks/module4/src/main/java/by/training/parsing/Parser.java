package by.training.parsing;

import java.util.List;

@FunctionalInterface
public interface Parser<T> {
    List<T> parse(String shipXMLPath, String poolXMLPath) throws ParserException;
}

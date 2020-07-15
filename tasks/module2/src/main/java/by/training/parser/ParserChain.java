package by.training.parser;

import by.training.model.TextComposite;

public interface ParserChain<T> {
    TextComposite parseLine(String line);
    ParserChain<T> linkWith(ParserChain<T> next);
}
package by.training.parser;

import by.training.model.TextComposite;

import java.util.Optional;

public abstract class TextParser implements ParserChain<TextComposite> {

    private TextParser next;

    public ParserChain<TextComposite> linkWith(ParserChain<TextComposite> next) {
        this.next = (TextParser) next;
        return this;
    }

    Optional<TextComposite> nextParse(String line) {
        return (next!=null)? Optional.ofNullable(next.parseLine(line)): Optional.empty();
    }
}
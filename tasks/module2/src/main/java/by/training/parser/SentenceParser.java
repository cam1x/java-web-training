package by.training.parser;

import by.training.model.Paragraph;
import by.training.model.TextComposite;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SentenceParser extends TextParser {

    private static final Pattern SENTENCE_PARSER = Pattern.compile("(\\n)?[A-ZА-Я].+?[.?!]+[^\\nA-ZА-Я]*");

    @Override
    public TextComposite parseLine(String line) {
        Paragraph paragraph = new Paragraph();
        Matcher matcher = SENTENCE_PARSER.matcher(line);
        while (matcher.find()) {
            Optional<TextComposite> textComposite = nextParse(matcher.group());
            textComposite.ifPresent(paragraph::addText);
        }
        return paragraph;
    }
}

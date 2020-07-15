package by.training.parser;

import by.training.model.*;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ParagraphParser extends TextParser {

    private static final Pattern PARAGRAPH_PARSER = Pattern.compile("\\t[^\\t]+[\\.\\?\\!]+");

    @Override
    public TextComposite parseLine(String line) {
        WholeText wholeText = new WholeText();
        Matcher matcher = PARAGRAPH_PARSER.matcher(line);
        while (matcher.find()) {
            Optional<TextComposite> textComposite = nextParse(matcher.group());
            textComposite.ifPresent(wholeText::addText);
        }
        return wholeText;
    }
}

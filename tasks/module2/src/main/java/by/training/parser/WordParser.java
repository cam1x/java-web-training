package by.training.parser;

import by.training.model.Sentence;
import by.training.model.TextComposite;
import by.training.model.Word;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WordParser extends TextParser {

    private static final Pattern WORD_PARSER = Pattern.compile("([\\'\\(]+)?(\\w+|\\-)([\\.\\,\\'\\)\\:\\s]+)?");

    @Override
    public TextComposite parseLine(String line) {
        Sentence sentence = new Sentence(line.startsWith("\n"));
        Matcher wordMatcher = WORD_PARSER.matcher(line);
        while (wordMatcher.find()) {
            String beg = wordMatcher.group(1);
            String end = wordMatcher.group(3);
            Word word = new Word(wordMatcher.group(2), (beg==null)?"":beg, (end==null)?"":end);
            sentence.addText(word);
        }
        return sentence;
    }
}

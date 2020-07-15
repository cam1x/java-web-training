package by.training.parser;

import by.training.model.TextComposite;
import by.training.reader.FromFileReader;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.*;

@RunWith(JUnit4.class)
public class ParagraphParserTest {

    private ParagraphParser parser;

    @Before
    public  void prepare() {
        WordParser wordParser = new WordParser();
        SentenceParser sentenceParser = new SentenceParser();
        sentenceParser.linkWith(wordParser);
        parser = new ParagraphParser();
        parser.linkWith(sentenceParser);
    }

    @Test
    public void shouldParseValidTextFirst() {
        String path = new File(this.getClass().getClassLoader()
                .getResource("ValidText1.txt").getFile()).getAbsolutePath();
        FromFileReader reader = new FromFileReader();
        String expected = reader.readInString(path);

        TextComposite wholeText = parser.parseLine(expected);
        String real = wholeText.getText();

        Assert.assertEquals(expected, real);
    }

    @Test
    public void shouldParseValidTextSecond() {
        String path = new File(this.getClass().getClassLoader()
                .getResource("ValidText2.txt").getFile()).getAbsolutePath();
        FromFileReader reader = new FromFileReader();
        String expected = reader.readInString(path);

        TextComposite wholeText = parser.parseLine(expected);
        String real = wholeText.getText();

        Assert.assertEquals(expected, real);
    }

    @Test
    public void shouldParseEmptyText() {
        String path = new File(this.getClass().getClassLoader()
                .getResource("Empty.txt").getFile()).getAbsolutePath();
        FromFileReader reader = new FromFileReader();
        String expected = reader.readInString(path);

        TextComposite wholeText = parser.parseLine(expected);
        String real = wholeText.getText();

        Assert.assertEquals(expected, real);
    }
}

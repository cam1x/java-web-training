package by.training.repository;

import by.training.model.TextComposite;
import by.training.parser.ParagraphParser;
import by.training.parser.SentenceParser;
import by.training.parser.WordParser;
import by.training.reader.FromFileReader;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.File;

@RunWith(JUnit4.class)
public class WholeTextRepositoryTest {

    private SmartTextRepository wholeTextRepo;
    private SmartTextRepository paragraphRepo;
    private SmartTextRepository sentenceRepo;
    private SmartTextRepository wordRepo;
    private ParagraphParser parser;

    @Before
    public void prepare() {
        WordParser wordParser = new WordParser();
        SentenceParser sentenceParser = new SentenceParser();
        sentenceParser.linkWith(wordParser);
        parser = new ParagraphParser();
        parser.linkWith(sentenceParser);
        wordRepo = new WordRepository();
        sentenceRepo = new SentenceRepository(wordRepo);
        paragraphRepo = new ParagraphRepository(sentenceRepo);
        wholeTextRepo = new WholeTextRepository(paragraphRepo);
    }

    @Test
    public void shouldAddTwoTexts() {
        String pathFirst = new File(this.getClass().getClassLoader()
                .getResource("ValidText1.txt").getFile()).getAbsolutePath();
        String pathSecond = new File(this.getClass().getClassLoader()
                .getResource("ValidText2.txt").getFile()).getAbsolutePath();
        FromFileReader reader = new FromFileReader();
        String textFirst = reader.readInString(pathFirst);
        String textSecond = reader.readInString(pathSecond);

        TextComposite wholeTextFirst = parser.parseLine(textFirst);

        TextComposite wholeTextSecond = parser.parseLine(textSecond);

        wholeTextRepo.add(wholeTextFirst);
        wholeTextRepo.add(wholeTextSecond);

        Assert.assertEquals(2, wholeTextRepo.getAll().size());

        wholeTextRepo.setCurrentTextNum(0);
        Assert.assertEquals(5, paragraphRepo.getAll().size());
        Assert.assertEquals(textFirst.split("[\\.\\?\\!]+").length, sentenceRepo.getAll().size());
        Assert.assertEquals(textFirst.split("\\b\\W+?(\\b|$)").length, wordRepo.getAll().size());

        wholeTextRepo.setCurrentTextNum(1);
        Assert.assertEquals(7, paragraphRepo.getAll().size());
        Assert.assertEquals(textSecond.split("[\\.\\?\\!]+").length, sentenceRepo.getAll().size());
        Assert.assertEquals(textSecond.split("\\b\\W+?(\\b|$)").length, wordRepo.getAll().size());
    }
}

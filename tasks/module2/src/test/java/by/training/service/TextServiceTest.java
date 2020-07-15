package by.training.service;

import by.training.model.*;
import by.training.parser.ParagraphParser;
import by.training.parser.SentenceParser;
import by.training.parser.WordParser;
import by.training.reader.FromFileReader;
import by.training.repository.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.File;
import java.util.List;

@RunWith(JUnit4.class)
public class TextServiceTest {

    private TextService service;
    private SmartTextRepository wordRepo;
    private SmartTextRepository sentenceRepo;
    private SmartTextRepository paragraphRepo;
    private SmartTextRepository wholeTextRepo;

    @Before
    public void prepare() {
        wordRepo = new WordRepository();
        sentenceRepo = new SentenceRepository(wordRepo);
        paragraphRepo = new ParagraphRepository(sentenceRepo);
        wholeTextRepo = new WholeTextRepository(paragraphRepo);
        service = new TextService(wholeTextRepo);
    }

    @Test
    public void shouldAddWordToText() {
        Word word = new Word("word");

        service.save(word);

        Assert.assertEquals(1, wordRepo.getAll().size());
        Assert.assertEquals(0, sentenceRepo.getAll().size());
        Assert.assertEquals(0, paragraphRepo.getAll().size());
        Assert.assertEquals(0, wholeTextRepo.getAll().size());
    }

    @Test
    public void shouldAddSentenceToText() {
        String sentences = "Mauris viverra arcu sit amet rutrum interdum.";

        WordParser wordParser = new WordParser();

        TextComposite sentence  = wordParser.parseLine(sentences);

        service.save(sentence);

        Assert.assertEquals(7, wordRepo.getAll().size());
        Assert.assertEquals(1, sentenceRepo.getAll().size());
        Assert.assertEquals(0, paragraphRepo.getAll().size());
        Assert.assertEquals(0, wholeTextRepo.getAll().size());
    }

    @Test
    public void shouldAddParagraphToText() {
        String sentences = "Mauris viverra arcu sit amet rutrum interdum." +
                "Phasellus commodo pellentesque purus non tempus. Curabitur ut mi orci. In nec turpis nisi.";

        SentenceParser sentenceParser = new SentenceParser();
        WordParser wordParser = new WordParser();
        sentenceParser.linkWith(wordParser);

        TextComposite paragraph  = sentenceParser.parseLine(sentences);
        service.save(paragraph);

        Assert.assertEquals(21, wordRepo.getAll().size());
        Assert.assertEquals(4, sentenceRepo.getAll().size());
        Assert.assertEquals(1, paragraphRepo.getAll().size());
        Assert.assertEquals(0, wholeTextRepo.getAll().size());
    }

    @Test
    public void shouldRemoveWord() {
        String sentences = "Mauris viverra arcu sit amet rutrum interdum." +
                "Phasellus commodo pellentesque purus non tempus. Curabitur ut mi orci. In nec turpis nisi.";

        SentenceParser sentenceParser = new SentenceParser();
        WordParser wordParser = new WordParser();
        sentenceParser.linkWith(wordParser);

        TextComposite paragraph  = sentenceParser.parseLine(sentences);

        service.save(paragraph);
        Word toRemove = new Word("tempus");
        service.remove(toRemove);

        Assert.assertEquals(20, wordRepo.getAll().size());
        Assert.assertEquals(4, sentenceRepo.getAll().size());
        Assert.assertEquals(1, paragraphRepo.getAll().size());
        Assert.assertEquals(0, wholeTextRepo.getAll().size());
    }

    @Test
    public void shouldRemoveSentence() {
        String sentences = "Mauris viverra arcu sit amet rutrum interdum." +
                "Phasellus commodo pellentesque purus non tempus. Curabitur ut mi orci. In nec turpis nisi.";

        SentenceParser sentenceParser = new SentenceParser();
        WordParser wordParser = new WordParser();
        sentenceParser.linkWith(wordParser);
        TextComposite paragraph  = sentenceParser.parseLine(sentences);
        service.save(paragraph);

        String sentence = "Mauris viverra arcu sit amet rutrum interdum.";
        TextComposite toRemove  = wordParser.parseLine(sentence);
        service.remove(toRemove);

        Assert.assertEquals(14, wordRepo.getAll().size());
        Assert.assertEquals(3, sentenceRepo.getAll().size());
        Assert.assertEquals(1, paragraphRepo.getAll().size());
        Assert.assertEquals(0, wholeTextRepo.getAll().size());
    }

    @Test
    public void shouldFindAllSentences() {
        WordParser wordParser = new WordParser();
        SentenceParser sentenceParser = new SentenceParser();
        sentenceParser.linkWith(wordParser);
        ParagraphParser parser = new ParagraphParser();
        parser.linkWith(sentenceParser);

        String path = new File(this.getClass().getClassLoader()
                .getResource("ValidText1.txt").getFile()).getAbsolutePath();
        FromFileReader reader = new FromFileReader();
        String expected = reader.readInString(path);

        TextComposite wholeText = parser.parseLine(expected);

        service.save(wholeText);
        List<TextComposite> found = service.find(x->x.getTextPartType()==TextPartTypes.SENTENCE);

        Assert.assertEquals(sentenceRepo.getAll().size(), found.size());
    }
}

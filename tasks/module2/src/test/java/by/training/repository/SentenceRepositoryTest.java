package by.training.repository;

import by.training.model.TextComposite;
import by.training.parser.WordParser;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class SentenceRepositoryTest {

    private SmartTextRepository sentenceRepo;
    private SmartTextRepository wordRepo;

    @Before
    public void prepare() {
        wordRepo = new WordRepository();
        sentenceRepo = new SentenceRepository(wordRepo);
    }

    @Test
    public void shouldAddAll() {
        String sentences = "Mauris viverra arcu sit amet rutrum interdum.";

        WordParser wordParser = new WordParser();

        TextComposite sentence = wordParser.parseLine(sentences);
        sentenceRepo.add(sentence);

        Assert.assertEquals(1, sentenceRepo.getAll().size());
        Assert.assertEquals(7, wordRepo.getAll().size());
    }
}

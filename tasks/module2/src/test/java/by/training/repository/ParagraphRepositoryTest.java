package by.training.repository;

import by.training.model.TextComposite;
import by.training.parser.SentenceParser;
import by.training.parser.WordParser;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;


@RunWith(JUnit4.class)
public class ParagraphRepositoryTest {

    private SmartTextRepository paragraphRepo;
    private SmartTextRepository sentenceRepo;
    private SmartTextRepository wordRepo;
    private SentenceParser parser;

    @Before
    public void prepare() {
        WordParser wordParser = new WordParser();
        parser = new SentenceParser();
        parser.linkWith(wordParser);
        wordRepo = new WordRepository();
        sentenceRepo = new SentenceRepository(wordRepo);
        paragraphRepo = new ParagraphRepository(sentenceRepo);
    }

    @Test
    public void shouldAddAll() {
        String inputParagraph = "\tMauris viverra arcu sit amet rutrum interdum. Phasellus commodo pellentesque purus non tempus. " +
                "Curabitur ut mi orci. In nec turpis nisi. Proin interdum dui libero, vel porta quam ultrices id.\n" +
                "Morbi et lorem vehicula, volutpat nibh et, lacinia lectus. Maecenas commodo velit in lectus tempus hendrerit. " +
                "Nulla sit amet suscipit nulla, commodo iaculis mi.\n" +
                "Curabitur luctus risus in convallis gravida. Sed tristique diam a nibh suscipit vulputate. Morbi sit amet viverra sapien.";

        TextComposite paragraph = parser.parseLine(inputParagraph);
        paragraphRepo.add(paragraph);

        Assert.assertEquals(1, paragraphRepo.getAll().size());
        Assert.assertEquals(inputParagraph.split("[\\.\\?\\!]+").length, sentenceRepo.getAll().size());
        Assert.assertEquals(inputParagraph.split("\\b\\W+?(\\b|$)").length, wordRepo.getAll().size());
    }
}

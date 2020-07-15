package by.training.model;

import by.training.parser.ParagraphParser;
import by.training.parser.SentenceParser;
import by.training.parser.WordParser;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.Comparator;

@RunWith(JUnit4.class)
public class CompositeSortingTest {

    private static WordParser wordParser;
    private static SentenceParser sentenceParser;
    private static ParagraphParser paragraphParser;

    @BeforeClass
    public static void prepare() {
        wordParser = new WordParser();
        sentenceParser = new SentenceParser();
        sentenceParser.linkWith(wordParser);
        paragraphParser = new ParagraphParser();
        paragraphParser.linkWith(sentenceParser);
    }

    @Test
    public void shouldSortByWordsFromString() {
        String sentence = "Mauris viverra arcu sit amet rutrum interdum.";

        Comparator<TextComposite> comparator = Comparator.comparingInt(p -> p.getText().length());
        TextComposite sentenceComposite = wordParser.parseLine(sentence);
        TextComposite sortedSentence = sentenceComposite.sort(TextPartTypes.WORD, comparator);

        String expected = "sit arcu amet Mauris rutrum viverra interdum.";

        Assert.assertEquals(expected, sortedSentence.getText());
    }

    @Test
    public void shouldSortBySentencesFromString() {
        String inputParagraph = "\tMauris viverra arcu sit amet rutrum interdum and smth more. " +
                "Phasellus commodo pellentesque purus non tempus. " +
                "In nec turpis nisi. Proin interdum dui libero, vel porta quam ultrices id of mine.\n" +
                "Morbi et lorem vehicula, volutpat nibh et, lacinia lectus. " +
                "Maecenas commodo velit in lectus tempus hendrerit. " +
                "Nulla sit amet suscipit nulla, commodo iaculis mi.\n";

        Comparator<TextComposite> comparator = Comparator.comparingInt(p -> p.getPartsList().size());
        TextComposite paragraph = sentenceParser.parseLine(inputParagraph);
        TextComposite sorted = paragraph.sort(TextPartTypes.SENTENCE, comparator);

        String expected =  "\tIn nec turpis nisi. " + "Phasellus commodo pellentesque purus non tempus. "
                + "Maecenas commodo velit in lectus tempus hendrerit. " +
                "Nulla sit amet suscipit nulla, commodo iaculis mi.\n" +
                "Morbi et lorem vehicula, volutpat nibh et, lacinia lectus. "
                + "Mauris viverra arcu sit amet rutrum interdum and smth more. " +
                "Proin interdum dui libero, vel porta quam ultrices id of mine.";

        Assert.assertEquals(expected, sorted.getText());
    }

    @Test
    public void shouldSortByParagraphsFromString() {
        String input = "\tFirst sentence. Some more words. Totally 3 sentences.\n"+
                "\tOne more chapter. Write smth. More. More. Totally 5 sentences.\n" +
                "\tYeah,its small.\n"+
                "\tMauris viverra arcu sit amet rutrum interdum and smth more. " +
                "Phasellus commodo pellentesque purus non tempus. " +
                "In nec turpis nisi. Proin interdum dui libero, vel porta quam ultrices id of mine.\n" +
                "Morbi et lorem vehicula, volutpat nibh et, lacinia lectus. " +
                "Maecenas commodo velit in lectus tempus hendrerit. " +
                "Nulla sit amet suscipit nulla, commodo iaculis mi.\n";

        Comparator<TextComposite> comparator = Comparator.comparingInt(p -> p.getPartsList().size());
        TextComposite text = paragraphParser.parseLine(input);
        TextComposite sorted = text.sort(TextPartTypes.PARAGRAPH, comparator);

        String expected = "\tYeah,its small.\n" +
                "\tFirst sentence. Some more words. Totally 3 sentences.\n" +
                "\tOne more chapter. Write smth. More. More. Totally 5 sentences.\n" +
                "\tMauris viverra arcu sit amet rutrum interdum and smth more. " +
                "Phasellus commodo pellentesque purus non tempus. " +
                "In nec turpis nisi. Proin interdum dui libero, vel porta quam ultrices id of mine.\n" +
                "Morbi et lorem vehicula, volutpat nibh et, lacinia lectus. " +
                "Maecenas commodo velit in lectus tempus hendrerit. " +
                "Nulla sit amet suscipit nulla, commodo iaculis mi.";

        Assert.assertEquals(expected, sorted.getText());
    }
}

package by.training.parser;

import by.training.model.TextComposite;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class SentenceParserTest {

    private SentenceParser parser;

    @Before
    public void prepare() {
        WordParser wordParser = new WordParser();
        parser = new SentenceParser();
        parser.linkWith(wordParser);
    }

    @Test
    public void shouldParseAllSentencesFirst() {
        String inputSentences = "\tAc placerat vestibulum lectus mauris ultrices eros in cursus turpis. " +
                "Convallis a cras semper auctor neque vitae tempus. Augue neque gravida in fermentum et sollicitudin. " +
                "Gravida quis blandit turpis cursus in hac habitasse platea dictumst. Et malesuada fames ac turpis egestas. " +
                "Semper viverra nam libero justo laoreet sit amet cursus sit. " +
                "Leo vel fringilla est ullamcorper. Aliquet nec ullamcorper sit amet risus nullam. Non consectetur a erat nam. " +
                "Nec ultrices dui sapien eget mi proin. Magna eget est lorem ipsum dolor sit.";

        TextComposite paragraph = parser.parseLine(inputSentences);
        String actual = paragraph.getText();

        Assert.assertEquals(inputSentences, actual);
    }

    @Test
    public void shouldParseAllSentencesSecond() {
        String inputSentences = "\tQuam nulla porttitor massa id neque. Enim nulla aliquet porttitor lacus luctus. " +
                "Pretium viverra suspendisse potenti nullam ac tortor vitae."+
                "Arcu felis bibendum ut tristique. Placerat in egestas erat imperdiet sed euismod nisi porta. " +
                "Malesuada pellentesque elit eget gravida. Semper eget duis at tellus at urna." +
                "Lacus viverra vitae congue eu consequat. Amet nisl suscipit adipiscing bibendum est ultricies. " +
                "Libero justo laoreet sit amet. Dui id ornare arcu odio ut sem nulla pharetra diam." +
                "Vestibulum lorem sed risus ultricies tristique. Varius morbi enim nunc faucibus a pellentesque.";

        TextComposite paragraph = parser.parseLine(inputSentences);
        String actual = paragraph.getText();

        Assert.assertEquals(inputSentences, actual);
    }

    @Test
    public void shouldParseAllSentencesFromEmpty() {
        String inputSentences = "\t";

        TextComposite paragraph  = parser.parseLine(inputSentences);
        String actual = paragraph.getText();

        Assert.assertEquals(inputSentences, actual);
    }
}

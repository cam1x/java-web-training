package by.training.parser;

import by.training.model.TextComposite;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class WordParserTest {

    private WordParser parser;

    @Before
    public void prepare() {
        parser = new WordParser();
    }

    @Test
    public void shouldParseAllWords() {
        String input = "Ac placerat vestibulum lectus mauris ultrices eros in cursus turpis. " +
                "Convallis a cras semper auctor neque vitae tempus. Augue neque gravida in fermentum et sollicitudin." +
                "Gravida quis blandit turpis cursus in hac habitasse platea dictumst. Et malesuada fames ac turpis egestas. " +
                "Semper viverra nam libero justo laoreet sit amet cursus sit." +
                "Leo vel fringilla est ullamcorper. Aliquet nec ullamcorper sit amet risus nullam. Non consectetur a erat nam. " +
                "Nec ultrices dui sapien eget mi proin. Magna eget est lorem ipsum dolor sit.";

        TextComposite sentence = parser.parseLine(input);

        Assert.assertEquals(input, sentence.getText());
    }
}

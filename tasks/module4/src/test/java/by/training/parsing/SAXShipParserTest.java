package by.training.parsing;

import by.training.thread.Ship;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.File;
import java.util.List;
import java.util.Objects;

@RunWith(JUnit4.class)
public class SAXShipParserTest {
    private static Parser<Ship> parser;

    @BeforeClass
    public static void prepareBeforeTest() {
        parser = new SAXShipParser();
    }

    @Test
    public void shouldSuccessfullyParse() throws ParserException {
        String shipXMLPath = new File(Objects.requireNonNull(this.getClass().getClassLoader()
                .getResource("ships.xml")).getFile()).getAbsolutePath();
        String poolXMLPath = new File(Objects.requireNonNull(this.getClass().getClassLoader()
                .getResource("pool.xml")).getFile()).getAbsolutePath();

        List<Ship> parsedShips = parser.parse(shipXMLPath, poolXMLPath);

        Assert.assertEquals(7, parsedShips.size());
    }

    @Test(expected = ParserException.class)
    public void shouldThrowExceptionAfterParsingFromInvalidXML() throws ParserException {
        String shipXMLPath = new File(Objects.requireNonNull(this.getClass().getClassLoader()
                .getResource("empty.xml")).getFile()).getAbsolutePath();
        String poolXMLPath = new File(Objects.requireNonNull(this.getClass().getClassLoader()
                .getResource("pool.xml")).getFile()).getAbsolutePath();

        parser.parse(shipXMLPath, poolXMLPath);
    }

    @Test(expected = ParserException.class)
    public void shouldThrowExceptionAfterParsingFromNotExistingXML() throws ParserException {
        String shipXMLPath = "notExisting.xml";
        String poolXMLPath = new File(Objects.requireNonNull(this.getClass().getClassLoader()
                .getResource("pool.xml")).getFile()).getAbsolutePath();

        parser.parse(shipXMLPath, poolXMLPath);
    }
}

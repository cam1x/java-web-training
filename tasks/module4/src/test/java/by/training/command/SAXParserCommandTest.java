package by.training.command;

import by.training.thread.Ship;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.File;
import java.util.List;
import java.util.Objects;

@RunWith(JUnit4.class)
public class SAXParserCommandTest {
    private Command<Ship> command;

    @Before
    public void prepare() {
        command = new SAXParserCommand();
    }

    @Test
    public void shouldSuccessfullyParse() throws CommandException {
        String shipXMLPath = new File(Objects.requireNonNull(this.getClass().getClassLoader()
                .getResource("ships.xml")).getFile()).getAbsolutePath();
        String poolXMLPath = new File(Objects.requireNonNull(this.getClass().getClassLoader()
                .getResource("pool.xml")).getFile()).getAbsolutePath();

        List<Ship> parsedShips = command.build(shipXMLPath, poolXMLPath);

        Assert.assertEquals(7, parsedShips.size());
    }

    @Test(expected = CommandException.class)
    public void shouldThrowExceptionAfterParsingFromInvalidXML() throws CommandException {
        String shipXMLPath = new File(Objects.requireNonNull(this.getClass().getClassLoader()
                .getResource("empty.xml")).getFile()).getAbsolutePath();
        String poolXMLPath = new File(Objects.requireNonNull(this.getClass().getClassLoader()
                .getResource("pool.xml")).getFile()).getAbsolutePath();

        command.build(shipXMLPath, poolXMLPath);
    }

    @Test(expected = CommandException.class)
    public void shouldThrowExceptionAfterParsingFromNotExistingXML() throws CommandException {
        String shipXMLPath = "notExisting.xml";
        String poolXMLPath = new File(Objects.requireNonNull(this.getClass().getClassLoader()
                .getResource("pool.xml")).getFile()).getAbsolutePath();

        command.build(shipXMLPath, poolXMLPath);
    }
}
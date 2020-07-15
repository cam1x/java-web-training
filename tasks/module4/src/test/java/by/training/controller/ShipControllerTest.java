package by.training.controller;

import by.training.command.CommandProvider;
import by.training.command.CommandType;
import by.training.command.SAXParserCommand;
import by.training.command.ShipCommandProvider;
import by.training.parsing.Parser;
import by.training.parsing.SAXShipParser;
import by.training.thread.Ship;
import by.training.thread.ShipOperationType;
import by.training.validation.FileValidator;
import by.training.validation.XMLValidator;
import by.training.validation.XMLValidatorTest;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.File;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@RunWith(JUnit4.class)
public class ShipControllerTest {
    private static ShipController controller;

    @BeforeClass
    public static void prepare() {
        String shipXSDPath = new File(Objects.requireNonNull(XMLValidatorTest.class.getClassLoader()
                .getResource("ships.xsd")).getFile()).getAbsolutePath();
        String poolXSDPath = new File(Objects.requireNonNull(XMLValidatorTest.class.getClassLoader()
                .getResource("pool.xsd")).getFile()).getAbsolutePath();

        FileValidator fileValidator = new FileValidator();
        XMLValidator shipXMLValidator = new XMLValidator(shipXSDPath);
        XMLValidator poolXMLValidator = new XMLValidator(poolXSDPath);
        CommandProvider<Ship> provider = new ShipCommandProvider();
        provider.register(CommandType.SAX_PARSER_COMMAND, new SAXParserCommand());

        controller = new ShipController(fileValidator, shipXMLValidator, poolXMLValidator, provider);
    }

    @Test
    public void shouldRun() throws InterruptedException {
        String shipXMLPath = new File(Objects.requireNonNull(this.getClass().getClassLoader()
                .getResource("ships.xml")).getFile()).getAbsolutePath();
        String poolXMLPath = new File(Objects.requireNonNull(this.getClass().getClassLoader()
                .getResource("pool.xml")).getFile()).getAbsolutePath();

        List<Ship> ships = controller.readFromXMLAndRun(shipXMLPath, poolXMLPath, CommandType.SAX_PARSER_COMMAND);
        TimeUnit.SECONDS.sleep(7);
        Assert.assertEquals(7, ships.size());

        List<Ship> notLoadedShips = ships.stream()
                .filter(x -> x.getOperationType() == ShipOperationType.LOAD)
                .filter(x -> x.getCurrCongestion() != x.getCapacity())
                .collect(Collectors.toList());
        Assert.assertTrue(notLoadedShips.isEmpty());

        List<Ship> notUnloadedShips = ships.stream()
                .filter(x -> x.getOperationType() == ShipOperationType.UNLOAD)
                .filter(x -> x.getCurrCongestion() != 0)
                .collect(Collectors.toList());
        Assert.assertTrue(notUnloadedShips.isEmpty());
    }
}
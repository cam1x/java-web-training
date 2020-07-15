package by.training.controller;

import by.training.command.*;
import by.training.entity.*;
import by.training.repository.BankDepositRepository;
import by.training.service.BankDepositService;
import by.training.service.Service;
import by.training.validator.FileValidator;
import by.training.validator.XMLValidator;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RunWith(JUnit4.class)
public class BankDepositControllerTest {

    private static List<BankDeposit> validDeposits;
    private FileValidator fileValidator;
    private BankDepositCommandProvider provider;
    private Service<BankDeposit> service;

    @Before
    public void prepareBeforeTest() {
        fileValidator = new FileValidator();
        provider = new BankDepositCommandProvider();
        BankDepositRepository repo = new BankDepositRepository();
        service = new BankDepositService(repo);
    }

    @BeforeClass
    public static void prepareBeforeClass() {
        BankDeposit demand = new DemandDeposit("BPS Sberbank", "Belarus", "Maxim",
                1, "USD", 2000, 10);

        BankDeposit metal = new MetalDeposit("ICBC", "China", "Pavel", 2, "gram",
                289, 15, TimeUnitType.YEAR, 5, "gold");

        BankDeposit savings = new SavingsDeposit("BNP Pariba", "England", "Mark", 3, "GBP",
                5890, 7, TimeUnitType.YEAR, 3);

        BankDeposit rated = new RatedDeposit("BoA", "USA", "John", 4, "USD",
                15900, 7, TimeUnitType.MONTH, 28, 7, 10);

        BankDeposit funded = new FundedDeposit("Crédit Agricole", "France", "Frank", 56, "EUR",
                28900, 8, TimeUnitType.YEAR, 7, 7);

        validDeposits = new ArrayList<>();
        validDeposits.add(demand);
        validDeposits.add(metal);
        validDeposits.add(savings);
        validDeposits.add(rated);
        validDeposits.add(funded);
    }

    @Test
    public void shouldBuildAllFromValidXMLWithDOMFirst() {
        String xsdPath = new File(Objects.requireNonNull(this.getClass().getClassLoader()
                .getResource("EntitiesSchema.xsd")).getFile()).getAbsolutePath();
        XMLValidator xmlValidator = new XMLValidator(xsdPath);
        provider.register(CommandType.DOM_PARSER_COMMAND, new DOMParserCommand());
        BankDepositController controller = new BankDepositController(fileValidator, xmlValidator, provider, service);

        String xmlPath = new File(Objects.requireNonNull(this.getClass().getClassLoader()
                .getResource("ValidEntitiesFirst.xml")).getFile()).getAbsolutePath();
        controller.buildFromXML(xmlPath, CommandType.DOM_PARSER_COMMAND);

        Assert.assertEquals(validDeposits, service.getAll());
    }

    @Test
    public void shouldBuildAllFromValidXMLWithDOMSecond() {
        String xsdPath = new File(Objects.requireNonNull(this.getClass().getClassLoader()
                .getResource("EntitiesSchema.xsd")).getFile()).getAbsolutePath();
        XMLValidator xmlValidator = new XMLValidator(xsdPath);
        provider.register(CommandType.DOM_PARSER_COMMAND, new DOMParserCommand());
        BankDepositController controller = new BankDepositController(fileValidator, xmlValidator, provider, service);

        String xmlPath = new File(Objects.requireNonNull(this.getClass().getClassLoader()
                .getResource("ValidEntitiesSecond.xml")).getFile()).getAbsolutePath();
        controller.buildFromXML(xmlPath, CommandType.DOM_PARSER_COMMAND);

        Assert.assertEquals(16, service.getAll().size());
    }

    @Test
    public void shouldBuildAllFromValidXMLWithSAXFirst() {
        String xsdPath = new File(Objects.requireNonNull(this.getClass().getClassLoader()
                .getResource("EntitiesSchema.xsd")).getFile()).getAbsolutePath();
        XMLValidator xmlValidator = new XMLValidator(xsdPath);
        provider.register(CommandType.SAX_PARSER_COMMAND, new SAXParserCommand());
        BankDepositController controller = new BankDepositController(fileValidator, xmlValidator, provider, service);

        String xmlPath = new File(Objects.requireNonNull(this.getClass().getClassLoader()
                .getResource("ValidEntitiesFirst.xml")).getFile()).getAbsolutePath();
        controller.buildFromXML(xmlPath, CommandType.SAX_PARSER_COMMAND);

        Assert.assertEquals(validDeposits, service.getAll());
    }

    @Test
    public void shouldBuildAllFromValidXMLWithSAXSecond() {
        String xsdPath = new File(Objects.requireNonNull(this.getClass().getClassLoader()
                .getResource("EntitiesSchema.xsd")).getFile()).getAbsolutePath();
        XMLValidator xmlValidator = new XMLValidator(xsdPath);
        provider.register(CommandType.SAX_PARSER_COMMAND, new SAXParserCommand());
        BankDepositController controller = new BankDepositController(fileValidator, xmlValidator, provider, service);

        String xmlPath = new File(Objects.requireNonNull(this.getClass().getClassLoader()
                .getResource("ValidEntitiesSecond.xml")).getFile()).getAbsolutePath();
        controller.buildFromXML(xmlPath, CommandType.SAX_PARSER_COMMAND);

        Assert.assertEquals(16, service.getAll().size());
    }

    @Test
    public void shouldBuildAllFromValidXMLWithStAXFirst() {
        String xsdPath = new File(Objects.requireNonNull(this.getClass().getClassLoader()
                .getResource("EntitiesSchema.xsd")).getFile()).getAbsolutePath();
        XMLValidator xmlValidator = new XMLValidator(xsdPath);
        provider.register(CommandType.StAX_PARSER_COMMAND, new StAXParserCommand());
        BankDepositController controller = new BankDepositController(fileValidator, xmlValidator, provider, service);

        String xmlPath = new File(Objects.requireNonNull(this.getClass().getClassLoader()
                .getResource("ValidEntitiesFirst.xml")).getFile()).getAbsolutePath();
        controller.buildFromXML(xmlPath, CommandType.StAX_PARSER_COMMAND);

        Assert.assertEquals(validDeposits, service.getAll());
    }

    @Test
    public void shouldBuildAllFromValidXMLWithStAXSecond() {
        String xsdPath = new File(Objects.requireNonNull(this.getClass().getClassLoader()
                .getResource("EntitiesSchema.xsd")).getFile()).getAbsolutePath();
        XMLValidator xmlValidator = new XMLValidator(xsdPath);
        provider.register(CommandType.StAX_PARSER_COMMAND, new StAXParserCommand());
        BankDepositController controller = new BankDepositController(fileValidator, xmlValidator, provider, service);

        String xmlPath = new File(Objects.requireNonNull(this.getClass().getClassLoader()
                .getResource("ValidEntitiesSecond.xml")).getFile()).getAbsolutePath();
        controller.buildFromXML(xmlPath, CommandType.StAX_PARSER_COMMAND);

        Assert.assertEquals(16, service.getAll().size());
    }

    @Test
    public void shouldSaveNothingFromInvalidXML() {
        String xsdPath = new File(Objects.requireNonNull(this.getClass().getClassLoader()
                .getResource("EntitiesSchema.xsd")).getFile()).getAbsolutePath();
        XMLValidator xmlValidator = new XMLValidator(xsdPath);

        provider.register(CommandType.StAX_PARSER_COMMAND, new StAXParserCommand());
        BankDepositController controller = new BankDepositController(fileValidator, xmlValidator, provider, service);

        String xmlPath = new File(Objects.requireNonNull(this.getClass().getClassLoader()
                .getResource("InvalidEntitiyTagName.xml")).getFile()).getAbsolutePath();
        controller.buildFromXML(xmlPath, CommandType.StAX_PARSER_COMMAND);

        Assert.assertEquals(0, service.getAll().size());
    }

    @Test
    public void shouldSaveNothingFromEmptyXML() {
        String xsdPath = new File(Objects.requireNonNull(this.getClass().getClassLoader()
                .getResource("EntitiesSchema.xsd")).getFile()).getAbsolutePath();
        XMLValidator xmlValidator = new XMLValidator(xsdPath);

        provider.register(CommandType.StAX_PARSER_COMMAND, new StAXParserCommand());
        BankDepositController controller = new BankDepositController(fileValidator, xmlValidator, provider, service);

        String xmlPath = new File(Objects.requireNonNull(this.getClass().getClassLoader()
                .getResource("InvalidEmpty.xml")).getFile()).getAbsolutePath();
        controller.buildFromXML(xmlPath, CommandType.StAX_PARSER_COMMAND);

        Assert.assertEquals(0, service.getAll().size());
    }
}

package by.training.parser;

import by.training.entity.*;
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
public class DOMPDepositParserTest {

    private static List<BankDeposit> expectedValidDeposits;
    private Parser<BankDeposit> parser;

    @Before
    public void prepareBeforeTest() {
        parser = new DOMDepositParser();
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

        expectedValidDeposits = new ArrayList<>();
        expectedValidDeposits.add(demand);
        expectedValidDeposits.add(metal);
        expectedValidDeposits.add(savings);
        expectedValidDeposits.add(rated);
        expectedValidDeposits.add(funded);
    }

    @Test
    public void shouldParseFromValidXMLFirst() throws ParserException {
        String xmlPath = new File(Objects.requireNonNull(this.getClass().getClassLoader()
                .getResource("ValidEntitiesFirst.xml")).getFile()).getAbsolutePath();

        List<BankDeposit> parsed = parser.parse(xmlPath);

        Assert.assertEquals(expectedValidDeposits, parsed);
    }

    @Test
    public void shouldParseFromValidXMLSecond() throws ParserException {
        String xmlPath = new File(Objects.requireNonNull(this.getClass().getClassLoader()
                .getResource("ValidEntitiesSecond.xml")).getFile()).getAbsolutePath();

        List<BankDeposit> parsed = parser.parse(xmlPath);

        Assert.assertEquals(16, parsed.size());
    }

    @Test(expected = ParserException.class)
    public void shouldThrowExceptionAfterParsingFromEmptyXML() throws ParserException {
        String xmlPath = new File(Objects.requireNonNull(this.getClass().getClassLoader()
                .getResource("InvalidEmpty.xml")).getFile()).getAbsolutePath();

        parser.parse(xmlPath);
    }

    @Test(expected = ParserException.class)
    public void shouldThrowExceptionAfterParsingFromNotExistingXML() throws ParserException {
        String xmlPath = "NotExisting.xml";

        parser.parse(xmlPath);
    }
}

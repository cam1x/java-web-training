package by.training.command;

import by.training.entity.BankDeposit;
import by.training.parser.Parser;
import by.training.parser.ParserException;
import by.training.parser.StAXDepositParser;

import java.util.List;

public class StAXParserCommand implements Command<BankDeposit> {

    private Parser<BankDeposit> parser = new StAXDepositParser();

    @Override
    public List<BankDeposit> build(String path) throws StAXParserCommandException {
        try {
            return parser.parse(path);
        } catch (ParserException e) {
            throw new StAXParserCommandException(e);
        }
    }
}

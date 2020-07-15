package by.training.command;

import by.training.entity.BankDeposit;
import by.training.parser.DOMDepositParser;
import by.training.parser.Parser;
import by.training.parser.ParserException;

import java.util.List;

public class DOMParserCommand implements Command<BankDeposit> {

    private Parser<BankDeposit> parser = new DOMDepositParser();

    @Override
    public List<BankDeposit> build(String path) throws DOMParserCommandException {
        try {
            return parser.parse(path);
        } catch (ParserException e) {
            throw new DOMParserCommandException(e);
        }
    }
}

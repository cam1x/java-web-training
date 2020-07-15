package by.training.command;

import by.training.entity.BankDeposit;
import by.training.parser.Parser;
import by.training.parser.ParserException;
import by.training.parser.SAXDepositParser;

import java.util.List;

public class SAXParserCommand implements Command<BankDeposit> {

    private Parser<BankDeposit> parser = new SAXDepositParser();

    @Override
    public List<BankDeposit> build(String path) throws SAXParserCommandException {
        try {
            return parser.parse(path);
        } catch (ParserException e) {
            throw new SAXParserCommandException(e);
        }
    }
}

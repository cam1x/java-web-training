package by.training.parser;

import by.training.entity.BankDeposit;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;
import java.util.List;

public class SAXDepositParser implements Parser<BankDeposit> {

    @Override
    public List<BankDeposit> parse(String xmlPath) throws SAXParserException {
        SAXParserFactory parserFactory = SAXParserFactory.newInstance();
        SAXParser parser;

        try {
            parser = parserFactory.newSAXParser();
        } catch (ParserConfigurationException | SAXException e) {
            throw new SAXParserException(e);
        }

        SAXHandler handler = new SAXHandler();

        try {
            parser.parse(xmlPath, handler);
        } catch (SAXException | IOException e) {
            throw new SAXParserException(e);
        }

        return handler.getDepositList();
    }
}

package by.training.command;

import by.training.parsing.Parser;
import by.training.parsing.ParserException;
import by.training.parsing.SAXShipParser;
import by.training.thread.Ship;

import java.util.List;

public class SAXParserCommand implements Command<Ship> {
    private Parser<Ship> parser = new SAXShipParser();

    @Override
    public List<Ship> build(String shipXMLPath, String poolXMLPath) throws SAXParserCommandException {
        try {
            return parser.parse(shipXMLPath, poolXMLPath);
        } catch (ParserException e) {
            throw new SAXParserCommandException(e);
        }
    }
}
package by.training.parsing;

import by.training.thread.BerthPool;
import by.training.thread.Ship;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;
import java.util.List;

public class SAXShipParser implements Parser<Ship> {
    @Override
    public List<Ship> parse(String shipXMLPath, String poolXMLPath) throws ParserException {
        SAXParserFactory parserFactory = SAXParserFactory.newInstance();
        SAXParser parser;
        try {
            parser = parserFactory.newSAXParser();
        } catch (ParserConfigurationException | SAXException e) {
            throw new SAXParserException(e);
        }

        SAXPoolHandler poolHandler = new SAXPoolHandler();
        try {
            parser.parse(poolXMLPath, poolHandler);
        } catch (SAXException | IOException e) {
            throw new SAXParserException(e);
        }

        BerthPool pool = poolHandler.getPool();
        SAXShipHandler shipHandler = new SAXShipHandler(pool);
        try {
            parser.parse(shipXMLPath, shipHandler);
        } catch (SAXException | IOException e) {
            throw new SAXParserException(e);
        }

        return shipHandler.getShipList();
    }
}

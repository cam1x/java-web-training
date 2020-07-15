package by.training.parsing;

import by.training.thread.*;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.List;

import static by.training.parsing.ShipParserConstants.*;

public class SAXShipHandler extends DefaultHandler {
    private String content;
    private List<Ship> shipList = new ArrayList<>();
    private BerthPool pool;
    private ShipType shipType;
    private String shipName;
    private long capacity;
    private long congestion;
    private ShipOperationType operationType;

    public SAXShipHandler(BerthPool pool) {
        this.pool = pool;
    }

    public List<Ship> getShipList() {
        return shipList;
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) {
        if (SHIP.equals(qName)) {
            String type = attributes.getValue(SHIP_TYPE).toUpperCase();
            shipType = ShipType.valueOf(type.toUpperCase());
            shipName = attributes.getValue(NAME);
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) {
        switch (qName) {
            case SHIP: {
                Ship ship = new Ship(shipType, shipName, capacity, congestion, operationType, pool);
                shipList.add(ship);
                break;
            }
            case CAPACITY: {
                capacity = Long.parseLong(content);
                break;
            }
            case CONGESTION: {
                congestion = Long.parseLong(content);
                break;
            }
            case OPERATION_TYPE: {
                operationType = ShipOperationType.valueOf(content.toUpperCase());
                break;
            }
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) {
        content = String.copyValueOf(ch, start, length).trim();
    }
}

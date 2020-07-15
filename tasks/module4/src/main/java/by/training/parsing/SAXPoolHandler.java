package by.training.parsing;

import by.training.thread.Berth;
import by.training.thread.BerthPool;
import by.training.thread.Port;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static by.training.parsing.PoolParserConstants.*;

public class SAXPoolHandler extends DefaultHandler {
    private String content;
    private BerthPool pool;
    private TimeUnit timeUnit;
    private long millis;
    private Port port;
    private long capacity;
    private long congestion;
    private Berth berth;
    private List<Berth> berthList;

    public BerthPool getPool() {
        return pool;
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) {
        switch (qName) {
            case BERTHS: {
                berthList = new ArrayList<>();
                break;
            }
            case BERTH: {
                String name = attributes.getValue(NAME);
                berth = new Berth(name);
                break;
            }
            case TIME_OUT_LIMIT: {
                String unit = attributes.getValue(TIME_OUT_UNIT);
                timeUnit = TimeUnit.valueOf(unit.toUpperCase());
                break;
            }
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) {
        switch (qName) {
            case POOL: {
                pool = new BerthPool(port, timeUnit, millis);
                break;
            }
            case PORT: {
                port = new Port(capacity, congestion, berthList);
                break;
            }
            case BERTH: {
                berthList.add(berth);
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
            case TIME_OUT_LIMIT: {
                millis = Long.parseLong(content);
                break;
            }
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) {
        content = String.copyValueOf(ch, start, length).trim();
    }
}

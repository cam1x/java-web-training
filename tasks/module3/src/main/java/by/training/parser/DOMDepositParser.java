package by.training.parser;

import by.training.entity.*;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static by.training.parser.BankDepositParserConstants.*;

public class DOMDepositParser implements Parser<BankDeposit> {

    @Override
    public List<BankDeposit> parse(String xmlPath) throws ParserException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;

        try {
            builder = factory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            throw new DOMParserException(e);
        }

        Document document;
        try {
            document = builder.parse(xmlPath);
        } catch (SAXException | IOException e) {
            throw new DOMParserException(e);
        }

        List<BankDeposit> depositList = new ArrayList<>();
        NodeList nodeList = document.getDocumentElement().getChildNodes();

        for (int i=0; i<nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            if (node instanceof Element) {
                BankDeposit deposit;
                switch (node.getNodeName()) {
                    case DEMAND: {
                        deposit = new DemandDeposit();
                        break;
                    }
                    case METAL: {
                        deposit = new MetalDeposit();
                        break;
                    }
                    case SAVINGS: {
                        deposit = new SavingsDeposit();
                        break;
                    }
                    case RATED: {
                        deposit = new RatedDeposit();
                        break;
                    }
                    case FUNDED: {
                        deposit = new FundedDeposit();
                        break;
                    }
                    default: {
                        throw new DOMParserException("Not valid name of entity element!");
                    }
                }

                long id = Long.parseLong(node.getAttributes().getNamedItem(ID).getNodeValue());
                deposit.setAccountId(id);
                NodeList childNodes = node.getChildNodes();

                for (int j=0; j<childNodes.getLength(); j++) {
                    Node insideNode = childNodes.item(j);
                    if (insideNode instanceof Element) {
                        String content = insideNode.getLastChild().getTextContent().trim();
                        switch (insideNode.getNodeName()) {
                            case NAME: {
                                deposit.setBankName(content);
                                break;
                            }

                            case COUNTRY: {
                                deposit.setCountry(content);
                                break;
                            }

                            case DEPOSITOR: {
                                deposit.setDepositor(content);
                                break;
                            }

                            case AMOUNT_ON_DEPOSIT: {
                                deposit.setAmountOnDeposit(Double.parseDouble(content));
                                String depositUnit = insideNode.getAttributes().getNamedItem(UNIT).getNodeValue();
                                deposit.setDepositUnit(depositUnit);
                                if (deposit instanceof MetalDeposit) {
                                    String preciousMetal = insideNode.getAttributes().getNamedItem(PRECIOUS_METAL).getNodeValue();
                                    ((MetalDeposit)deposit).setPreciousMetal(preciousMetal);
                                }
                                break;
                            }

                            case PROFITABILITY: {
                                deposit.setProfitability(Double.parseDouble(content));
                                break;
                            }

                            case TIME_CONSTRAINS: {
                                if (deposit instanceof TermDeposit) {
                                    String timeUnit = insideNode.getAttributes().getNamedItem(UNIT).getNodeValue().toUpperCase();
                                    TimeUnitType type = TimeUnitType.valueOf(timeUnit);
                                    ((TermDeposit)deposit).setTimeUnitType(type);
                                    ((TermDeposit)deposit).setTimeConstraints(Long.parseLong(content));
                                }
                                break;
                            }

                            case MAX_NUM_OF_REPLENISH: {
                                if (deposit instanceof RatedDeposit) {
                                    ((RatedDeposit)deposit).setMaxNumOfReplenish(Integer.parseInt(content));
                                } else {
                                    if (deposit instanceof FundedDeposit) {
                                        ((FundedDeposit)deposit).setMaxNumOfReplenish(Integer.parseInt(content));
                                    }
                                }
                                break;
                            }

                            case MAX_NUM_OF_WITHDRAWAL: {
                                if (deposit instanceof RatedDeposit) {
                                    ((RatedDeposit)deposit).setMaxNumOfWithdrawals(Integer.parseInt(content));
                                }
                                break;
                            }
                        }
                    }
                }
                depositList.add(deposit);
            }
        }

        return depositList;
    }
}

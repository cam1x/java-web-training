package by.training.parser;

import by.training.entity.*;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import static by.training.parser.BankDepositParserConstants.*;

public class StAXDepositParser implements Parser<BankDeposit> {

    private List<BankDeposit> depositList = new ArrayList<>();
    private BankDeposit deposit;
    private String content;
    private String depositUnit;
    private String metalType;
    private String timeUnit;

    @Override
    public List<BankDeposit> parse(String xmlPath) throws StAXParserException {
        XMLInputFactory factory = XMLInputFactory.newInstance();
        try {
            XMLStreamReader reader = factory.createXMLStreamReader(new FileInputStream(xmlPath));
            while (reader.hasNext()) {
                int event = reader.next();
                switch (event) {
                    case XMLStreamConstants.START_ELEMENT: {
                        switch (reader.getLocalName()) {
                            case DEMAND: {
                                deposit = new DemandDeposit();
                                String idValue = reader.getAttributeValue("", ID);
                                long id = Long.parseLong(idValue);
                                deposit.setAccountId(id);
                                break;
                            }

                            case METAL: {
                                deposit = new MetalDeposit();
                                String idValue = reader.getAttributeValue("", ID);
                                long id = Long.parseLong(idValue);
                                deposit.setAccountId(id);
                                break;
                            }

                            case SAVINGS: {
                                deposit = new SavingsDeposit();
                                String idValue = reader.getAttributeValue("", ID);
                                long id = Long.parseLong(idValue);
                                deposit.setAccountId(id);
                                break;
                            }

                            case RATED: {
                                deposit = new RatedDeposit();
                                String idValue = reader.getAttributeValue("", ID);
                                long id = Long.parseLong(idValue);
                                deposit.setAccountId(id);
                                break;
                            }

                            case FUNDED: {
                                deposit = new FundedDeposit();
                                String idValue = reader.getAttributeValue("", ID);
                                long id = Long.parseLong(idValue);
                                deposit.setAccountId(id);
                                break;
                            }

                            case AMOUNT_ON_DEPOSIT: {
                                depositUnit = reader.getAttributeValue("", UNIT);
                                metalType = reader.getAttributeValue("", PRECIOUS_METAL);
                                break;
                            }

                            case TIME_CONSTRAINS: {
                                timeUnit = reader.getAttributeValue("", UNIT);
                                break;
                            }
                        }
                        break;
                    }

                    case XMLStreamConstants.CHARACTERS: {
                        content = reader.getText().trim();
                        break;
                    }

                    case XMLStreamConstants.END_ELEMENT: {
                        switch (reader.getLocalName()) {
                            case DEMAND:
                            case METAL:
                            case SAVINGS:
                            case RATED:
                            case FUNDED: {
                                depositList.add(deposit);
                                break;
                            }

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
                                deposit.setDepositUnit(depositUnit);
                                if (deposit instanceof MetalDeposit) {
                                    ((MetalDeposit)deposit).setPreciousMetal(metalType);
                                }
                                break;
                            }

                            case PROFITABILITY: {
                                deposit.setProfitability(Double.parseDouble(content));
                                break;
                            }

                            case TIME_CONSTRAINS: {
                                if (deposit instanceof TermDeposit) {
                                    TimeUnitType type = TimeUnitType.valueOf(timeUnit.toUpperCase());
                                    ((TermDeposit)deposit).setTimeUnitType(type);
                                    ((TermDeposit)deposit).setTimeConstraints(Long.parseLong(content));
                                }
                                break;
                            }

                            case MAX_NUM_OF_REPLENISH: {
                                if (deposit.getClass() == RatedDeposit.class) {
                                    ((RatedDeposit)deposit).setMaxNumOfReplenish(Integer.parseInt(content));
                                } else {
                                    if (deposit.getClass() == FundedDeposit.class) {
                                        ((FundedDeposit)deposit).setMaxNumOfReplenish(Integer.parseInt(content));
                                    }
                                }
                                break;
                            }

                            case MAX_NUM_OF_WITHDRAWAL: {
                                if (deposit.getClass() == RatedDeposit.class) {
                                    ((RatedDeposit)deposit).setMaxNumOfWithdrawals(Integer.parseInt(content));
                                }
                                break;
                            }
                        }
                        break;
                    }
                }
            }
        } catch (XMLStreamException | FileNotFoundException e) {
            throw new StAXParserException(e);
        }

        return depositList;
    }
}

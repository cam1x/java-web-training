package by.training.parser;

import by.training.entity.*;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.List;

import static by.training.parser.BankDepositParserConstants.*;

class SAXHandler extends DefaultHandler {

    private List<BankDeposit> depositList = new ArrayList<>();
    private BankDeposit deposit;
    private String content;
    private String depositUnit;
    private String metalType;
    private String timeUnit;

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) {
        switch (qName) {
            case DEMAND: {
                deposit = new DemandDeposit();
                long id = Long.parseLong(attributes.getValue(ID));
                deposit.setAccountId(id);
                break;
            }

            case METAL: {
                deposit = new MetalDeposit();
                long id = Long.parseLong(attributes.getValue(ID));
                deposit.setAccountId(id);
                break;
            }

            case SAVINGS: {
                deposit = new SavingsDeposit();
                long id = Long.parseLong(attributes.getValue(ID));
                deposit.setAccountId(id);
                break;
            }

            case RATED: {
                deposit = new RatedDeposit();
                long id = Long.parseLong(attributes.getValue(ID));
                deposit.setAccountId(id);
                break;
            }

            case FUNDED: {
                deposit = new FundedDeposit();
                long id = Long.parseLong(attributes.getValue(ID));
                deposit.setAccountId(id);
                break;
            }

            case AMOUNT_ON_DEPOSIT: {
                depositUnit = attributes.getValue(UNIT);
                metalType = attributes.getValue(PRECIOUS_METAL);
                break;
            }

            case TIME_CONSTRAINS: {
                timeUnit = attributes.getValue(UNIT);
                break;
            }
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) {
        switch (qName) {
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
    }

    @Override
    public void characters(char[] ch, int start, int length) {
        content = String.copyValueOf(ch, start, length).trim();
    }

    public List<BankDeposit> getDepositList() {
        return depositList;
    }
}

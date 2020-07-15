package by.training.entity;

import java.util.Objects;

/**
 * Implements and expands the possibilities of a bank deposit:
 * contains deposit currency
 */
public class DemandDeposit extends BankDeposit {
    public DemandDeposit() {
        this("BPS", "Belarus", "Unknown",
                42,"USD", 0,  10);
    }

    public DemandDeposit(String bankName, String country, String depositor,
                         long accountId, String depositUnit, double amountOnDeposit, double profitability) {
        super(DepositType.DEMAND, bankName, country, depositor, accountId, depositUnit, amountOnDeposit, profitability);
    }
}

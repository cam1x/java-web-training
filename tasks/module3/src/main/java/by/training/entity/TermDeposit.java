package by.training.entity;

/**
 * Abstract class representing a term deposit
 * Expands the possibilities of a bank deposit:
 * contains the term of the deposit and time unit
 */
public abstract class TermDeposit extends BankDeposit {
    private TimeUnitType timeUnitType;
    private long timeConstraints;

    public TermDeposit(DepositType type, String bankName, String country, String depositor, long accountId, String depositUnit,
                       double amountOnDeposit, double profitability, TimeUnitType timeUnitType, long timeConstraints) {
        super(type, bankName, country, depositor, accountId,depositUnit, amountOnDeposit, profitability);
        this.timeUnitType = timeUnitType;
        this.timeConstraints = timeConstraints;
    }

    public void setTimeUnitType(TimeUnitType timeUnitType) {
        this.timeUnitType = timeUnitType;
    }

    public void setTimeConstraints(long timeConstraints) {
        this.timeConstraints = timeConstraints;
    }
}

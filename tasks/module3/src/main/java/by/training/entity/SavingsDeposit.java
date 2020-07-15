package by.training.entity;

/**
 * Realization of a fixed deposit without the possibility
 * of replenishment and withdrawal from the account
 */
public class SavingsDeposit extends TermDeposit {
    public SavingsDeposit() {
        this("BPS", "Belarus", "Unknown", 42, "USD", 0,
                10, TimeUnitType.YEAR, 1);
    }

    public SavingsDeposit(String bankName, String country, String depositor, long accountId, String depositUnit,
                          double amountOnDeposit, double profitability,
                          TimeUnitType timeUnitType, long timeConstraints) {
        super(DepositType.SAVINGS, bankName, country, depositor, accountId, depositUnit,
                amountOnDeposit, profitability, timeUnitType, timeConstraints);
    }
}

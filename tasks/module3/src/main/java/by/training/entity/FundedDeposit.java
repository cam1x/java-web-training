package by.training.entity;

import java.util.Objects;

/**
 * Expands the possibilities of a term deposit:
 * allows to replenish account,
 * contains max number of replenishment
 */
public class FundedDeposit extends TermDeposit  {
    private int maxNumOfReplenish;

    public FundedDeposit() {
        this("BPS", "Belarus", "Unknown", 42,"USD", 0,
                10, TimeUnitType.YEAR, 1, 3);
    }

    public FundedDeposit(String bankName, String country, String depositor,
                         long accountId, String depositUnit, double amountOnDeposit,
                         double profitability, TimeUnitType timeUnitType,
                         long timeConstraints, int maxNumOfReplenish) {
        super(DepositType.FUNDED, bankName, country, depositor, accountId, depositUnit,
                amountOnDeposit, profitability, timeUnitType, timeConstraints);
        this.maxNumOfReplenish = maxNumOfReplenish;
    }

    public int getMaxNumOfReplenish() {
        return maxNumOfReplenish;
    }

    public void setMaxNumOfReplenish(int maxNumOfReplenish) {
        this.maxNumOfReplenish = maxNumOfReplenish;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FundedDeposit that = (FundedDeposit) o;
        return maxNumOfReplenish == that.maxNumOfReplenish;
    }

    @Override
    public int hashCode() {
        return Objects.hash(maxNumOfReplenish);
    }
}

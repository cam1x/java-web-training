package by.training.entity;

import java.util.Objects;

/**
 * Expands the possibilities of a term deposit:
 * allows to replenish and withdraw from the account
 * contains max number of replenishment/withdrawal
 */
public class RatedDeposit extends TermDeposit {
    private int maxNumOfWithdrawals;
    private int maxNumOfReplenish;

    public RatedDeposit() {
        this("BPS", "Belarus", "Unknown", 42,"USD", 0,
                10, TimeUnitType.YEAR, 1, 3, 3);
    }

    public RatedDeposit(String bankName, String country, String depositor,
                        long accountId, String depositUnit, double amountOnDeposit, double profitability,
                        TimeUnitType timeUnitType, long timeConstraints,
                        int maxNumOfReplenish, int maxNumOfWithdrawals) {
        super(DepositType.RATED, bankName, country, depositor, accountId, depositUnit,
                amountOnDeposit, profitability, timeUnitType, timeConstraints);
        this.maxNumOfReplenish = maxNumOfReplenish;
        this.maxNumOfWithdrawals = maxNumOfWithdrawals;
    }

    public int getMaxNumOfWithdrawals() {
        return maxNumOfWithdrawals;
    }

    public int getMaxNumOfReplenish() {
        return maxNumOfReplenish;
    }

    public void setMaxNumOfWithdrawals(int maxNumOfWithdrawals) {
        this.maxNumOfWithdrawals = maxNumOfWithdrawals;
    }

    public void setMaxNumOfReplenish(int maxNumOfReplenish) {
        this.maxNumOfReplenish = maxNumOfReplenish;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RatedDeposit that = (RatedDeposit) o;
        return maxNumOfWithdrawals == that.maxNumOfWithdrawals &&
                maxNumOfReplenish == that.maxNumOfReplenish;
    }

    @Override
    public int hashCode() {
        return Objects.hash(maxNumOfWithdrawals, maxNumOfReplenish);
    }
}

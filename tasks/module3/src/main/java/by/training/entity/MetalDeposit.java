package by.training.entity;

import java.util.Objects;

/**
 * Expands the possibilities of a term deposit:
 * allows to store precious metals
 */
public class MetalDeposit extends TermDeposit {
    private String preciousMetal;

    public MetalDeposit() {
        this("BPS", "Belarus", "Unknown", 42,"gram", 0,
                10, TimeUnitType.YEAR, 1, "silver");
    }

    public MetalDeposit(String bankName, String country, String depositor, long accountId, String depositUnit,
                        double amountOnDeposit, double profitability, TimeUnitType timeUnitType,
                        long timeConstraints, String preciousMetal) {
        super(DepositType.METAL, bankName, country, depositor, accountId, depositUnit,
                amountOnDeposit, profitability, timeUnitType, timeConstraints);
        this.preciousMetal = preciousMetal;
    }

    public String getPreciousMetal() {
        return preciousMetal;
    }

    public void setPreciousMetal(String preciousMetal) {
        this.preciousMetal = preciousMetal;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MetalDeposit that = (MetalDeposit) o;
        return Objects.equals(preciousMetal, that.preciousMetal);
    }

    @Override
    public int hashCode() {
        return Objects.hash(preciousMetal);
    }
}

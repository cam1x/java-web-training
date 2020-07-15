package by.training.entity;

import java.util.Objects;

/**
 * Abstract class representing bank deposit
 */
public abstract class BankDeposit {
    private DepositType type;
    private String bankName;
    private String country;
    private String depositor;
    private long accountId;
    private String depositUnit;
    private double amountOnDeposit;
    private double profitability;

    public BankDeposit(DepositType type, String bankName, String country, String depositor,
                       long accountId, String depositUnit, double amountOnDeposit, double profitability) {
        this.type = type;
        this.bankName = bankName;
        this.country = country;
        this.depositor = depositor;
        this.accountId = accountId;
        this.depositUnit = depositUnit;
        this.amountOnDeposit = amountOnDeposit;
        this.profitability = profitability;
    }

    public void setDepositUnit(String depositUnit) {
        this.depositUnit = depositUnit;
    }

    public DepositType getType() {
        return type;
    }

    public String getBankName() {
        return bankName;
    }

    public String getCountry() {
        return country;
    }

    public String getDepositor() {
        return depositor;
    }

    public long getAccountId() {
        return accountId;
    }

    public String getDepositUnit() {
        return depositUnit;
    }

    public double getAmountOnDeposit() {
        return amountOnDeposit;
    }

    public double getProfitability() {
        return profitability;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setDepositor(String depositor) {
        this.depositor = depositor;
    }

    public void setAccountId(long accountId) {
        this.accountId = accountId;
    }

    public void setAmountOnDeposit(double amountOnDeposit) {
        this.amountOnDeposit = amountOnDeposit;
    }

    public void setProfitability(double profitability) {
        this.profitability = profitability;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BankDeposit that = (BankDeposit) o;
        return accountId == that.accountId &&
                Double.compare(that.amountOnDeposit, amountOnDeposit) == 0 &&
                Double.compare(that.profitability, profitability) == 0 &&
                type == that.type &&
                Objects.equals(bankName, that.bankName) &&
                Objects.equals(country, that.country) &&
                Objects.equals(depositor, that.depositor) &&
                Objects.equals(depositUnit, that.depositUnit);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, bankName, country, depositor, accountId, depositUnit, amountOnDeposit, profitability);
    }
}

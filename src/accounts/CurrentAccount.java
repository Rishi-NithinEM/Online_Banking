package accounts;

public class CurrentAccount extends Account {

    private int creditLimit;
    private int withdrawLimit;
    private double interestRate;

    public double getInterestRate() {
        return this.interestRate;
    }

    public void setInterestRate(double interestRate) {
        this.interestRate = interestRate;
    }

    public int getCreditLimit() {
        return this.creditLimit;
    }

    public void setCreditLimit(int creditLimit) {
        this.creditLimit = creditLimit;
    }

    public int getWithdrawLimit() {
        return this.withdrawLimit;
    }

    public void setWithdrawLimit(int withdrawLimit) {
        this.withdrawLimit = withdrawLimit;
    }

    public CurrentAccount() {
        this.setBalance(10000);
        this.setCreditLimit(50000);
        this.setInterestRate(0.45D);
        this.setWithdrawLimit(100000);
    }

}

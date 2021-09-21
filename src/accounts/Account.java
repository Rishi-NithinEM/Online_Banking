package accounts;

import banking.Branch;

public class Account {

    private int customerID;
    private int accountNo;
    private int balance;
    private int pin;
    private AccountType accType;
    private Branch.BrachName brachName;
    private String ifscCode;


    public Account() {
    }

    public int getCustomerID() {
        return this.customerID;
    }

    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    public void setBrachName(int index) {
        this.brachName = Branch.BrachName.values()[index];
        setIfscCode(brachName.getIFSCCode());
    }

    public void setBrachName(String val) {
        this.brachName = Branch.BrachName.valueOf(val);
    }

    public void setIfscCode(String val){
        this.ifscCode = val;
    }

    public String getIfscCode(){
        return this.ifscCode;
    }

    public Branch.BrachName getBrachName() {
        return this.brachName;
    }

    public int getAccountNo() {
        return this.accountNo;
    }

    public void setAccountNo(int accountNo) {
        this.accountNo = accountNo;
    }

    public AccountType getAccType() {
        return this.accType;
    }

    public void setAccType(int index) {
        this.accType = AccountType.values()[index];
    }

    public void setAccType(String val) {
        this.accType = AccountType.valueOf(val);
    }



    public int getPin() {
        return this.pin;
    }

    public void setPin(int pin) {
        this.pin = pin;
    }

    public int getBalance() {
        return this.balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }



    public static enum AccountType {
        Savings,
        Current,
        Deposit;

        private AccountType() {
        }
    }

}

package main;

import accounts.Account;
import banking.Transaction;
import customer.Customer;

import java.io.IOException;
import java.util.*;

public class TransactionFunctions {



    public void transfer(Customer cust, Transaction tr) throws IOException {
        Scanner sc = new Scanner(System.in);
        tr.setCustomerId(cust.getCustomerID());
        int accNo;
        Account acc;
        int raccNo;
        Account racc;
        while (true) {
            System.out.println("Sender Account no");
            try {
                accNo = sc.nextInt();
            } catch (Exception e) {
                System.out.println("Enter only numbers");
                continue;
            }
            acc = checkAccountNo(accNo, cust);

            if (acc != null) {
                tr.setSenderAccNo(accNo);


                if (DataHandler.getBeneficiaryfromFile(accNo) != "") {

                    System.out.println("Select a beneficiary Account: \n");

                    String account[] = DataHandler.getBeneficiaryfromFile(accNo).split(";");
                    int i = 1;
                    for (String s : account) {
                        System.out.println(i++ + "  " + s);
                    }
                    System.out.println(i + "  Add new beneficiary account");
                    int val = 0;
                    try {
                        val = sc.nextInt();
                    } catch (Exception e) {
                        System.out.println("Enter only number between 1 to " + account.length + 1);
                        continue;
                    }

                    if (val <= account.length && val > 0) {

                        raccNo = Integer.parseInt(account[val - 1]);
                        racc = DataHandler.getAccountfromFile(raccNo);
                        tr.setReceiverAccNo(racc.getAccountNo());

                        System.out.println("Enter transaction amt");
                        int amt = sc.nextInt();

                        if (amt < 1) {
                            System.out.println("Invalid Amount");
                        } else {
                            if (acc.getBalance() - amt >= 0) {
                                tr.setTransactionAmt(amt);
                                System.out.println("Enter Pin :");
                                int pin = sc.nextInt();
                                if (acc.getPin() == pin) {
                                    acc.setBalance(acc.getBalance() - amt);
                                    racc.setBalance(racc.getBalance() + amt);
                                    FileHandling.changeBalance(acc);
                                    FileHandling.changeBalance(racc);
                                    tr.setCurrentBalance(acc.getBalance());
                                    tr.setTransactionID(FileHandling.getLastTranId());
                                    tr.setTranactionTime(new Date().toString());
                                    DataHandler.addTransactiontoFile(tr);
                                    Operations.printTransaction(tr);
                                } else {
                                    System.out.println("Wrong pin");
                                    continue;
                                }
                            } else {
                                System.out.println("Sorry transfer amount exceeds the balance");
                                continue;
                            }
                        }

                        break;

                    } else {
                        if (val == account.length + 1) {
                            System.out.println("Adding new beneficiary\n");
                            addBeneficiary(acc);
                            continue;
                        }else {
                            System.out.println("Enter only number between 1 to " + account.length);
                            continue;
                        }
                    }
                } else {
                    System.out.println("Adding new beneficiary\n");
                    addBeneficiary(acc);
                    continue;
                }
            } else {
                return;
            }
        }
    }

    public Account addBeneficiary(Account acc) throws IOException {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter Beneficiary Account number");
        Account racc;
        int accnNo = Integer.parseInt(sc.nextLine());
        if (accnNo != acc.getAccountNo() && checkBeneficiary(acc.getAccountNo(),accnNo)) {
            racc = DataHandler.getAccountfromFile(accnNo);
            if (racc != null) {
                System.out.println("Enter Beneficiary Account IFSC code");
                String code = sc.nextLine();

                if (racc.getIfscCode().equals(code)) {

                    DataHandler.addBeneficiarytoFile(acc.getAccountNo(), accnNo);
                    System.out.println("New beneficiary Added");
                    return racc;

                } else {
                    System.out.println("Wrong IFSC code");
                    return null;
                }
            }else
                System.out.println("Sorry account does not exist");
        } else {
            System.out.println("Cant enter same account number or Account already exists");
            return null;
        }

        return null;
    }

    public boolean checkBeneficiary(int accNo,int benNo) throws IOException {

       String st = DataHandler.getBeneficiaryfromFile(accNo);
       if(st==""){
           return true;
       }else {
           String no[] = st.split(";");
           for(String s: no){
               if(benNo == Integer.parseInt(s)){
                   return false;
               }
           }
           return true;
       }

    }


    public void checkBalance(Customer cust) throws IOException {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter Account no");
        int accNo = Integer.parseInt(sc.nextLine());
        Account acc = checkAccountNo(accNo, cust);
        if (acc != null) {
            System.out.println("Enter Pin :");
            int pin = Integer.parseInt(sc.nextLine());
            if (acc.getPin() == pin) {
                System.out.println("Balance : " + acc.getBalance());
            } else {
                System.out.println("Wrong Pin");
            }
        }
    }


    public static Account checkAccountNo(int accNo, Customer cust) throws IOException {

        List<Account> accountList = DataHandler.getAccountList();
        if (accountList != null) {
            for (Account acc : accountList) {
                if (acc.getAccountNo() == accNo && acc.getCustomerID() == cust.getCustomerID()) {
                    return acc;
                } else
                    continue;
            }
            System.out.println("Sorry No Account found of that Account Number");
        } else {
            System.out.println("No Account created");
        }
        return null;
    }

//    public Account checkAccountNo(int accNo) throws IOException {
//
//
//        Iterator var5 = DataHandler.getAccountList().iterator();
//
//        while (var5.hasNext()) {
//            Account acc = (Account) var5.next();
//
//            if (acc.getAccountNo() == accNo) {
//                return acc;
//            }
//        }
//
//        return null;
//    }


}

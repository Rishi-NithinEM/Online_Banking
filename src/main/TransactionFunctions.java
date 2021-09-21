package main;

import accounts.Account;
import banking.Transaction;
import customer.Customer;

import java.io.IOException;
import java.util.*;

public class TransactionFunctions {

    public static FileHandling fileHandling = new FileHandling();


//    public void deposit(Customer cust, Transaction tr) throws IOException {
//        Scanner sc = new Scanner(System.in);
//        tr.setCustomerId(cust.getCustomerID());
//        System.out.println("Account no");
//        int accNo = Integer.parseInt(sc.nextLine());
//        Account acc = checkAccountNo(accNo, cust);
//        if (acc != null) {
//            tr.setSenderAccNo(accNo);
//            System.out.println("Enter Pin :");
//            int pin = Integer.parseInt(sc.nextLine());
//            if (acc.getPin() == pin) {
//                System.out.println("Enter transaction amt");
//                int amt = Integer.parseInt(sc.nextLine());
//                if (amt < 1) {
//                    System.out.println("Invalid Amount");
//                } else {
//                    tr.setTransactionAmt(amt);
//                    acc.setBalance(acc.getBalance() + amt);
//                    tr.setCurrentBalance(acc.getBalance());
////                    tr.setTransactionID(BankingMain.TransactionId++);
//                    tr.setTranactionTime(new Date());
////                    BankingMain.transactionList.add(tr);
//                    Operations.printTransaction(tr);
//                }
//            } else {
//                System.out.println("Wrong Pin");
//            }
//        } else
//            return;
//    }
//
//    public void withdraw(Customer cust, Transaction tr) throws IOException {
//        Scanner sc = new Scanner(System.in);
//        tr.setCustomerId(cust.getCustomerID());
//        System.out.println("Account no");
//        int accNo = Integer.parseInt(sc.nextLine());
//        Account acc = checkAccountNo(accNo, cust);
//        if (acc != null) {
//            tr.setSenderAccNo(accNo);
//            System.out.println("Enter Pin :");
//            int pin = Integer.parseInt(sc.nextLine());
//            if (acc.getPin() == pin) {
//                System.out.println("Enter transaction amt");
//                int amt = Integer.parseInt(sc.nextLine());
//                if (amt < 1) {
//                    System.out.println("Invalid Amount");
//                } else {
//                    if (acc.getBalance() - amt >= 0) {
//                        tr.setTransactionAmt(amt);
//                        acc.setBalance(acc.getBalance() - amt);
//                        tr.setCurrentBalance(acc.getBalance());
////                        tr.setTransactionID(BankingMain.TransactionId++);
//                        tr.setTranactionTime(new Date());
//                        Operations.printTransaction(tr);
//                    } else {
//                        System.out.println("Sorry withdraw amount exceeds the balance");
//                    }
//                }
//            } else {
//                System.out.println("Wrong Pin");
//            }
//        } else
//            return;
//    }

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


                if (fileHandling.getBeneficiaryfromFile(accNo) != "") {

                    System.out.println("Select a beneficiary Account: \n");

                    String account[] = fileHandling.getBeneficiaryfromFile(accNo).split(";");
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
                        racc = fileHandling.getAccountfromFile(raccNo);
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
                                    fileHandling.changeBalance(acc);
                                    fileHandling.changeBalance(racc);
                                    tr.setCurrentBalance(acc.getBalance());
                                    tr.setTransactionID(fileHandling.getLastTranId());
                                    tr.setTranactionTime(new Date().toString());
                                    fileHandling.addTransactiontoFile(tr);
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
            racc = fileHandling.getAccountfromFile(accnNo);
            if (racc != null) {
                System.out.println("Enter Beneficiary Account IFSC code");
                String code = sc.nextLine();

                if (racc.getIfscCode().equals(code)) {

                    fileHandling.addBeneficiarytoFile(acc.getAccountNo(), accnNo);
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

       String st = fileHandling.getBeneficiaryfromFile(accNo);
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


    public Account checkAccountNo(int accNo, Customer cust) throws IOException {

        List<Account> accountList = fileHandling.getAccountList();
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

    public Account checkAccountNo(int accNo) throws IOException {


        Iterator var5 = fileHandling.getAccountList().iterator();

        while (var5.hasNext()) {
            Account acc = (Account) var5.next();

            if (acc.getAccountNo() == accNo) {
                return acc;
            }
        }

        return null;
    }


}

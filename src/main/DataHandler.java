package main;

import accounts.Account;
import banking.Address;
import banking.Transaction;
import customer.Customer;
import employee.Employee;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

public class DataHandler {

    private static FileHandling fileHandling = new FileHandling();


    public static void addCustomertoFile(Customer cust) throws IOException{
        fileHandling.addCustomertoFile(cust);
    }

    public static void addEmployeetoFile(Employee emp) throws IOException{
        fileHandling.addEmployeetoFile(emp);
    }

    public static void addAddresstoFile(Address address) throws IOException{
        fileHandling.addAddresstoFile(address);
    }

    public static void addAccounttoFile(Account acc) throws IOException{
        fileHandling.addAccounttoFile(acc);
    }

    public static void addBeneficiarytoFile(int accNo, int benNo) throws IOException{
        fileHandling.addBeneficiarytoFile(accNo,benNo);
    }

    public static void addTransactiontoFile(Transaction tr) throws IOException{
        fileHandling.addTransactiontoFile(tr);
    }

    public static Customer getCustomerfromFile(int custId) throws IOException{
        return fileHandling.getCustomerfromFile(custId);
    }

    public static Employee getEmployeefromFile(int empId) throws IOException{
       return fileHandling.getEmployeefromFile(empId);
    }

    public static Account getAccountfromFile(int accNo) throws IOException{
        return fileHandling.getAccountfromFile(accNo);
    }

    public static Transaction getTransactionfromFile(int custId) throws IOException, ParseException {
        return fileHandling.getTransactionfromFile(custId);
    }

    public static String getBeneficiaryfromFile(int accNo) throws IOException{
        return fileHandling.getBeneficiaryfromFile(accNo);
    }

    public static List<Customer> getCustomerList() throws IOException{
        return fileHandling.getCustomerList();
    }

    public static List<Employee> getEmployeeList() throws IOException{
        return fileHandling.getEmployeeList();
    }

    public static List<Account> getAccountList() throws IOException{
        return fileHandling.getAccountList();
    }

    public static List<Transaction> getTransactionList() throws IOException, ParseException {
        return fileHandling.getTransactionList();
    }


}

package main;

import accounts.*;
import banking.Address;
import banking.Transaction;
import customer.Customer;
import employee.Employee;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Operations {

    public static FileHandling fileHandling = new FileHandling();


    public static void createNewCustomer() throws IOException {
        Scanner sc = new Scanner(System.in);
        Customer newCustomer = new Customer();
        while (true) {
            System.out.println("\n\nEnter the following details\nFirst Name : ");
            newCustomer.setFirstName(sc.nextLine());
            if (!checkName(newCustomer.getFirstName().toLowerCase())) {
                System.out.println("Enter only alphabets");
                continue;
            }
            System.out.println("Last Name : ");
            newCustomer.setLastName(sc.nextLine());
            System.out.println("Phone number : ");
            String num = sc.nextLine();
            if (checkPhoneNumber(num))
                newCustomer.setPhoneNumber(Long.parseLong(num));
            else {
                System.out.println("Enter 10 digit number");
                System.out.println("That starts with 7,8 or 9");
                continue;
            }
            System.out.println("Date of birth as dd/mm/yyyy");
            newCustomer.setDOB(sc.nextLine());
            if (!dobCheck(newCustomer.getDOB())) {
                System.out.println("Print Date in the correct format dd/mm/yyyy (eg: 31/03/1997)");
                continue;
            }
            System.out.println("Enter password");
            newCustomer.setPassword(sc.nextLine());
            if (newCustomer.getPassword().trim().equals("")) {
                System.out.println("password should not be empty");
                continue;
            }
            newCustomer.setCustomerID(fileHandling.getLastCustId());
            newCustomer.setAddressID(createNewAddress().getAddressID());
            System.out.println("Your CustomerId is : " + newCustomer.getCustomerID());
            fileHandling.addCustomertoFile(newCustomer);
            newCustomer = null;
            return;
        }
    }


    public static boolean checkPhoneNumber(String str) {


        Pattern ptrn = Pattern.compile("(0/91)?[7-9][0-9]{9}");

        Matcher match = ptrn.matcher(str);

        return (match.find() && match.group().equals(str));


    }

    public static boolean dobCheck(String dateStr) {
        DateFormat sdf = new SimpleDateFormat("dd/mm/yyyy");
        sdf.setLenient(false);
        try {
            sdf.parse(dateStr);
        } catch (ParseException e) {
            return false;
        }
        return true;
    }

    public static boolean checkName(String name) {

        if (name.trim().equals(""))
            return false;

        for (char ch : name.toCharArray()) {
            if (ch < 97 || ch > 122)
                return false;

        }

        return true;
    }

    public static boolean isNumber(String st) {

        for (char ch : st.toCharArray()) {

            if (ch < 48 || ch > 57)
                return false;

        }

        return true;

    }


    public static void isValidCustomer() throws IOException, ParseException {

        if (fileHandling.getCustomerList() != null) {

            Scanner sc = new Scanner(System.in);
            int id;
            while (true) {
                System.out.println("Enter customer id");
                String st = sc.nextLine();
                if (!isNumber(st.trim())) {
                    System.out.println("customer id contains only numbers");
                    continue;
                }
                id = Integer.parseInt(st.trim());
                break;
            }
            System.out.println("Enter customer password");
            String password = sc.nextLine();

            Customer cust = fileHandling.getCustomerfromFile(id);

            if (cust != null) {
                if (cust.getPassword().equals(password)) {
                    System.out.println("\n\n" + cust.getFirstName() + " " + cust.getLastName() + "'s account");
                    performOnlineTransaction(cust);
                    return;
                } else {
                    System.out.println("Wrong password");
                    return;
                }
            } else {
                System.out.println("Invalid credentials or Account not Found ");
                return;
            }
        } else {
            System.out.println("No Customer found 1st create one");
            createNewCustomer();
        }
    }

    public static void createNewEmployee() throws IOException {
        Scanner sc = new Scanner(System.in);
        String[] empTypes = new String[]{"Manager", "Accountant", "Cashier"};
        Employee ee = new Employee();
        while (true) {
            System.out.println("\n\nEnter the following details to create an employee");
            System.out.println("Enter the name");
            ee.setEmployeeName(sc.nextLine());
            if (!checkName(ee.getEmployeeName().toLowerCase())) {
                System.out.println("Enter only alphabets");
                continue;
            }

            System.out.println("Enter the employee type");

            int typeNum;
            for (typeNum = 0; typeNum < empTypes.length; ++typeNum) {
                System.out.println(typeNum + 1 + " for " + empTypes[typeNum]);
            }
            String st = sc.nextLine().trim();
            if (!isNumber(st)) {
                System.out.println("Enter numbers only");
                continue;
            } else {
                if (Integer.parseInt(st) < 0 || Integer.parseInt(st) > 3) {
                    System.out.println("Enter number between 1 - 3 only");
                    continue;
                }
            }
            typeNum = Integer.parseInt(st);
            ee.setEmployeeType(typeNum - 1);
            System.out.println("Enter Phone number");
            st = sc.nextLine();
            if (!checkPhoneNumber(st)) {
                System.out.println("Enter 10 digit number");
                System.out.println("That starts with 7,8 or 9");
                continue;
            }
            ee.setPhoneNum(Long.parseLong(st));
            System.out.println("Enter the date of birth as dd/mm/yyyy");
            ee.setDob(sc.nextLine());
            if (!dobCheck(ee.getDob())) {
                System.out.println("Print Date in the correct format dd/mm/yyyy (eg: 31/03/1997)");
                continue;
            }


            System.out.println("Enter the salary");
            st = sc.nextLine().trim();
            if (!isNumber(st)) {
                System.out.println("Enter only digits");
                continue;
            }
            ee.setSalary(Integer.parseInt(st));
            ee.setEmployeeAddressID(createNewAddress().getAddressID());
            ee.setEmployeeID(fileHandling.getLastEmpId());
            System.out.println("Your Employee ID : " + ee.getEmployeeID());
            fileHandling.addEmployeetoFile(ee);
            ee = null;
            return;
        }
    }

    public static boolean isValidEmployee() throws IOException, ParseException {

        if (fileHandling.getEmployeeList() != null) {

            Scanner sc = new Scanner(System.in);
            int empID;
            while (true) {
                System.out.println("Enter Employee ID");
                String st = sc.nextLine();
                if (!isNumber(st.trim())) {
                    System.out.println("Employee ID contains only numbers");
                    continue;
                }
                empID = Integer.parseInt(st.trim());
                break;
            }
            System.out.println("Enter Employee Name");
            String empName = sc.nextLine();

            Employee ee = fileHandling.getEmployeefromFile(empID);


            if (ee != null) {
                if (ee.getEmployeeID() == empID && ee.getEmployeeName().equals(empName)) {
                    System.out.println(ee.getEmployeeName() + "'s Employee account");
                    employeeFunctions(ee);
                    return true;
                } else {
                    System.out.println("Wrong user name");
                }
            } else {
                System.out.println("Invalid id");
            }
            return false;
        } else {
            System.out.println("No employee found 1st create one");
            createNewEmployee();
        }
        return false;
    }


    public static Address createNewAddress() throws IOException {
        Scanner sc = new Scanner(System.in);
        Address newAddress = new Address();
        String st;
        while (true) {
            System.out.println("\nEnter the following details. flat no :");
            st = sc.nextLine().trim();
            if (!isNumber(st)) {
                System.out.println("Enter only number");
                continue;
            }
            newAddress.setBuildingNo(Integer.parseInt(st));
            break;
        }
        System.out.println("Area or Street name");
        newAddress.setArea(sc.nextLine());
        System.out.println("City");
        newAddress.setCity(sc.nextLine());
        while (true) {
            System.out.println("State :");
            System.out.println("1 State_1\n2 State_2\n3 State_3");
            st = sc.nextLine().trim();
            if (!isNumber(st)) {
                System.out.println("Enter only number");
                continue;
            } else {
                if (Integer.parseInt(st) < 0 || Integer.parseInt(st) > 3) {
                    System.out.println("Enter number between 1 - 3 only");
                    continue;
                }
            }
            newAddress.setState(Integer.parseInt(st));
            break;
        }
        while (true) {
            System.out.println("Postal code");
            st = sc.nextLine();
            if (st.length() != 6 || !isNumber(st)) {
                System.out.println("Postal code should be of 6 digits only");
                continue;
            }
            newAddress.setPincode(Long.parseLong(st));
            break;
        }

        newAddress.setAddressID(fileHandling.getLastAddId());
        System.out.println("Your AddressId : " + newAddress.getAddressID());
        fileHandling.addAddresstoFile(newAddress);
        return newAddress;
    }


    public static void employeeFunctions(Employee ee) throws IOException, ParseException {


        Employee.EmployeeType st = ee.getEmployeeType();

        switch (st) {
            case Manager:
                managerFunctions();
                break;
            case Accountant:
                accountantFunctions();
                break;
            case Cashier:
                System.out.println("No Function for the cashier");
                break;

        }


    }


    public static void managerFunctions() throws IOException {
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("1 :create a new Customer");
            System.out.println("2 :create a new Account\n3 : Exit");

            String st = sc.nextLine();
            int num;
            if (!isNumber(st.trim())) {
                System.out.println("Enter numbers between 1 - 3 only");
                continue;
            } else {
                num = Integer.parseInt(st);
                if (num < 0 || num > 3) {
                    System.out.println("Enter number between 1 - 3 only");
                    continue;
                }
                if (num == 1) {
                    createNewCustomer();
                } else if (num == 2) {

                    System.out.println("Enter your Customer Id");
                    st = sc.nextLine();
                    if (!isNumber(st.trim())) {
                        System.out.println("Please enter numbers only");
                    } else {
                        num = Integer.parseInt(st);

                        Customer cust = fileHandling.getCustomerfromFile(num);


                        if (cust != null) {

                            createNewAccount(cust);
                            break;
                        } else {
                            System.out.println("Sorry No Customer Found of that Id");
                            continue;
                        }

                    }

                } else {
                    return;
                }
            }


        }

    }


    public static void accountantFunctions() throws IOException, ParseException {

        while (true) {

            Scanner sc = new Scanner(System.in);

            System.out.println("\n\nEnter\n1 : to view all accounts");
            System.out.println("2 : to view all savings account");
            System.out.println("3 : to view all current account");
            System.out.println("4 : to create an employee");
            System.out.println("5 : to view all Transactions");
            System.out.println("6 : exit");
            String accType = "";
            int opt = 0;
            String st = sc.nextLine();
            try {
                opt = Integer.parseInt(st);
            } catch (Exception e) {
                System.out.println("Enter numbers between 1 - 6 only");
                continue;
            }
            switch (opt) {
                case 1:
                    accType = "all";
                    break;
                case 2:
                    accType = "Savings";
                    break;
                case 3:
                    accType = "Current";
                    break;
                case 4:
                    createNewEmployee();
                    break;
                case 5: {
                    if (fileHandling.getTransactionList() == null) {
                        System.out.println("No Transactions made yet");
                    } else {
                        Iterator var5 = fileHandling.getTransactionList().iterator();


                        while (var5.hasNext()) {
                            Transaction tr = (Transaction) var5.next();
                            printTransaction(tr);
                        }
                    }
                }
                case 6:
                    return;
                default:
                    System.out.println("Enter number between 1 - 6 only");
                    continue;
            }


            if (accType != "") {
                List<Account> allAccs = getAllAccounts(accType);
                Iterator var5 = allAccs.iterator();

                while (var5.hasNext()) {
                    Account i = (Account) var5.next();
                    System.out.println(i.getCustomerID() + " " + i.getAccountNo() + " " + i.getAccType());
                }
            }
        }
    }

    private static List<Account> getAllAccounts(String accType) throws IOException {


        List<Account> acc = new ArrayList<>();
        boolean b = false;

        if (fileHandling.getAccountList() != null) {
            Iterator var5 = fileHandling.getAccountList().iterator();

            while (var5.hasNext()) {
                Account i = (Account) var5.next();
                if (accType != "all") {
                    if (i.getAccType().toString().equals(accType)) {
                        b = true;
                        acc.add(i);
                    }
                } else {
                    b = true;
                    acc.add(i);
                }

            }
            if (b == false) {
                System.out.println("No Accounts found of Type " + accType);
            }
            return acc;
        } else {
            System.out.println("No accounts found");
        }
        return null;

    }

    public static Account createNewAccount(Customer cust) throws IOException {
        Scanner sc = new Scanner(System.in);
        Account ac;

        String[] accTypes = new String[]{"Savings", "Current", "Deposit"};
        System.out.println("Account type");

        int temp;
        for (temp = 0; temp < accTypes.length; ++temp) {
            System.out.println(temp + 1 + " " + accTypes[temp]);
        }

        temp = Integer.parseInt(sc.nextLine());

        switch (temp) {
            case 1:
                ac = new SavingsAccount();
                break;
            case 2:
                ac = new CurrentAccount();
                break;
            case 3:
                ac = new DepositAccount();
                break;
            default:
                return null;
        }

        ac.setAccType(temp - 1);
        if (ac instanceof DepositAccount) {
            System.out.println("Enter the deposit period in months");
            ((DepositAccount) ac).setTermsInMonth(Integer.parseInt(sc.nextLine()));
        }
        ac.setCustomerID(cust.getCustomerID());
        System.out.println("PIN <= 9999");

        while (true) {
            ac.setPin(Integer.parseInt(sc.nextLine()));
            if (ac.getPin() <= 9999)
                break;
        }
        while (true) {
            String st;
            System.out.println("\nBranch :");
            System.out.println("1 Madurai\n2 Chennai\n3 Coimbatore");
            st = sc.nextLine().trim();
            if (!isNumber(st)) {
                System.out.println("Enter only number");
                continue;
            } else {
                if (Integer.parseInt(st) < 0 || Integer.parseInt(st) > 3) {
                    System.out.println("Enter number between 1 - 3 only");
                    continue;
                } else {

                    ac.setBrachName(Integer.parseInt(st) - 1);
                    break;
                }
            }
        }
        ac.setAccountNo(fileHandling.getLastAccNo());
        fileHandling.addAccounttoFile(ac);
        System.out.println("Your Account Number :" + ac.getAccountNo()+"\nIFSC_CODE: "+ac.getIfscCode());
        return ac;
    }


    public static void performOnlineTransaction(Customer cust) throws IOException, ParseException {
        Scanner sc = new Scanner(System.in);
        while (true) {

            Transaction tr = new Transaction();
            String[] types = new String[]{"Transfer", "Balance enquiry"};
            System.out.println("\n\nEnter transaction type");

            int ch;
            String st;
            for (ch = 0; ch < types.length; ++ch) {
                System.out.println(ch + 1 + " " + types[ch]);
            }
            System.out.println("3 Check Transactions\n4 Exit");
            st = sc.nextLine();
            try {
                ch = Integer.parseInt(st);
            } catch (Exception e) {
                System.out.println("Enter numbers between 1 - 4 only");
                continue;
            }

            TransactionFunctions transaction = new TransactionFunctions();

            switch (ch) {
                case 1:
                    transaction.transfer(cust, tr);
                    break;
                case 2:
                    transaction.checkBalance(cust);
                    break;
                case 3:
                    viewTransactions(cust);
                    break;
                case 4:
                    return;
                default:
                    System.out.println("Enter number between 1- 4 only");
                    continue;


            }
        }
    }


    public static void viewTransactions(Customer cust) throws IOException, ParseException {


        Transaction tr = fileHandling.getTransactionfromFile(cust.getCustomerID());


        if (tr != null) {
            printTransaction(tr);
        } else {
            System.out.println("No transactions made yet");
            return;
        }
    }

    public static void printTransaction(Transaction tr) {


        System.out.println("Transaction Id : " + tr.getTransactionID());
        System.out.println("Sender Account No : " + tr.getSenderAccNo());
        System.out.println("Transaction Amount :" + tr.getTransactionAmt());
        System.out.println("Receiver Account No : " + tr.getReceiverAccNo());
        System.out.println("Current Balance :" + tr.getCurrentBalance());
        System.out.println("Transaction Time :" + tr.getTranactionTime() + "\n\n");

    }
}


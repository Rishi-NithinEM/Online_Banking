package main;

import accounts.Account;
import banking.Address;
import banking.Transaction;
import customer.Customer;
import employee.Employee;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class BankingMain {


    public static void main(String[] arg) throws IOException, ParseException {


        String functions = "Enter\n1 : login as Employee\n";
        functions = functions + "2 : login as customer\n";
//        functions = functions + "3 : create new Employee\n";
//        functions = functions + "4 : create a new customer\n";
        functions = functions + "3 : Exit";
        Scanner sc = new Scanner(System.in);
        int opt = 0;
        String st;

        while (true) {
            System.out.println(functions);
            st = sc.nextLine();
            try {
                opt = Integer.parseInt(st);
            } catch (Exception e) {
                System.out.println("Enter numbers between 1 - 3 only");
                opt = 0;
                continue;
            }

            switch (opt) {
                case 1:
                    Operations.isValidEmployee();
                    break;
                case 2:
                    Operations.isValidCustomer();
                    break;
//                case 3:
//                    Operations.createNewEmployee();
//                    break;
//                case 4:
//                    Operations.createNewCustomer();
//                    break;
                case 3:
                    return;
                default:
                    System.out.println("Enter number between 1 to 3");
            }
        }
    }
}


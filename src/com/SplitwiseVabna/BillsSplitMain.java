package com.SplitwiseVabna;

import java.io.*;
import java.util.*;

public class BillsSplitMain {

    public static void main(String[] args) throws IOException {
	// write your code here
        HashSet<String> members = new HashSet<>();/*HashSet ensures that all elements are unique, and it does not allow duplicates.
        HashSet does not maintain the order of elements. It provides constant-time (O(1)) performance for adding, removing, and checking the existence of elements.*/
        ArrayList expenses = new ArrayList<>();/*ArrayList maintains the insertion order of elements, allowing access by index.
        ArrayList permits duplicate elements.It provides fast (O(1)) access to elements using their index.*/
        List<Transaction> transactions = new ArrayList<>();

        InputStreamReader read = new InputStreamReader(System.in);
        BufferedReader in = new BufferedReader(read);
        BillsSplitMain ob = new BillsSplitMain();
        System.out.println("Welcome to BILLS SPLIT");
        BillsSplit billsSplit = new BillsSplit(members, expenses);

        System.out.println("Enter details of expense:");

        ob.addExpense(expenses, members, in, billsSplit);
        System.out.println("-----------------------------------------------------------------------------------------------------------------------");

        System.out.println("Do you want to add more members (Y / y):");
        char c = Character.toLowerCase(in.readLine().charAt(0));
        if(c == 'y')
            ob.addMembers(members, in);
        System.out.println("-----------------------------------------------------------------------------------------------------------------------");

        ob.viewExpenses(expenses);
        System.out.println("-----------------------------------------------------------------------------------------------------------------------");

        billsSplit.calculateBalancesOfEachMember();
        ob.viewBalancesOfEachMember(billsSplit.getBal());
        System.out.println("-----------------------------------------------------------------------------------------------------------------------");


        int choice = 0;


        while (true) {
            System.out.println("-----------------------------------------------------------------------------------------------------------------------");
            System.out.println("What can I do:");

            displayOptions();
            System.out.println("-----------------------------------------------------------------------------------------------------------------------");
            choice = Integer.parseInt(in.readLine());
            switch (choice) {
                case 1:
                    ob.viewExpenses(expenses);
                    break;
                case 2:
                    ob.viewBalancesOfEachMember(billsSplit.getBal());
                    break;
                case 3:
                    billsSplit.debtSimplify();
                    break;
                case 4:
                    Transaction t = billsSplit.splitExpense(in);
                    if (t == null) System.out.println("Transaction Unsuccessful");
                    else {
                        System.out.println("Transaction Successful");
                        transactions.add(t);
                    }
                    break;
                case 5:
                    ob.viewTransaction(transactions);
                    break;
                case 6:
                    ob.viewLendersBorrowers(billsSplit.simplifyBorrowerLender());
                    break;
                case 7:
                    return;
                default:
                    System.out.println("Invalid Choice!!!!! \nPlease enter a valid choice:");
            }
        }
    }

    public static void displayOptions(){
        System.out.println("1. Expenses");
        System.out.println("2. Balance of Each Member");
        System.out.println("3. Debt Simplifier");
        System.out.println("4. Return Money Transaction");
        System.out.println("5. View All Return Transactions");
        System.out.println("6. View Lenders and Borrowers");
        System.out.println("7. Exit");
    }

    private void viewLendersBorrowers(Map<String, Map<String, Double>> simplifyBorrowerLender) {
        if (simplifyBorrowerLender.isEmpty()) {
            System.out.println("No Records!!!!!!!!!!!!!!!!");
            return;
        }
        Map<String, Double> lender = simplifyBorrowerLender.get("Lender");
        Map<String, Double> borrower = simplifyBorrowerLender.get("Borrower");
        boolean f1 = false, f2 = false;

        if (!lender.isEmpty()) {
            System.out.println("Lenders:");
            for (Map.Entry<String, Double> entry :
                    lender.entrySet()) {
                System.out.println(entry.getKey());
                f1 = true;
            }

        }

        if (!borrower.isEmpty()) {
            System.out.println("Borrower:");
            for (Map.Entry<String, Double> entry :
                    borrower.entrySet()) {
                System.out.println(entry.getKey());
                f2 = true;
            }
        }
        if (f1 && f2)
            return;
        System.out.println("There are no lenders and borrowers");
    }

    private void viewTransaction(List<Transaction> transactions) {
        if (transactions.isEmpty()) {
            System.out.println("Nobody has returned any money yet.");
        }
        for (Transaction t :
                transactions) {
            System.out.println(t.getFrom() + " paid back Rs." + t.getAmount() + " to " + t.getTo());
        }
    }

    public void viewBalancesOfEachMember(Map<String, Double> bal){
        if (bal.isEmpty()){
            System.out.println("Data has not been added yet");
            return;
        }
        System.out.println("Balance of each member");
        for (Map.Entry<String, Double> entry :
                bal.entrySet()) {
            System.out.print(entry.getKey());
            if (entry.getValue() >= 0)
                System.out.println(" will get Rs." + entry.getValue());
            else System.out.println(" will pay Rs." + Math.abs(entry.getValue()));
        }
    }

    public void addExpense(List<Expense> expenses, HashSet<String> members, BufferedReader in, BillsSplit billsSplit) throws IOException {
        String by;
        double amt;
        String act;
        char ch = 'y';
        while (Character.toLowerCase(ch) == 'y'){
            System.out.println("Enter the payer's name:");
            by = in.readLine();
            boolean addOrNot = members.add(by);
            if(addOrNot)
                System.out.println("New member added");
            else System.out.println("Member already exists");
            System.out.println("Enter the amount paid by " + by + ":");
            amt = Double.parseDouble(in.readLine());
            System.out.println("Enter the type of expense for which the amount is paid:");
            act = in.readLine();
            expenses.add(new Expense(by, amt, act));
            System.out.println("Expense added successfully");
            System.out.println("Press 'Y' to continue adding more expenses or press any character to discontinue:");
            ch = in.readLine().charAt(0);
        }
    }

    private void addMembers(HashSet<String> members, BufferedReader in) throws IOException {
        while (true){
            if (!members.isEmpty()) {
                System.out.println("Members Present");
                for (String s :
                        members) {
                    System.out.println(s);
                }
            }
            System.out.println("Enter the name of member to be added:");
            String name = in.readLine();
            members.add(name);
            System.out.println("Enter Y/y if you want to continue adding members else press any character");
            char ch = in.readLine().charAt(0);
            if(ch != 'y' && ch != 'Y')
                return;
        }
    }

    private void viewExpenses(List<Expense> expenses){
        if(expenses.isEmpty()){
            System.out.println("No expenses added");
            return;
        }
        System.out.println("Expenses:");
        for (Expense e :
                expenses) {
            System.out.println(e.getPayedBy() + " paid Rs." + e.getAmount() + " for " + e.getActivity());
        }
    }
}



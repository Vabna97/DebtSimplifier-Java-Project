package com.SplitwiseVabna;

import java.io.*;
import java.util.*;

public class SplitwiseMain {

    public static void main(String[] args) throws IOException {
	// write your code here
        HashSet<String> members = new HashSet<>();
        ArrayList expenses = new ArrayList();
        List<Transaction> transactions = new ArrayList<>();

        InputStreamReader read = new InputStreamReader(System.in);
        BufferedReader in = new BufferedReader(read);
        SplitwiseMain ob = new SplitwiseMain();
        System.out.print("Welcome to BILLS SPLIT");
        Splitwise splitwise = new Splitwise(members, expenses);
        int choice = 0;
        System.out.println("What can I do:");
        while (true) {
            displayOptions();
            choice = Integer.parseInt(in.readLine());
            switch (choice) {
                case 1:
                    ob.addExpense(expenses, members, in, splitwise);
                    break;
                case 2:
                    ob.addMembers(members, in);
                    break;
                case 3:
                    ob.viewExpenses(expenses);
                    break;
                case 4:
                    splitwise.calculateBalancesOfEachMember();
                    ob.viewBalancesOfEachMember(splitwise.getBal());
                    break;
                case 5:
                    splitwise.debtSimplify();
                    break;
                case 6:
                    Transaction t = splitwise.splitExpense(in);
                    if (t == null) System.out.println("Transaction Unsuccessful");
                    else {
                        System.out.println("Transaction Successful");
                        transactions.add(t);
                    }
                    break;
                case 7:
                    ob.viewTransaction(transactions);
                    break;
                case 8:
                    ob.viewLendersBorrowers(splitwise.simplifyBorrowerLender());
                    break;
                case 9:
                    return;
                default:
                    System.out.println("Invalid Choice!!!!! \nPlease enter a valid choice:");
            }
        }
    }

    public static void displayOptions(){
        System.out.println("1. Add expense");
        System.out.println("2. Add members");
        System.out.println("3. Expenses");
        System.out.println("4. Balance of Each Member");
        System.out.println("5. Debt Simplifier");
        System.out.println("6. Return Money Transaction");
        System.out.println("7. View All Return Transactions");
        System.out.println("8. View Lenders and Borrowers");
        System.out.println("9. Exit");
    }

    private void viewLendersBorrowers(Map<String, Map<String, Double>> simplifyBorrowerLender) {
        if (simplifyBorrowerLender.isEmpty()) {
            System.out.println("No Records!!!!!!!!!!!!!!!!");
            return;
        }
        Map<String, Double> lender = simplifyBorrowerLender.get("Lender");
        Map<String, Double> borrower = simplifyBorrowerLender.get("Borrower");
        if (!lender.isEmpty()) {
            System.out.println("Lenders:");
            for (Map.Entry<String, Double> entry :
                    lender.entrySet()) {
                System.out.println(entry.getKey());
            }

        }
        if (!borrower.isEmpty()) {
            System.out.println("Borrower:");
            for (Map.Entry<String, Double> entry :
                    borrower.entrySet()) {
                System.out.println(entry.getKey());
            }

        }
    }

    private void viewTransaction(List<Transaction> transactions) {
        if (transactions == null) {
            System.out.println("Nobody returned money yet");
        }
        for (Transaction t :
                transactions) {
            System.out.println(t.getFrom() + "paid back Rs." + t.getAmount() + " to " + t.getTo());
        }
    }

    public void viewBalancesOfEachMember(Map<String, Double> bal){
        if (bal.isEmpty()){
            System.out.println("Data has not been added yet");
            return;
        }

        for (Map.Entry<String, Double> entry :
                bal.entrySet()) {
            System.out.print(entry.getKey());
            if (entry.getValue() >= 0)
                System.out.println(" will get Rs." + entry.getValue());
            else System.out.println(" will pay Rs." + Math.abs(entry.getValue()));
        }
    }

    private void addExpense(List<Expense> expenses, HashSet<String> members, BufferedReader in, Splitwise splitwise) throws IOException {
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
            else System.out.println("New member not added");
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



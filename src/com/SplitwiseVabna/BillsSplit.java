package com.SplitwiseVabna;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.*;

public class BillsSplit {
    private HashSet<String> members;
    private List<Expense> expenses;
    private HashMap<String, Double> bal;

    public BillsSplit(HashSet<String> members, List<Expense> expenses) {
        this.members = members;
        this.expenses = expenses;
        bal = new HashMap<>();
    }

    private double calculateEachMemberShare(){
        double totalExpense = expenses.stream().mapToDouble(Expense::getAmount).sum();
        return totalExpense / members.size();
    }

    public void calculateBalancesOfEachMember(){
        double eachShare = calculateEachMemberShare();

        for (String m :
                members) {
            double expOfMember = expenses.stream().filter(exp -> exp.getPayedBy().equalsIgnoreCase(m))
                    .mapToDouble(Expense :: getAmount).sum();
            bal.put(m, Math.round(expOfMember - eachShare) * 1.0);
        }
    }

    // Separate members into lenders (who paid extra) and borrowers (who owe money)
    public Map<String, Map<String, Double>> simplifyBorrowerLender(){
        Map<String, Double> lender = new HashMap<>();
        Map<String, Double> borrower = new HashMap<>();
        //calculateBalancesOfEachMember();
        for (Map.Entry<String, Double> entry :
                bal.entrySet()) {
            if (entry.getValue() > 0) {
                lender.put(entry.getKey(), entry.getValue());
            }
            else if (entry.getValue() < 0) {
                borrower.put(entry.getKey(), entry.getValue());
            }
        }
        Map<String, Map<String, Double>> classifiedBorrowerLender = new HashMap<>();
        classifiedBorrowerLender.put("Borrower", borrower);
        classifiedBorrowerLender.put("Lender", lender);
        return classifiedBorrowerLender;
    }

    //function to simplify the intertwined debts and suggest transactions to  minimize their number
    public void debtSimplify(){
        Map<String, Map<String, Double>> lenderBorrowerSimplified = simplifyBorrowerLender();
        PriorityQueue<Map.Entry<String, Double>> borrower = new PriorityQueue<>(Comparator.comparingDouble(Map.Entry::getValue));   //Method Reference
        PriorityQueue<Map.Entry<String, Double>> lender = new PriorityQueue<>((a,b) -> Double.compare(b.getValue(),a.getValue()));//max heap PriorityQueue<Map.Entry<String, Double>> borrower = new PriorityQueue<>(Comparator.comparingDouble(Map.Entry::getValue)).reverse();

        borrower.addAll(lenderBorrowerSimplified.get("Borrower").entrySet());
        lender.addAll(lenderBorrowerSimplified.get("Lender").entrySet());
        if(lenderBorrowerSimplified.get("Borrower").isEmpty()|| lenderBorrowerSimplified.get("Lender").isEmpty()){
            System.out.println("All dues are settled");
        }
        while (!lender.isEmpty() && !borrower.isEmpty()){
            Map.Entry<String, Double> debtor = borrower.poll();
            Map.Entry<String, Double> creditor = lender.poll();
//            borrower.remove();
//            lender.remove();

            double amtToBePayed = Math.round(Math.min(Math.abs(debtor.getValue()), creditor.getValue())) * 1.0;
            System.out.println(debtor.getKey() + " needs to pay " + creditor.getKey() + ": Rs." + amtToBePayed);

            double newBalanceDebtor = Math.round(amtToBePayed + debtor.getValue());
            double newBalanceCreditor = Math.round(creditor.getValue() - amtToBePayed);

            if (newBalanceDebtor < 0){
                borrower.offer(new AbstractMap.SimpleImmutableEntry<>(debtor.getKey(), newBalanceDebtor)); // Entry is an interface
            }
            if (newBalanceCreditor > 0){
                lender.offer(new AbstractMap.SimpleImmutableEntry<>(creditor.getKey(), newBalanceCreditor)); // Entry is an interface
            }
        }
    }



    public Transaction splitExpense(BufferedReader in) throws IOException {
        Map<String, Map<String, Double>> lenderBorrowerSimplified = simplifyBorrowerLender();
        Map<String, Double> borrowers = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
        borrowers.putAll(lenderBorrowerSimplified.get("Borrower"));
        Map<String, Double> lenders = new TreeMap<>(String.CASE_INSENSITIVE_ORDER); //sorted map based on a Red-Black Tree. It stores key-value pairs in ascending order of keys
        lenders.putAll(lenderBorrowerSimplified.get("Lender"));

        if (lenders.isEmpty()){
            System.out.println("There are no lenders!!!!!!!!!!!!!!!!!!!!!!");
            return null;
        }

        String lenderName;
        String borrowerName;

        if(borrowers.size() == 1)
            borrowerName = borrowers.keySet().iterator().next();
        else {
            System.out.println("Enter the name of borrower who is willing to return money: ");
            borrowerName = in.readLine();
            if (!borrowers.containsKey(borrowerName)) {
                System.out.println("there is no borrower named " + borrowerName);
                return null;
            }
        }
        System.out.println(borrowerName + " owes Rs."  + Math.abs(borrowers.get(borrowerName)));
        System.out.println("List of  lenders and the amount they lent:");
        printDetails(lenders);

        if(lenders.size() == 1)
            lenderName = lenders.keySet().iterator().next();
        else {
            System.out.println("Enter the name of lender " + borrowerName + " wants to pay to: ");
            lenderName = in.readLine();

            if (!lenders.containsKey(lenderName)) {
                System.out.println("there is no lender named " + lenderName);
                return null;
            }
        }

        System.out.println("Enter the amount you want to return:");
        double amt = Double.parseDouble(in.readLine());
        if (amt > lenders.get(lenderName)){
            System.out.println("Amount entered is larger than lent by " + lenderName);
            return null;
        }
        else if (amt > Math.abs(borrowers.get(borrowerName))){
            System.out.println("Amount entered is larger than owed by " + borrowerName);
            return null;
        }
        else
            return transactionOfBorrowerPayingLender(borrowerName, amt, lenderName);

    }
    private Transaction transactionOfBorrowerPayingLender(String borrower,
                                                          double borrowedAmt, /*amount borrower is able to repay at the moment*/
//                                                            ,Map<String, Double> borrowerData,
                                                          String lender//, Map<String, Double> lenderData
    ){

        System.out.println("Updated amount owed by the borrower " + borrower +  " : " + Math.abs(bal.get(borrower) + borrowedAmt));
        System.out.println("Updated amount lent by the lender " + lender + " : " + (bal.get(lender) - borrowedAmt));
        bal.put(borrower, bal.get(borrower) + borrowedAmt);
        bal.put(lender, bal.get(lender) - borrowedAmt);
        return new Transaction(borrower, lender, borrowedAmt);
    }

    public void printDetails(Map<String, Double> details){
        for (Map.Entry<String, Double> entry :
                details.entrySet()) {
            System.out.println(entry.getKey() + " lent Rs." + entry.getValue());
        }
    }

    public HashMap<String, Double> getBal() {
        return bal;
    }
}

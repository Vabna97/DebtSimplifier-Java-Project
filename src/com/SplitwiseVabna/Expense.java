package com.SplitwiseVabna;

public class Expense {
    private String payedBy;
    private double amount;
    private String activity;

    public Expense(String payedBy, double amount, String activity) {
        this.payedBy = payedBy;
        this.amount = amount;
        this.activity = activity;
    }

    public String getPayedBy() {
        return payedBy;
    }

    public double getAmount() {
        return amount;
    }

    public String getActivity() {
        return activity;
    }
}

package com.BillsSplitMain.model;

public class Expense {
    private Member payedBy;
    private double amount;
    private String activity;

    public Expense(Member payedBy, double amount, String activity) {
        this.payedBy = payedBy;
        this.amount = amount;
        this.activity = activity;
    }

    public Member getPayedBy() {
        return payedBy;
    }

    public void setPayedBy(Member payedBy) {
        this.payedBy = payedBy;
    }

    public double getAmount() {
        return amount;
    }

    public String getActivity() {
        return activity;
    }
}

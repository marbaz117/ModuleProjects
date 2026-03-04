package org.example.model;

public class Expense{
    private int customerId;
    private String category;
    private double amount;

    public Expense(int customerId,String category,double amount) {
        this.customerId=customerId;
        this.category=category;
        this.amount=amount;
    }

    public int getCustomerId(){
        return customerId;
    }

    public String getCategory() {
        return category;
    }

    public double getAmount() {
        return amount;
    }
}
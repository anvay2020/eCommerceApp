package com.anvay.pawan.wholeseller.models;

import com.anvay.pawan.wholeseller.utils.Constants;

public class SellerDetails {
    private String sellerId;
    private double totalSales, walletBalance;
    private int pendingOrders, completedOrders, shippedOrders, returnedOrders;
    private int complaintType1;
    private int accountHealth;

    public SellerDetails() {
    }

    public SellerDetails(String sellerId) {
        this.sellerId = sellerId;
        totalSales = 0.00;
        walletBalance = 0.00;
        pendingOrders = 0;
        completedOrders = 0;
        shippedOrders = 0;
        returnedOrders = 0;
        complaintType1 = 0;
        accountHealth = Constants.ACCOUNT_ACTIVE;
    }

    public String getSellerId() {
        return sellerId;
    }

    public void setSellerId(String sellerId) {
        this.sellerId = sellerId;
    }

    public double getTotalSales() {
        return totalSales;
    }

    public void setTotalSales(double totalSales) {
        this.totalSales = totalSales;
    }

    public double getWalletBalance() {
        return walletBalance;
    }

    public void setWalletBalance(double walletBalance) {
        this.walletBalance = walletBalance;
    }

    public int getPendingOrders() {
        return pendingOrders;
    }

    public void setPendingOrders(int pendingOrders) {
        this.pendingOrders = pendingOrders;
    }

    public int getCompletedOrders() {
        return completedOrders;
    }

    public void setCompletedOrders(int completedOrders) {
        this.completedOrders = completedOrders;
    }

    public int getShippedOrders() {
        return shippedOrders;
    }

    public void setShippedOrders(int shippedOrders) {
        this.shippedOrders = shippedOrders;
    }

    public int getReturnedOrders() {
        return returnedOrders;
    }

    public void setReturnedOrders(int returnedOrders) {
        this.returnedOrders = returnedOrders;
    }

    public int getComplaintType1() {
        return complaintType1;
    }

    public void setComplaintType1(int complaintType1) {
        this.complaintType1 = complaintType1;
    }

    public int getAccountHealth() {
        return accountHealth;
    }

    public void setAccountHealth(int accountHealth) {
        this.accountHealth = accountHealth;
    }
}

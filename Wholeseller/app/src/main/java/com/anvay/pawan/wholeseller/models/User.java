/**
 * coded by Pawan Singh Harariya
 */
package com.anvay.pawan.wholeseller.models;

import com.anvay.pawan.wholeseller.utils.Constants;

public class User {
    String userId, name, pincode, address, email, mobile, landmark, gstNumber, panNumber, pickupAddress, returnAddress;
    double walletBalance;
    int accountHealth;

    public User() {
    }

    public User(String userId, String name, String email, String mobile, String address, String landmark, String pincode, String gstNumber, String panNumber,
                String pickupAddress, String returnAddress) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.mobile = mobile;
        this.landmark = landmark;
        this.address = address;
        this.pincode = pincode;
        this.gstNumber = gstNumber;
        this.panNumber = panNumber;
        this.pickupAddress = pickupAddress;
        this.returnAddress = returnAddress;
        this.walletBalance = 0.00;
        this.accountHealth = Constants.ACCOUNT_ACTIVE;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLandmark() {
        return landmark;
    }

    public void setLandmark(String landmark) {
        this.landmark = landmark;
    }

    public String getGstNumber() {
        return gstNumber;
    }

    public void setGstNumber(String gstNumber) {
        this.gstNumber = gstNumber;
    }

    public String getPanNumber() {
        return panNumber;
    }

    public void setPanNumber(String panNumber) {
        this.panNumber = panNumber;
    }

    public String getPickupAddress() {
        return pickupAddress;
    }

    public void setPickupAddress(String pickupAddress) {
        this.pickupAddress = pickupAddress;
    }

    public String getReturnAddress() {
        return returnAddress;
    }

    public void setReturnAddress(String returnAddress) {
        this.returnAddress = returnAddress;
    }

    public double getWalletBalance() {
        return walletBalance;
    }

    public void setWalletBalance(double walletBalance) {
        this.walletBalance = walletBalance;
    }

    public int getAccountHealth() {
        return accountHealth;
    }

    public void setAccountHealth(int accountHealth) {
        this.accountHealth = accountHealth;
    }
}

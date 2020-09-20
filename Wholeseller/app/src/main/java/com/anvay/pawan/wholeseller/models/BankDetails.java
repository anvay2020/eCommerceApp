/**
 * coded by Pawan Singh Harariya
 */
package com.anvay.pawan.wholeseller.models;

public class BankDetails {
    String holderName, accountNo, ifscCode, branchName;

    public BankDetails() {
    }

    public BankDetails(String holderName, String accountNo, String ifscCode, String branchName) {
        this.holderName = holderName;
        this.accountNo = accountNo;
        this.ifscCode = ifscCode;
        this.branchName = branchName;
    }

    public String getHolderName() {
        return holderName;
    }

    public void setHolderName(String holderName) {
        this.holderName = holderName;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public String getIfscCode() {
        return ifscCode;
    }

    public void setIfscCode(String ifscCode) {
        this.ifscCode = ifscCode;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }
}

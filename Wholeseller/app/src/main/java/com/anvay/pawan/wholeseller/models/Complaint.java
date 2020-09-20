package com.anvay.pawan.wholeseller.models;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.ServerTimestamp;

public class Complaint {
    private String userName, description, sellerId;
    private int complaintType;
    @ServerTimestamp
    private Timestamp time;

    public Complaint() {
    }

    public Complaint(String userName, String sellerId, String description, int complaintType) {
        this.userName = userName;
        this.description = description;
        this.complaintType = complaintType;
        this.sellerId = sellerId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSellerId() {
        return sellerId;
    }

    public void setSellerId(String sellerId) {
        this.sellerId = sellerId;
    }

    public int getComplaintType() {
        return complaintType;
    }

    public void setComplaintType(int complaintType) {
        this.complaintType = complaintType;
    }

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }
}

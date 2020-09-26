/**
 * coded by Pawan Singh Harariya
 */
package com.anvay.pawan.wholeseller.models;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.ServerTimestamp;

import java.util.ArrayList;

public class Complaint {
    private String sellerName, title, sellerId, complaintId, retailerId;
    private int complaintType;
    private ArrayList<Message> messages;
    @ServerTimestamp
    private Timestamp time;

    public Complaint() {
    }

    public Complaint(String sellerName, String sellerId, String title, int complaintType, ArrayList<Message> messages, String retailerId) {
        this.sellerName = sellerName;
        this.title = title;
        this.complaintType = complaintType;
        this.sellerId = sellerId;
        this.retailerId = retailerId;
        this.messages = messages;
    }

    public String getSellerName() {
        return sellerName;
    }

    public void setSellerName(String sellerName) {
        this.sellerName = sellerName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public ArrayList<Message> getMessages() {
        return messages;
    }

    public void setMessages(ArrayList<Message> messages) {
        this.messages = messages;
    }

    public String getComplaintId() {
        return complaintId;
    }

    public void setComplaintId(String complaintId) {
        this.complaintId = complaintId;
    }

    public String getRetailerId() {
        return retailerId;
    }

    public void setRetailerId(String retailerId) {
        this.retailerId = retailerId;
    }
}

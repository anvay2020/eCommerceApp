/**
 * coded by Pawan Singh Harariya
 */
package com.anvay.pawan.wholeseller.models;

import com.anvay.pawan.wholeseller.utils.Constants;

public class Message {
    private String text;
    private boolean sender;// 0 for admin, 1 for seller

    public Message() {
    }

    public Message(String text) {
        this.sender = Constants.SENDER_SELLER;
        this.text = text;
    }

    public boolean getSender() {
        return sender;
    }

    public void setSender(boolean sender) {
        this.sender = sender;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}

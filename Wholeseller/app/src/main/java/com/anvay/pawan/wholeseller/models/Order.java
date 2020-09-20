/**
 * coded by Pawan Singh Harariya
 */
package com.anvay.pawan.wholeseller.models;

import com.anvay.pawan.wholeseller.utils.Constants;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.ServerTimestamp;

public class Order {

    private String orderId, sellerId, customerId, transactionId, trackingId, productId, productName, sku, customerName, customerAddress,
            customerContact, orderValue, orderQuantity, shippingTime, imageUrl;
    private int status;
    @ServerTimestamp
    private Timestamp orderTime;

    public Order() {
    }

    public Order(String sellerId, String customerId, String transactionId, String productId, String productName, String sku, String customerName,
                 String customerAddress, String customerContact, String orderValue, String orderQuantity, String imageUrl) {
        this.sellerId = sellerId;
        this.customerId = customerId;
        this.transactionId = transactionId;
        this.productId = productId;
        this.sku = sku;
        this.productName = productName;
        this.customerName = customerName;
        this.customerAddress = customerAddress;
        this.customerContact = customerContact;
        this.orderValue = orderValue;
        this.orderQuantity = orderQuantity;
        this.imageUrl = imageUrl;
        this.status = Constants.STATUS_PENDING;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getSellerId() {
        return sellerId;
    }

    public void setSellerId(String sellerId) {
        this.sellerId = sellerId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getTrackingId() {
        return trackingId;
    }

    public void setTrackingId(String trackingId) {
        this.trackingId = trackingId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerAddress() {
        return customerAddress;
    }

    public void setCustomerAddress(String customerAddress) {
        this.customerAddress = customerAddress;
    }

    public String getCustomerContact() {
        return customerContact;
    }

    public void setCustomerContact(String customerContact) {
        this.customerContact = customerContact;
    }

    public String getOrderValue() {
        return orderValue;
    }

    public void setOrderValue(String orderValue) {
        this.orderValue = orderValue;
    }

    public String getOrderQuantity() {
        return orderQuantity;
    }

    public void setOrderQuantity(String orderQuantity) {
        this.orderQuantity = orderQuantity;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getShippingTime() {
        return shippingTime;
    }

    public void setShippingTime(String shippingTime) {
        this.shippingTime = shippingTime;
    }

    public Timestamp getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(Timestamp orderTime) {
        this.orderTime = orderTime;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }
}

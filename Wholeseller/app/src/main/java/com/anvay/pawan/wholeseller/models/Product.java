/**
 * coded by Pawan Singh Harariya
 */
package com.anvay.pawan.wholeseller.models;

public class Product {
    String id, name, brand, details, category, imageUrl, ownerId, sku, sla, originCountry, mName, mAddress, mContact, parentCategory, childCategory;
    int stockQuantity, minQuantity, maxQuantity;
    double price, discount, gst;

    public Product() {
        //empty constructor for firebase
    }

    public Product(String id, String ownerId, String name, String brand, String details, String category, String imageUrl, String sku,
                   String sla, String originCountry, String mName, String mAddress, String mContact, String parentCategory,
                   String childCategory, int stockQuantity, double gst, int minQuantity, int maxQuantity, double price, double discount) {
        this.category = category;
        this.id = id;
        this.name = name;
        this.brand = brand;
        this.details = details;
        this.price = price;
        this.stockQuantity = stockQuantity;
        this.imageUrl = imageUrl;
        this.sku = sku;
        this.sla = sla;
        this.originCountry = originCountry;
        this.mName = mName;
        this.mAddress = mAddress;
        this.mContact = mContact;
        this.parentCategory = parentCategory;
        this.childCategory = childCategory;
        this.gst = gst;
        this.minQuantity = minQuantity;
        this.maxQuantity = maxQuantity;
        this.discount = discount;
        this.ownerId = ownerId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getStockQuantity() {
        return stockQuantity;
    }

    public void setStockQuantity(int stockQuantity) {
        this.stockQuantity = stockQuantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getsla() {
        return sla;
    }

    public void setsla(String sla) {
        this.sla = sla;
    }

    public String getOriginCountry() {
        return originCountry;
    }

    public void setOriginCountry(String originCountry) {
        this.originCountry = originCountry;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public String getmAddress() {
        return mAddress;
    }

    public void setmAddress(String mAddress) {
        this.mAddress = mAddress;
    }

    public String getmContact() {
        return mContact;
    }

    public void setmContact(String mContact) {
        this.mContact = mContact;
    }

    public String getParentCategory() {
        return parentCategory;
    }

    public void setParentCategory(String parentCategory) {
        this.parentCategory = parentCategory;
    }

    public String getChildCategory() {
        return childCategory;
    }

    public void setChildCategory(String childCategory) {
        this.childCategory = childCategory;
    }

    public double getGst() {
        return gst;
    }

    public void setGst(double gst) {
        this.gst = gst;
    }

    public int getMinQuantity() {
        return minQuantity;
    }

    public void setMinQuantity(int minQuantity) {
        this.minQuantity = minQuantity;
    }

    public int getMaxQuantity() {
        return maxQuantity;
    }

    public void setMaxQuantity(int maxQuantity) {
        this.maxQuantity = maxQuantity;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }
}

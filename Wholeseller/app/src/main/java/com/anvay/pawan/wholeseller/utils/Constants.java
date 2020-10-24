package com.anvay.pawan.wholeseller.utils;

public class Constants {
    public static final String
            CATEGORY_KEY = "category",
            PRODUCT_ID_KEY = "productId",
            FIREBASE_ID = "firebaseId",
            MOBILE_NUMBER = "mobileNumber",
            LOGIN_STATUS = "loginStatus",
            PROFILE_STATUS = "profileStatus",
            BANK_DETAILS_STATUS = "bankDetails",
            SELLER_NAME = "sellerName";

    public static final int
            STATUS_PENDING = 0,
            STATUS_SHIPPED = 1,
            STATUS_COMPLETED = 2,
            STATUS_RETURNED = -1,
            ACCOUNT_ACTIVE = 1,
            ACCOUNT_INACTIVE = 0,
            ACCOUNT_SUSPENDED = -1;
    public static final boolean
            SENDER_SELLER = false,
            SENDER_ADMIN = true;
    public static final String
            PRODUCTS_PATH = "products/categories",
            USERS_PATH = "wholesellers",
            ORDERS_PATH = "orders",
            INACTIVE_SELLERS_PATH = "inactiveSellers",
            SELLER_COMPLAINTS_PATH = "sellerIssues",
            RETAILER_COMPLAINTS_PATH = "retailerComplaints",
            SELLER_DETAILS = "sellerDetails",
            BANK_DETAILS_PATH = "sellerBankDetails";
}

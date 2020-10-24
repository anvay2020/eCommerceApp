/**
 * coded by Pawan Singh Harariya
 */
package com.anvay.pawan.wholeseller.activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.anvay.pawan.wholeseller.R;
import com.anvay.pawan.wholeseller.models.Order;
import com.anvay.pawan.wholeseller.utils.Constants;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;
import java.util.Objects;

public class OrderDetailsActivity extends AppCompatActivity {
    private TextView productName, productId, orderedId, transactionId, sku, orderValue, orderQuantity, orderTime, customerName, customerAddress,
            customerContact, trackingId;
    private Button shipOrderButton;
    private View loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);
        getUI();
        loading.setVisibility(View.VISIBLE);
        SharedPreferences sharedPreferences = getSharedPreferences("app", MODE_PRIVATE);
        String firebaseId = sharedPreferences.getString(Constants.FIREBASE_ID, null);
        String orderId = Objects.requireNonNull(getIntent().getExtras()).getString("orderId");
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        assert orderId != null;
        assert firebaseId != null;
        db.collection(Constants.ORDERS_PATH)
                .document(orderId)
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        Order order = documentSnapshot.toObject(Order.class);
                        assert order != null;
                        productName.setText(order.getProductName());
                        productId.setText(order.getProductId());
                        orderedId.setText(documentSnapshot.getId());
                        transactionId.setText(order.getTransactionId());
                        sku.setText(order.getSku());
                        orderValue.setText(order.getOrderValue());
                        orderQuantity.setText(order.getOrderQuantity());
                        Calendar calendar = Calendar.getInstance();
                        calendar.setTimeInMillis(order.getOrderTime().getSeconds() * 1000);
                        String date = DateFormat.format("dd-MM-yyyy hh:mm", calendar).toString();
                        orderTime.setText(date);
                        customerName.setText(order.getCustomerName());
                        customerAddress.setText(order.getCustomerAddress());
                        customerContact.setText(order.getCustomerContact());
                        if (order.getStatus() != Constants.STATUS_PENDING)
                            trackingId.setText(order.getTrackingId());
                        loading.setVisibility(View.GONE);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        loading.setVisibility(View.GONE);
                        Toast.makeText(OrderDetailsActivity.this, "Unknown Error", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void getUI() {
        productName = findViewById(R.id.product_name);
        productId = findViewById(R.id.product_id);
        orderedId = findViewById(R.id.order_id);
        trackingId = findViewById(R.id.tracking_id_text);
        transactionId = findViewById(R.id.transaction_id);
        sku = findViewById(R.id.sku);
        orderValue = findViewById(R.id.order_value);
        orderQuantity = findViewById(R.id.order_quantity);
        orderTime = findViewById(R.id.order_time);
        customerName = findViewById(R.id.customer_name);
        customerAddress = findViewById(R.id.customer_address);
        customerContact = findViewById(R.id.customer_contact);
        shipOrderButton = findViewById(R.id.ship_order_button);
        loading = findViewById(R.id.loading);
    }
}
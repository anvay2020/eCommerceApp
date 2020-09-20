package com.anvay.pawan.wholeseller.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.anvay.pawan.wholeseller.R;
import com.anvay.pawan.wholeseller.utils.Constants;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ShipOrderActivity extends AppCompatActivity {
    private View loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ship_order);
        SharedPreferences sharedPreferences = getSharedPreferences("app", MODE_PRIVATE);
        final String firebaseId = sharedPreferences.getString(Constants.FIREBASE_ID, null);
        final String orderId = Objects.requireNonNull(getIntent().getExtras()).getString("orderId");
        TextView orderIdText = findViewById(R.id.order_id_text);
        orderIdText.setText(orderId);
        final EditText trackingIdText = findViewById(R.id.tracking_id_text);
        loading = findViewById(R.id.loading);
        Button confirmButton = findViewById(R.id.confirm_button);
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(trackingIdText.getText().toString()))
                    Toast.makeText(ShipOrderActivity.this, "Enter Tracking Id", Toast.LENGTH_SHORT).show();
                else
                    confirmShipping(firebaseId, orderId, trackingIdText.getText().toString());
            }
        });
    }

    private void confirmShipping(String firebaseId, String orderId, String trackingId) {
        loading.setVisibility(View.VISIBLE);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Map<String, Object> data = new HashMap<>();
        data.put("trackingId", trackingId);
        data.put("status", Constants.STATUS_SHIPPED);
        db.collection(Constants.ORDERS_PATH).document(firebaseId).collection(Constants.ORDERS_PATH).document(orderId)
                .update(data)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(ShipOrderActivity.this, "Order Shipped", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(ShipOrderActivity.this, OrdersActivity.class);
                        i.putExtra("status", Constants.STATUS_PENDING);
                        startActivity(i);
                        finish();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(ShipOrderActivity.this, "Unknown Error", Toast.LENGTH_SHORT).show();
                        loading.setVisibility(View.GONE);
                    }
                });
    }
}
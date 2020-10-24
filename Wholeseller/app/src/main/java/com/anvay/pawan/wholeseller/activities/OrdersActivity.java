/**
 * coded by Pawan Singh Harariya
 */
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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.anvay.pawan.wholeseller.R;
import com.anvay.pawan.wholeseller.adapters.OrdersListAdapter;
import com.anvay.pawan.wholeseller.models.Order;
import com.anvay.pawan.wholeseller.utils.Constants;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class OrdersActivity extends AppCompatActivity implements OrdersListAdapter.OrdersClickListener, OrdersListAdapter.ShipOrderClickListener {
    private ArrayList<Order> ordersList;
    private View loading, shippingLayout;
    private TextView orderIdText;
    private String orderId;
    private int position;
    private OrdersListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders);
        loading = findViewById(R.id.loading);
        loading.setVisibility(View.VISIBLE);
        SharedPreferences sharedPreferences = getSharedPreferences("app", MODE_PRIVATE);
        final String firebaseId = sharedPreferences.getString(Constants.FIREBASE_ID, null);
        int status = Objects.requireNonNull(getIntent().getExtras()).getInt("status", 0);
        TextView orderStatus = findViewById(R.id.orders_status);
        if (status == Constants.STATUS_PENDING)
            orderStatus.setText("Pending Orders");
        else if (status == Constants.STATUS_COMPLETED)
            orderStatus.setText("Completed Orders");
        else if (status == Constants.STATUS_RETURNED)
            orderStatus.setText("Returned Orders");
        else if (status == Constants.STATUS_SHIPPED)
            orderStatus.setText("Shipped Orders");
        RecyclerView ordersRecycler = findViewById(R.id.orders_recycler);
        ordersList = new ArrayList<>();
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        ordersRecycler.setLayoutManager(layoutManager);
        adapter = new OrdersListAdapter(this, ordersList, this, this);
        ordersRecycler.setAdapter(adapter);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        assert firebaseId != null;
        db.collection(Constants.ORDERS_PATH)
                .whereEqualTo("sellerId", firebaseId)
                .whereEqualTo("status", status)
                .orderBy("orderTime", Query.Direction.DESCENDING)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        ordersList.clear();
                        for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                            Order order = document.toObject(Order.class);
                            order.setOrderId(document.getId());
                            ordersList.add(order);
                        }
                        adapter.notifyDataSetChanged();
                        loading.setVisibility(View.GONE);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        loading.setVisibility(View.GONE);
                        Toast.makeText(OrdersActivity.this, "Unknown Error", Toast.LENGTH_SHORT).show();
                    }
                });
        orderIdText = findViewById(R.id.order_id_text);
        shippingLayout = findViewById(R.id.shipping_layout);
        shippingLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shippingLayout.setVisibility(View.GONE);
            }
        });
        final EditText trackingIdText = findViewById(R.id.tracking_id_text);
        Button confirmButton = findViewById(R.id.confirm_button);
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(trackingIdText.getText().toString()))
                    Toast.makeText(OrdersActivity.this, "Enter Tracking Id", Toast.LENGTH_SHORT).show();
                else
                    confirmShipping(firebaseId, orderId, trackingIdText.getText().toString());
            }
        });
    }

    @Override
    public void OnOrderItemClicked(int position) {
        orderId = ordersList.get(position).getOrderId();
        Intent i = new Intent(OrdersActivity.this, OrderDetailsActivity.class);
        i.putExtra("orderId", orderId);
        startActivity(i);
    }

    @Override
    public void OnOrderShipped(int position) {
        orderId = ordersList.get(position).getOrderId();
        orderIdText.setText(orderId);
        this.position = position;
        shippingLayout.setVisibility(View.VISIBLE);
    }

    private void confirmShipping(String firebaseId, String orderId, String trackingId) {
        loading.setVisibility(View.VISIBLE);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Map<String, Object> data = new HashMap<>();
        data.put("trackingId", trackingId);
        data.put("status", Constants.STATUS_SHIPPED);
        db.collection(Constants.SELLER_DETAILS).document(firebaseId)
                .update("shippedOrders", FieldValue.increment(1), "pendingOrders", FieldValue.increment(-1));
        db.collection(Constants.ORDERS_PATH).document(orderId)
                .update(data)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(OrdersActivity.this, "Order Shipped", Toast.LENGTH_SHORT).show();
                        shippingLayout.setVisibility(View.GONE);
                        ordersList.remove(position);
                        adapter.notifyItemRemoved(position);
                        loading.setVisibility(View.GONE);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(OrdersActivity.this, "Unknown Error", Toast.LENGTH_SHORT).show();
                        loading.setVisibility(View.GONE);
                    }
                });
    }
}
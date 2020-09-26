/**
 * coded by Pawan Singh Harariya
 */
package com.anvay.pawan.wholeseller.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.anvay.pawan.wholeseller.R;
import com.anvay.pawan.wholeseller.activities.OrdersActivity;
import com.anvay.pawan.wholeseller.models.SellerDetails;
import com.anvay.pawan.wholeseller.utils.Constants;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class OrdersFragment extends Fragment {
    private Context context;

    public OrdersFragment(Context context) {
        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_orders, container, false);
        CardView pendingOrders = view.findViewById(R.id.pending_orders);
        final CardView returnedOrders = view.findViewById(R.id.returned_orders);
        CardView completedOrders = view.findViewById(R.id.completed_orders);
        CardView shippedOrders = view.findViewById(R.id.shipped_orders);
        final TextView totalSales = view.findViewById(R.id.total_sales);
        final TextView walletBalance = view.findViewById(R.id.wallet_balance);
        final TextView noPendingOrders = view.findViewById(R.id.no_pending_orders);
        final TextView noCompletedOrders = view.findViewById(R.id.no_completed_orders);
        final TextView noReturnedOrders = view.findViewById(R.id.no_returned_orders);
        final TextView noShippedOrders = view.findViewById(R.id.no_shipped_orders);
        final View loading = view.findViewById(R.id.loading);
        loading.setVisibility(View.VISIBLE);
        SharedPreferences sharedPreferences = context.getSharedPreferences("app", Context.MODE_PRIVATE);
        String firebaseId = sharedPreferences.getString(Constants.FIREBASE_ID, null);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        assert firebaseId != null;
        db.collection(Constants.SELLER_DETAILS).document(firebaseId)
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        SellerDetails sellerDetails = documentSnapshot.toObject(SellerDetails.class);
                        assert sellerDetails != null;
                        totalSales.setText(String.valueOf(sellerDetails.getTotalSales()));
                        walletBalance.setText(String.valueOf(sellerDetails.getWalletBalance()));
                        noPendingOrders.setText(String.valueOf(sellerDetails.getPendingOrders()));
                        noCompletedOrders.setText(String.valueOf(sellerDetails.getCompletedOrders()));
                        noReturnedOrders.setText(String.valueOf(sellerDetails.getReturnedOrders()));
                        noShippedOrders.setText(String.valueOf(sellerDetails.getShippedOrders()));
                        loading.setVisibility(View.GONE);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        loading.setVisibility(View.GONE);
                        Toast.makeText(context, "Error fetching orders", Toast.LENGTH_SHORT).show();
                    }
                });
        pendingOrders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, OrdersActivity.class);
                i.putExtra("status", Constants.STATUS_PENDING);
                startActivity(i);
            }
        });
        returnedOrders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, OrdersActivity.class);
                i.putExtra("status", Constants.STATUS_RETURNED);
                startActivity(i);
            }
        });
        completedOrders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, OrdersActivity.class);
                i.putExtra("status", Constants.STATUS_COMPLETED);
                startActivity(i);
            }
        });
        shippedOrders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, OrdersActivity.class);
                i.putExtra("status", Constants.STATUS_SHIPPED);
                startActivity(i);
            }
        });
        return view;
    }
}

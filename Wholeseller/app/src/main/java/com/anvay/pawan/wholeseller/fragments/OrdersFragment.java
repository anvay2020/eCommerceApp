/**
 * coded by Pawan Singh Harariya
 */
package com.anvay.pawan.wholeseller.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.anvay.pawan.wholeseller.R;
import com.anvay.pawan.wholeseller.activities.OrdersActivity;
import com.anvay.pawan.wholeseller.utils.Constants;

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
        CardView returnedOrders = view.findViewById(R.id.returned_orders);
        CardView completedOrders = view.findViewById(R.id.completed_orders);
        CardView shippedOrders = view.findViewById(R.id.shipped_orders);
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

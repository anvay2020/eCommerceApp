/**
 * coded by Pawan Singh Harariya
 */
package com.anvay.pawan.wholeseller.adapters;

import android.content.Context;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.anvay.pawan.wholeseller.R;
import com.anvay.pawan.wholeseller.models.Order;
import com.anvay.pawan.wholeseller.utils.Constants;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Calendar;

public class OrdersListAdapter extends RecyclerView.Adapter<OrdersListAdapter.OrdersViewHolder> {
    private Context context;
    private ArrayList<Order> ordersList;
    private OrdersClickListener ordersClickListener;
    private ShipOrderClickListener shipOrderClickListener;

    public OrdersListAdapter(Context context, ArrayList<Order> ordersList, OrdersClickListener ordersClickListener, ShipOrderClickListener shipOrderClickListener) {
        this.context = context;
        this.ordersList = ordersList;
        this.ordersClickListener = ordersClickListener;
        this.shipOrderClickListener = shipOrderClickListener;
    }

    @NonNull
    @Override
    public OrdersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_item_orders, parent, false);
        return new OrdersViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrdersViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return ordersList == null ? 0 : ordersList.size();
    }

    public class OrdersViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView productName, sku, orderValue, orderQuantity, orderTime;
        private ImageView productImage;
        private Button shipOrderButton;

        public OrdersViewHolder(@NonNull View itemView) {
            super(itemView);
            productName = itemView.findViewById(R.id.product_name);
            sku = itemView.findViewById(R.id.sku);
            orderQuantity = itemView.findViewById(R.id.order_quantity);
            orderTime = itemView.findViewById(R.id.order_time);
            productImage = itemView.findViewById(R.id.product_image);
            shipOrderButton = itemView.findViewById(R.id.ship_order_button);
            orderValue = itemView.findViewById(R.id.order_value);
        }

        public void bind(int position) {
            Order order = ordersList.get(position);
            productName.setText(order.getProductName());
            sku.setText(order.getSku());
            orderValue.setText(order.getOrderValue());
            orderQuantity.setText(order.getOrderQuantity());
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(order.getOrderTime().getSeconds() * 1000);
            String date = DateFormat.format("dd-MM-yyyy hh:mm", calendar).toString();
            orderTime.setText(date);
            Picasso.get()
                    .load(order.getImageUrl())
                    .into(productImage);
            itemView.setOnClickListener(this);
            if (order.getStatus() != Constants.STATUS_PENDING)
                shipOrderButton.setVisibility(View.GONE);
            shipOrderButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    shipOrderClickListener.OnOrderShipped(getAdapterPosition());
                }
            });
        }

        @Override
        public void onClick(View v) {
            ordersClickListener.OnOrderItemClicked(getAdapterPosition());
        }
    }

    public interface OrdersClickListener {
        void OnOrderItemClicked(int position);
    }

    public interface ShipOrderClickListener {
        void OnOrderShipped(int position);
    }
}

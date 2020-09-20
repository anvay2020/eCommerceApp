/**
 * coded by Pawan Singh Harariya
 */
package com.anvay.pawan.wholeseller.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.anvay.pawan.wholeseller.R;
import com.anvay.pawan.wholeseller.models.Product;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ProductsListAdapter extends RecyclerView.Adapter<ProductsListAdapter.ProductsViewHolder> {
    private Context context;
    private ArrayList<Product> productsList;
    private ProductsClickListener productsClickListener;

    public ProductsListAdapter(Context context, ArrayList<Product> productsList, ProductsClickListener productsClickListener) {
        this.context = context;
        this.productsList = productsList;
        this.productsClickListener = productsClickListener;
    }

    @NonNull
    @Override
    public ProductsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_item_products, parent, false);
        return new ProductsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductsViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return productsList == null ? 0 : productsList.size();
    }

    public class ProductsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView name, brand, price, stockQuantity;
        private ImageView productImage;

        public ProductsViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            brand = itemView.findViewById(R.id.brand);
            price = itemView.findViewById(R.id.price);
            stockQuantity = itemView.findViewById(R.id.stockQuantity);
            productImage = itemView.findViewById(R.id.product_image);
        }

        public void bind(int position) {
            Product product = productsList.get(position);
            name.setText(product.getName());
            brand.setText(product.getBrand());
            price.setText(String.valueOf(product.getPrice()));
            stockQuantity.setText(String.valueOf(product.getStockQuantity()));
            Picasso.get()
                    .load(product.getImageUrl())
                    .into(productImage);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            productsClickListener.OnProductItemClicked(getAdapterPosition());
        }
    }

    public interface ProductsClickListener {
        void OnProductItemClicked(int position);
    }
}

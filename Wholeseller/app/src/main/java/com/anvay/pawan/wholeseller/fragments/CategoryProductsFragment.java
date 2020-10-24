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
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.anvay.pawan.wholeseller.R;
import com.anvay.pawan.wholeseller.activities.AddProductActivity;
import com.anvay.pawan.wholeseller.activities.ProductDetailsActivity;
import com.anvay.pawan.wholeseller.adapters.ProductsListAdapter;
import com.anvay.pawan.wholeseller.models.Product;
import com.anvay.pawan.wholeseller.utils.Constants;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class CategoryProductsFragment extends Fragment implements ProductsListAdapter.ProductsClickListener {
    private Context context;
    private ArrayList<Product> productList;
    private String firebaseId, category;
    private TextView emptyTextView;
    private View loading;
    private ProductsListAdapter adapter;

    @Override
    public void onResume() {
        super.onResume();
        loadProducts();
    }

    public CategoryProductsFragment(Context context) {
        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_category_products, container, false);
        productList = new ArrayList<>();
        assert getArguments() != null;
        SharedPreferences sharedPreferences = context.getSharedPreferences("app", Context.MODE_PRIVATE);
        firebaseId = sharedPreferences.getString(Constants.FIREBASE_ID, null);
        category = getArguments().getString(Constants.CATEGORY_KEY);
        Button addNewProductButton = view.findViewById(R.id.add_new_product_button);
        emptyTextView = view.findViewById(R.id.empty_text_view);
        loading = view.findViewById(R.id.loading);
        RecyclerView previousProductsRecycler = view.findViewById(R.id.previous_products_recycler);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
        adapter = new ProductsListAdapter(context, productList, this);
        previousProductsRecycler.setLayoutManager(layoutManager);
        previousProductsRecycler.setAdapter(adapter);
        loadProducts();
        addNewProductButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, AddProductActivity.class);
                i.putExtra(Constants.CATEGORY_KEY, category);
                startActivity(i);
            }
        });
        return view;
    }

    private void loadProducts() {
        loading.setVisibility(View.VISIBLE);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection(Constants.PRODUCTS_PATH + "/" + category)
                .whereEqualTo("ownerId", firebaseId)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        productList.clear();
                        for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                            Product product = document.toObject(Product.class);
                            productList.add(product);
                        }
                        adapter.notifyDataSetChanged();
                        if (adapter.getItemCount() == 0)
                            emptyTextView.setVisibility(View.VISIBLE);
                        else emptyTextView.setVisibility(View.INVISIBLE);
                        loading.setVisibility(View.GONE);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(context, "Error getting data", Toast.LENGTH_SHORT).show();
                        loading.setVisibility(View.GONE);
                    }
                });
    }

    @Override
    public void OnProductItemClicked(int position) {
        Product product = productList.get(position);
        Intent i = new Intent(context, ProductDetailsActivity.class);
        i.putExtra(Constants.CATEGORY_KEY, product.getCategory());
        i.putExtra(Constants.PRODUCT_ID_KEY, product.getId());
        startActivity(i);
    }
}

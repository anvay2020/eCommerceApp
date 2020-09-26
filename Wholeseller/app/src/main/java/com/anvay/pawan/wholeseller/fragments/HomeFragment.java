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
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.anvay.pawan.wholeseller.R;
import com.anvay.pawan.wholeseller.activities.AccountHealthActivity;
import com.anvay.pawan.wholeseller.activities.ComplaintsActivity;
import com.anvay.pawan.wholeseller.activities.ContactUsActivity;
import com.anvay.pawan.wholeseller.utils.Constants;

public class HomeFragment extends Fragment {
    private View root;
    private Context context;
    private String category;
    private CardView clothingCategory;
    private View layoutMenu, emptyViewMenu;
    private ImageView menuButton;
    private TextView optionAccountHealth, optionContactUs, optionComplaints;

    public HomeFragment(Context context) {
        this.context = context;
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_home, container, false);
        getUI();
        clothingCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                category = "Clothing";
                openFragment();
            }
        });
        emptyViewMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layoutMenu.setVisibility(View.GONE);
            }
        });
        menuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layoutMenu.setVisibility(View.VISIBLE);
            }
        });

        optionAccountHealth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, AccountHealthActivity.class);
                startActivity(i);
            }
        });
        optionComplaints.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, ComplaintsActivity.class);
                startActivity(i);
            }
        });
        optionContactUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, ContactUsActivity.class);
                startActivity(i);
            }
        });
        return root;
    }

    private void openFragment() {
        FragmentManager fragmentManager = ((AppCompatActivity) context).getSupportFragmentManager();
        Bundle bundle = new Bundle();
        bundle.putString(Constants.CATEGORY_KEY, category);
        CategoryProductsFragment fragment = new CategoryProductsFragment(context);
        fragment.setArguments(bundle);
        fragmentManager.beginTransaction()
                .add(R.id.nav_host_fragment, fragment)
                .addToBackStack("tag")
                .commit();
    }

    private void getUI() {
        clothingCategory = root.findViewById(R.id.clothing_category);
        layoutMenu = root.findViewById(R.id.layout_menu);
        optionContactUs = root.findViewById(R.id.option_contact_us);
        optionComplaints = root.findViewById(R.id.option_complaints);
        optionAccountHealth = root.findViewById(R.id.option_account_health);
        menuButton = root.findViewById(R.id.menu_button);
        emptyViewMenu = root.findViewById(R.id.empty_view_menu);
    }
}
/**
 * coded by Pawan Singh Harariya
 */
package com.anvay.pawan.wholeseller.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.anvay.pawan.wholeseller.R;
import com.anvay.pawan.wholeseller.fragments.HomeFragment;
import com.anvay.pawan.wholeseller.fragments.OrdersFragment;
import com.anvay.pawan.wholeseller.fragments.ProfileFragment;
import com.anvay.pawan.wholeseller.utils.Constants;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    //    @Override
//    public void onBackPressed() {
//        FragmentManager fm = getSupportFragmentManager();
//        if (navigation.getSelectedItemId() != R.id.navigation_home && fm.getBackStackEntryCount() == 0) {
//            navigation.setSelectedItemId(R.id.navigation_home);
//        } else super.onBackPressed();
//    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SharedPreferences sharedPreferences = getSharedPreferences("app", MODE_PRIVATE);
        if (!sharedPreferences.getBoolean(Constants.LOGIN_STATUS, false)) {
            Intent i = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(i);
        } else if (!sharedPreferences.getBoolean(Constants.PROFILE_STATUS, false)) {
            Intent i = new Intent(MainActivity.this, SignUpActivity.class);
            startActivity(i);
        }
        BottomNavigationView navigation = findViewById(R.id.nav_view);
        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_home:
                        viewFragment(R.id.navigation_home);
                        return true;
                    case R.id.navigation_orders:
                        viewFragment(R.id.navigation_orders);
                        return true;
                    case R.id.navigation_profile:
                        viewFragment(R.id.navigation_profile);
                        return true;
                }
                return false;
            }
        });
        navigation.setSelectedItemId(R.id.navigation_home);
    }

    private void viewFragment(int id) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment;
        if (id == R.id.navigation_orders)
            fragment = new OrdersFragment(MainActivity.this);
        else if (id == R.id.navigation_profile)
            fragment = new ProfileFragment(MainActivity.this);
        else
            fragment = new HomeFragment(MainActivity.this);
        fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        fragmentManager.beginTransaction()
                .replace(R.id.nav_host_fragment, fragment)
                .commit();
    }
}
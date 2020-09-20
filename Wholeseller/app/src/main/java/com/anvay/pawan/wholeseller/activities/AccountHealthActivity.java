package com.anvay.pawan.wholeseller.activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.anvay.pawan.wholeseller.R;
import com.anvay.pawan.wholeseller.models.User;
import com.anvay.pawan.wholeseller.utils.Constants;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.switchmaterial.SwitchMaterial;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class AccountHealthActivity extends AppCompatActivity {
    private SwitchMaterial accountHealthSwitch;
    private TextView accountHealthText, complaintType1;
    private View loading;
    private String firebaseId;
    private int accountHealth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_health);
        getUI();
        loading.setVisibility(View.VISIBLE);
        SharedPreferences sharedPreferences = getSharedPreferences("app", MODE_PRIVATE);
        firebaseId = sharedPreferences.getString(Constants.FIREBASE_ID, null);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection(Constants.USERS_PATH).document(firebaseId).get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        User user = documentSnapshot.toObject(User.class);
                        assert user != null;
                        accountHealth = user.getAccountHealth();
                        setDetails();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(AccountHealthActivity.this, "Failed to load account heath", Toast.LENGTH_LONG).show();
                        onBackPressed();
                    }
                });
        complaintType1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        accountHealthSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.isSelected())
                    changeDetails(Constants.ACCOUNT_ACTIVE);
                else
                    changeDetails(Constants.ACCOUNT_INACTIVE);
            }
        });
    }

    private void changeDetails(final int status) {
        loading.setVisibility(View.VISIBLE);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection(Constants.USERS_PATH).document(firebaseId).update("accountHealth", status)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        accountHealth = status;
                        setDetails();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        loading.setVisibility(View.GONE);
                        Toast.makeText(AccountHealthActivity.this, "Unknown Error", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void setDetails() {
        if (accountHealth == Constants.ACCOUNT_SUSPENDED) {
            accountHealthSwitch.setEnabled(false);
            accountHealthText.setText("Your account has been suspended");
        } else if (accountHealth == Constants.ACCOUNT_ACTIVE) {
            accountHealthSwitch.setSelected(true);
            accountHealthSwitch.setEnabled(true);
            accountHealthText.setText("Your account is active");
        } else if (accountHealth == Constants.ACCOUNT_INACTIVE) {
            accountHealthSwitch.setSelected(false);
            accountHealthSwitch.setEnabled(true);
            accountHealthText.setText("Your account is inactive");
        }
        loading.setVisibility(View.GONE);
    }

    private void getUI() {
        accountHealthSwitch = findViewById(R.id.account_health_switch);
        accountHealthText = findViewById(R.id.account_health_text);
        complaintType1 = findViewById(R.id.complaint_type_1);
        loading = findViewById(R.id.loading);
    }
}
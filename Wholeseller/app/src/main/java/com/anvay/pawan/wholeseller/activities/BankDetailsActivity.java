/**
 * coded by Pawan Singh Harariya
 */
package com.anvay.pawan.wholeseller.activities;

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
import com.anvay.pawan.wholeseller.models.BankDetails;
import com.anvay.pawan.wholeseller.utils.Constants;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class BankDetailsActivity extends AppCompatActivity {
    private EditText accountName, accountNumber, ifscCode, branchName, confirmAccountNumber;
    private TextView confirmAccountLabel;
    private String firebaseId;
    private Button saveBankDetails;
    private View loading;
    private SharedPreferences sharedPreferences;
    private boolean detailsExist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bank_details);
        sharedPreferences = getSharedPreferences("app", MODE_PRIVATE);
        firebaseId = sharedPreferences.getString(Constants.FIREBASE_ID, null);
        getUI();
        detailsExist = sharedPreferences.getBoolean(Constants.BANK_DETAILS_STATUS, false);
        changeEdit(detailsExist);
        if (detailsExist) {
            loadDetails();
        }
        saveBankDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (detailsExist) {
                    detailsExist = false;
                    changeEdit(false);
                } else {
                    if (TextUtils.isEmpty(accountName.getText().toString()) || TextUtils.isEmpty(accountNumber.getText().toString())
                            || TextUtils.isEmpty(confirmAccountNumber.getText().toString()) || TextUtils.isEmpty(branchName.getText().toString()))
                        Toast.makeText(BankDetailsActivity.this, "Fill all details", Toast.LENGTH_SHORT).show();
                    else if (!(accountNumber.getText().toString().equals(confirmAccountNumber.getText().toString())))
                        Toast.makeText(BankDetailsActivity.this, "Account Number don't match", Toast.LENGTH_SHORT).show();
                    else
                        saveBankDetails();
                }
            }
        });
    }

    private void saveBankDetails() {
        loading.setVisibility(View.VISIBLE);
        final BankDetails bankDetails = new BankDetails(accountName.getText().toString(), accountNumber.getText().toString(), ifscCode.getText().toString(), branchName.getText().toString());
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection(Constants.BANK_DETAILS_PATH)
                .document(firebaseId)
                .set(bankDetails)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        changeEdit(true);
                        detailsExist = true;
                        sharedPreferences.edit().putBoolean(Constants.BANK_DETAILS_STATUS, true).apply();
                        loading.setVisibility(View.GONE);
                        Toast.makeText(BankDetailsActivity.this, "Details Saved", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(BankDetailsActivity.this, "Unknown Error", Toast.LENGTH_SHORT).show();
                        loading.setVisibility(View.GONE);
                    }
                });
    }

    private void changeEdit(boolean detailsExist) {
        accountNumber.setEnabled(!detailsExist);
        accountName.setEnabled(!detailsExist);
        ifscCode.setEnabled(!detailsExist);
        branchName.setEnabled(!detailsExist);
        confirmAccountNumber.setEnabled(!detailsExist);
        if (detailsExist) {
            saveBankDetails.setText("Edit");
            confirmAccountNumber.setVisibility(View.GONE);
            confirmAccountLabel.setVisibility(View.GONE);
        } else {
            saveBankDetails.setText("Save");
            confirmAccountNumber.setVisibility(View.VISIBLE);
            confirmAccountLabel.setVisibility(View.VISIBLE);
        }
    }

    private void loadDetails() {
        loading.setVisibility(View.VISIBLE);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection(Constants.BANK_DETAILS_PATH)
                .document(firebaseId)
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        BankDetails bankDetails = documentSnapshot.toObject(BankDetails.class);
                        assert bankDetails != null;
                        accountName.setText(bankDetails.getHolderName());
                        accountNumber.setText(bankDetails.getAccountNo());
                        ifscCode.setText(bankDetails.getIfscCode());
                        branchName.setText(bankDetails.getBranchName());
                        loading.setVisibility(View.GONE);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(BankDetailsActivity.this, "Unknown Error", Toast.LENGTH_SHORT).show();
                        loading.setVisibility(View.GONE);
                    }
                });
    }

    private void getUI() {
        accountName = findViewById(R.id.account_name);
        accountNumber = findViewById(R.id.account_number);
        confirmAccountNumber = findViewById(R.id.confirm_account_number);
        ifscCode = findViewById(R.id.ifsc_code);
        saveBankDetails = findViewById(R.id.save_bank_details);
        branchName = findViewById(R.id.branch_name);
        loading = findViewById(R.id.loading);
        confirmAccountLabel = findViewById(R.id.account2_label);
    }
}
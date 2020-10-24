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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.anvay.pawan.wholeseller.R;
import com.anvay.pawan.wholeseller.models.SellerDetails;
import com.anvay.pawan.wholeseller.models.User;
import com.anvay.pawan.wholeseller.utils.Constants;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

public class SignUpActivity extends AppCompatActivity {
    private EditText nameText, emailText, addressText, pincodeText, landmarkText, pickAddressText, returnAddressText, gstText, panText, mobileText;
    private String firebaseId, name, email, mobileNumber, address, pincode, landmark, pickAddress, returnAddress, gst, pan;
    private Button createProfile;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private View loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        getUI();
        sharedPreferences = getSharedPreferences("app", MODE_PRIVATE);
        firebaseId = sharedPreferences.getString(Constants.FIREBASE_ID, null);
        mobileNumber = sharedPreferences.getString(Constants.MOBILE_NUMBER, null);
        mobileText.setText(mobileNumber);
        createProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = nameText.getText().toString();
                email = emailText.getText().toString();
                address = addressText.getText().toString();
                pincode = pincodeText.getText().toString();
                landmark = landmarkText.getText().toString();
                pickAddress = pickAddressText.getText().toString();
                returnAddress = returnAddressText.getText().toString();
                gst = gstText.getText().toString();
                pan = panText.getText().toString();
                if (TextUtils.isEmpty(name) || TextUtils.isEmpty(email) || TextUtils.isEmpty(address) || TextUtils.isEmpty(pincode)
                        || TextUtils.isEmpty(gst) || TextUtils.isEmpty(pan) || TextUtils.isEmpty(pickAddress) || TextUtils.isEmpty(returnAddress))
                    Toast.makeText(SignUpActivity.this, "Fill all fields", Toast.LENGTH_SHORT).show();
                else
                    postData();
            }
        });
    }

    private void postData() {
        loading.setVisibility(View.VISIBLE);
        User user = new User(firebaseId, name, email, mobileNumber, address, landmark, pincode, gst, pan, pickAddress, returnAddress);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        SellerDetails sellerDetails = new SellerDetails(firebaseId);
        db.collection(Constants.SELLER_DETAILS).document(firebaseId).set(sellerDetails);
        db.collection(Constants.USERS_PATH).document(firebaseId).set(user)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(SignUpActivity.this, "Profile Created", Toast.LENGTH_SHORT).show();
                        editor = sharedPreferences.edit();
                        editor.putString(Constants.SELLER_NAME, name);
                        editor.putBoolean(Constants.PROFILE_STATUS, true);
                        editor.apply();
                        Intent i = new Intent(SignUpActivity.this, MainActivity.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(i);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(SignUpActivity.this, "Unknown Error", Toast.LENGTH_SHORT).show();
                        loading.setVisibility(View.GONE);
                    }
                });
    }

    private void getUI() {
        nameText = findViewById(R.id.name_text);
        emailText = findViewById(R.id.email_text);
        addressText = findViewById(R.id.address_text);
        pincodeText = findViewById(R.id.pincode_text);
        landmarkText = findViewById(R.id.landmark_text);
        pickAddressText = findViewById(R.id.pick_address_text);
        returnAddressText = findViewById(R.id.return_address_text);
        gstText = findViewById(R.id.gst_text);
        panText = findViewById(R.id.pan_text);
        loading = findViewById(R.id.loading);
        mobileText = findViewById(R.id.mobile_text);
        createProfile = findViewById(R.id.create_profile);
    }
}
/**
 * coded by Pawan Singh Harariya
 */
package com.anvay.pawan.wholeseller.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.anvay.pawan.wholeseller.R;
import com.anvay.pawan.wholeseller.models.User;
import com.anvay.pawan.wholeseller.utils.Constants;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class ProfileFragment extends Fragment {
    private Context context;
    private EditText nameText, emailText, addressText, pincodeText, landmarkText, pickAddressText, returnAddressText, gstText, panText, mobileText;
    private String firebaseId, name, email, address, pincode, landmark, pickAddress, returnAddress;
    private Button editProfile;
    private View view;
    private boolean isEditEnabled = false;

    public ProfileFragment(Context context) {
        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_profile, container, false);
        getUI();
        SharedPreferences sharedPreferences = context.getSharedPreferences("app", Context.MODE_PRIVATE);
        firebaseId = sharedPreferences.getString(Constants.FIREBASE_ID, null);
        loadProfile();
        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isEditEnabled) {
                    name = nameText.getText().toString();
                    email = emailText.getText().toString();
                    address = addressText.getText().toString();
                    pincode = pincodeText.getText().toString();
                    landmark = landmarkText.getText().toString();
                    pickAddress = pickAddressText.getText().toString();
                    returnAddress = returnAddressText.getText().toString();
                    if (TextUtils.isEmpty(name) || TextUtils.isEmpty(email) || TextUtils.isEmpty(address) || TextUtils.isEmpty(pincode)
                            || TextUtils.isEmpty(pickAddress) || TextUtils.isEmpty(returnAddress))
                        Toast.makeText(context, "Fill all fields", Toast.LENGTH_SHORT).show();
                    else
                        postData();
                } else
                    changeEdit(true);
            }
        });
        return view;
    }

    private void changeEdit(boolean b) {
        isEditEnabled = b;
        nameText.setEnabled(b);
        emailText.setEnabled(b);
        pickAddressText.setEnabled(b);
        returnAddressText.setEnabled(b);
        addressText.setEnabled(b);
        pincodeText.setEnabled(b);
        landmarkText.setEnabled(b);
        if (b)
            editProfile.setText("Save Profile");
        else
            editProfile.setText("Edit Profile");
    }

    private void loadProfile() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection(Constants.USERS_PATH).document(firebaseId).get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        User user = documentSnapshot.toObject(User.class);
                        assert user != null;
                        nameText.setText(user.getName());
                        emailText.setText(user.getEmail());
                        mobileText.setText(user.getMobile());
                        addressText.setText(user.getAddress());
                        landmarkText.setText(user.getLandmark());
                        pincodeText.setText(user.getPincode());
                        pickAddressText.setText(user.getPickupAddress());
                        returnAddressText.setText(user.getReturnAddress());
                        gstText.setText(user.getGstNumber());
                        panText.setText(user.getPanNumber());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(context, "Cannot load profile", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void postData() {
        Map<String, Object> data = new HashMap<>();
        data.put("name", name);
        data.put("email", email);
        data.put("address", address);
        data.put("pincode", pincode);
        data.put("landmark", landmark);
        data.put("pickupAddress", pickAddress);
        data.put("returnAddress", returnAddress);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection(Constants.USERS_PATH).document(firebaseId).update(data)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(context, "Profile Updated", Toast.LENGTH_SHORT).show();
                        changeEdit(false);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(context, "Unknown Error", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void getUI() {
        nameText = view.findViewById(R.id.name_text);
        emailText = view.findViewById(R.id.email_text);
        addressText = view.findViewById(R.id.address_text);
        pincodeText = view.findViewById(R.id.pincode_text);
        landmarkText = view.findViewById(R.id.landmark_text);
        pickAddressText = view.findViewById(R.id.pick_address_text);
        returnAddressText = view.findViewById(R.id.return_address_text);
        gstText = view.findViewById(R.id.gst_text);
        panText = view.findViewById(R.id.pan_text);
        mobileText = view.findViewById(R.id.mobile_text);
        editProfile = view.findViewById(R.id.edit_profile);
    }
}

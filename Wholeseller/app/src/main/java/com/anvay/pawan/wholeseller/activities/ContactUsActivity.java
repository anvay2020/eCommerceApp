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
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.anvay.pawan.wholeseller.R;
import com.anvay.pawan.wholeseller.models.Complaint;
import com.anvay.pawan.wholeseller.models.Message;
import com.anvay.pawan.wholeseller.utils.Constants;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class ContactUsActivity extends AppCompatActivity {
    private View loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);
        final EditText titleText = findViewById(R.id.title_text);
        final EditText messageText = findViewById(R.id.message_text);
        final Spinner typeSpinner = findViewById(R.id.type_spinner);
        loading = findViewById(R.id.loading);
        Button finishButton = findViewById(R.id.finish_button);
        SharedPreferences sharedPreferences = getSharedPreferences("app", MODE_PRIVATE);
        final String firebaseId = sharedPreferences.getString(Constants.FIREBASE_ID, null);
        final String name = sharedPreferences.getString(Constants.SELLER_NAME, null);
        finishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = titleText.getText().toString();
                String message = messageText.getText().toString();
                int type = typeSpinner.getSelectedItemPosition();
                if (TextUtils.isEmpty(title) || TextUtils.isEmpty(message))
                    Toast.makeText(ContactUsActivity.this, "Fill all details", Toast.LENGTH_SHORT).show();
                else
                    uploadData(title, message, type, firebaseId, name);
            }
        });
    }

    private void uploadData(String title, String message, int type, String firebaseId, String name) {
        loading.setVisibility(View.VISIBLE);
        Message initial = new Message(message);
        ArrayList<Message> messages = new ArrayList<>();
        messages.add(initial);
        Complaint complaint = new Complaint(name, firebaseId, title, type, messages, null);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection(Constants.SELLER_COMPLAINTS_PATH).document(firebaseId).collection(Constants.SELLER_COMPLAINTS_PATH).document()
                .set(complaint)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(ContactUsActivity.this, "Complaint Posted", Toast.LENGTH_SHORT).show();
                        loading.setVisibility(View.GONE);
                        onBackPressed();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(ContactUsActivity.this, "Unknown Error", Toast.LENGTH_SHORT).show();
                        loading.setVisibility(View.GONE);
                    }
                });
    }
}
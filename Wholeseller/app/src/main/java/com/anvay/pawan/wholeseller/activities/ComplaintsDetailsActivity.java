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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.anvay.pawan.wholeseller.R;
import com.anvay.pawan.wholeseller.adapters.ComplaintsMessageAdapter;
import com.anvay.pawan.wholeseller.models.Complaint;
import com.anvay.pawan.wholeseller.models.Message;
import com.anvay.pawan.wholeseller.utils.Constants;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Objects;

public class ComplaintsDetailsActivity extends AppCompatActivity {
    private ArrayList<Message> messages = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complaints_details);
        final TextView complaintTitle = findViewById(R.id.complaint_title);
        final TextView complaintCategory = findViewById(R.id.complaint_category);
        final RecyclerView messagesRecycler = findViewById(R.id.messages_recycler);
        final EditText replyMessage = findViewById(R.id.reply_message);
        Button sendMessageButton = findViewById(R.id.send_message_button);
        final View loading = findViewById(R.id.loading);
        loading.setVisibility(View.VISIBLE);
        final String complaintId = Objects.requireNonNull(getIntent().getExtras()).getString("complaintId");
        final boolean isRetailerComplaint = getIntent().getBooleanExtra("retailerComplaint", false);
        SharedPreferences sharedPreferences = getSharedPreferences("app", MODE_PRIVATE);
        final String firebaseId = sharedPreferences.getString(Constants.FIREBASE_ID, null);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        assert firebaseId != null;
        assert complaintId != null;
        final ComplaintsMessageAdapter adapter = new ComplaintsMessageAdapter(ComplaintsDetailsActivity.this, messages, isRetailerComplaint);
        DocumentReference docRef;
        if (isRetailerComplaint)
            docRef = db.collection(Constants.RETAILER_COMPLAINTS_PATH).document(complaintId);
        else
            docRef = db.collection(Constants.SELLER_COMPLAINTS_PATH).document(firebaseId).collection(Constants.SELLER_COMPLAINTS_PATH).document(complaintId);
        docRef.get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        Complaint complaint = documentSnapshot.toObject(Complaint.class);
                        assert complaint != null;
                        complaintTitle.setText(complaint.getTitle());
                        complaintCategory.setText(complaint.getComplaintType());
                        messages = complaint.getMessages();
                        LinearLayoutManager layoutManager = new LinearLayoutManager(ComplaintsDetailsActivity.this);
                        layoutManager.setReverseLayout(true);
                        layoutManager.setStackFromEnd(true);
                        messagesRecycler.setLayoutManager(layoutManager);
                        messagesRecycler.setAdapter(adapter);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(ComplaintsDetailsActivity.this, "Cannot load messages", Toast.LENGTH_LONG).show();
                        onBackPressed();
                    }
                });
        sendMessageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(replyMessage.getText().toString()))
                    Toast.makeText(ComplaintsDetailsActivity.this, "Empty message", Toast.LENGTH_SHORT).show();
                else
                    sendMessage();
            }

            private void sendMessage() {
                loading.setVisibility(View.VISIBLE);
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                final Message message = new Message(replyMessage.getText().toString());
                DocumentReference docRef;
                if (isRetailerComplaint)
                    docRef = db.collection(Constants.RETAILER_COMPLAINTS_PATH).document(complaintId);
                else
                    docRef = db.collection(Constants.SELLER_COMPLAINTS_PATH).document(firebaseId).collection(Constants.SELLER_COMPLAINTS_PATH).document(complaintId);
                docRef.set(FieldValue.arrayUnion("messages", message))
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                messages.add(message);
                                adapter.notifyDataSetChanged();
                                replyMessage.setText(null);
                                loading.setVisibility(View.GONE);
                                Toast.makeText(ComplaintsDetailsActivity.this, "Message sent", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(ComplaintsDetailsActivity.this, "Cannot send message", Toast.LENGTH_SHORT).show();
                                loading.setVisibility(View.GONE);
                            }
                        });
            }
        });
    }
}
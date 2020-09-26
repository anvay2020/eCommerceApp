/**
 * coded by Pawan Singh Harariya
 */
package com.anvay.pawan.wholeseller.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.anvay.pawan.wholeseller.R;
import com.anvay.pawan.wholeseller.adapters.ComplaintsAdapter;
import com.anvay.pawan.wholeseller.models.Complaint;
import com.anvay.pawan.wholeseller.utils.Constants;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class RetailerComplaintsActivity extends AppCompatActivity implements ComplaintsAdapter.ComplaintsClickListener {
    private ArrayList<Complaint> complaintsList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retailer_complaints);
        SharedPreferences sharedPreferences = getSharedPreferences("app", MODE_PRIVATE);
        int complaintType = getIntent().getIntExtra("complaintType", 0);
        String firebaseId = sharedPreferences.getString(Constants.FIREBASE_ID, null);
        RecyclerView complaintsRecycler = findViewById(R.id.complaints_recycler);
        final TextView noResults = findViewById(R.id.no_results);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        final ComplaintsAdapter adapter = new ComplaintsAdapter(this, complaintsList, this);
        final View loading = findViewById(R.id.loading);
        complaintsRecycler.setAdapter(adapter);
        loading.setVisibility(View.VISIBLE);
        complaintsRecycler.setLayoutManager(layoutManager);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        assert firebaseId != null;
        db.collection(Constants.RETAILER_COMPLAINTS_PATH)
                .whereEqualTo("sellerId", firebaseId)
                .whereEqualTo("complaintType", complaintType)
                .orderBy("time", Query.Direction.DESCENDING)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        complaintsList.clear();
                        for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                            Complaint complaint = document.toObject(Complaint.class);
                            complaint.setComplaintId(document.getId());
                            complaintsList.add(complaint);
                        }
                        adapter.notifyDataSetChanged();
                        loading.setVisibility(View.GONE);
                        if (complaintsList.size() == 0)
                            noResults.setVisibility(View.VISIBLE);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(RetailerComplaintsActivity.this, "Cannot load complaints", Toast.LENGTH_SHORT).show();
                        onBackPressed();
                    }
                });
    }

    @Override
    public void OnComplaintsItemClicked(int position) {
        Intent i = new Intent(RetailerComplaintsActivity.this, ComplaintsDetailsActivity.class);
        i.putExtra("complaintId", complaintsList.get(position).getComplaintId());
        i.putExtra("retailerComplaint", true);
        startActivity(i);
    }
}
/**
 * coded by Pawan Singh Harariya
 */
package com.anvay.pawan.wholeseller.adapters;

import android.content.Context;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.anvay.pawan.wholeseller.R;
import com.anvay.pawan.wholeseller.models.Complaint;

import java.util.ArrayList;
import java.util.Calendar;

public class ComplaintsAdapter extends RecyclerView.Adapter<ComplaintsAdapter.ComplaintViewHolder> {
    private Context context;
    private ArrayList<Complaint> complaints;
    private ComplaintsClickListener complaintsClickListener;
    private boolean isRetailerComplaint;

    public ComplaintsAdapter(Context context, ArrayList<Complaint> complaints, ComplaintsClickListener complaintsClickListener, boolean isRetailerComplaint) {
        this.context = context;
        this.complaints = complaints;
        this.complaintsClickListener = complaintsClickListener;
        this.isRetailerComplaint = isRetailerComplaint;
    }

    @NonNull
    @Override
    public ComplaintViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_item_complaints, parent, false);
        return new ComplaintViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ComplaintViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return complaints == null ? 0 : complaints.size();
    }

    public class ComplaintViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView complaintTitle, complaintCategory, date;

        public ComplaintViewHolder(@NonNull View itemView) {
            super(itemView);
            complaintTitle = itemView.findViewById(R.id.complaint_title);
            complaintCategory = itemView.findViewById(R.id.complaint_category);
            date = itemView.findViewById(R.id.date);
            itemView.setOnClickListener(this);
        }

        public void bind(int position) {
            Complaint complaint = complaints.get(position);
            complaintTitle.setText(complaint.getTitle());
            if (isRetailerComplaint)
                complaintCategory.setVisibility(View.GONE);
            else {
                int complaintType = complaint.getComplaintType();
                String complaintTypeText = "";
                switch (complaintType) {
                    case 0:
                        complaintTypeText = context.getResources().getString(R.string.seller_complaint0);
                        break;
                    case 1:
                        complaintTypeText = context.getResources().getString(R.string.seller_complaint1);
                        break;
                    case 2:
                        complaintTypeText = context.getResources().getString(R.string.seller_complaint2);
                        break;
                    case 3:
                        complaintTypeText = context.getResources().getString(R.string.seller_complaint3);
                        break;
                }
                complaintCategory.setText(complaintTypeText);
            }
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(complaint.getTime().getSeconds() * 1000);
            String dateString = DateFormat.format("dd-MM-yyyy", calendar).toString();
            date.setText(dateString);
        }

        @Override
        public void onClick(View v) {
            complaintsClickListener.OnComplaintsItemClicked(getAdapterPosition());
        }
    }

    public interface ComplaintsClickListener {
        void OnComplaintsItemClicked(int position);
    }
}

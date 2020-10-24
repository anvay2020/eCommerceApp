/**
 * coded by Pawan Singh Harariya
 */
package com.anvay.pawan.wholeseller.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.anvay.pawan.wholeseller.R;
import com.anvay.pawan.wholeseller.models.Message;
import com.anvay.pawan.wholeseller.utils.Constants;

import java.util.ArrayList;

public class ComplaintsMessageAdapter extends RecyclerView.Adapter<ComplaintsMessageAdapter.ComplaintMessageViewHolder> {
    private Context context;
    private ArrayList<Message> messages;
    private boolean isRetailerComplaint;

    public ComplaintsMessageAdapter(Context context, ArrayList<Message> messages, boolean isRetailerComplaint) {
        this.context = context;
        this.isRetailerComplaint = isRetailerComplaint;
        this.messages = messages;
    }

    @NonNull
    @Override
    public ComplaintMessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_item_complaints_message, parent, false);
        return new ComplaintMessageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ComplaintMessageViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return messages == null ? 0 : messages.size();
    }

    public class ComplaintMessageViewHolder extends RecyclerView.ViewHolder {
        private TextView sender, messageText;

        public ComplaintMessageViewHolder(@NonNull View itemView) {
            super(itemView);
            sender = itemView.findViewById(R.id.sender);
            messageText = itemView.findViewById(R.id.message_text);
        }

        public void bind(int position) {
            Message message = messages.get(position);
            messageText.setText(message.getText());
            if (message.getSender() == Constants.SENDER_SELLER)
                sender.setText("You");
            else {
                if (isRetailerComplaint)
                    sender.setText("Retailer");
                else
                    sender.setText("Admin");
            }
        }
    }
}

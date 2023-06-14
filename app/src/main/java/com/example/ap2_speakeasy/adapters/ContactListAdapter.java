package com.example.ap2_speakeasy.adapters;


import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ap2_speakeasy.ContactClickListener;
import com.example.ap2_speakeasy.R;
import com.example.ap2_speakeasy.entities.Contact;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

public class ContactListAdapter extends RecyclerView.Adapter<ContactListAdapter.ContactViewHolder> {

    class ContactViewHolder extends RecyclerView.ViewHolder {

        private final ImageView profilePic;
        private final TextView displayName;
        private final TextView lastMsg;
        private final TextView timeLastMsg;

        private ContactViewHolder(View itemView) {
            super(itemView);
            profilePic = itemView.findViewById(R.id.profile_image_user_view);
            displayName = itemView.findViewById(R.id.user_name_user_view);
            lastMsg = itemView.findViewById(R.id.last_massage_user_view);
            timeLastMsg = itemView.findViewById(R.id.time_user_view);
        }
    }

    private final LayoutInflater mInflater;
    private List<Contact> contacts;
    private ContactClickListener mContactClickListener;

    public ContactListAdapter(Context ctx) {
        mInflater = LayoutInflater.from(ctx);
        //mContactClickListener = listener;
    }

    @NonNull
    @Override
    public ContactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mView = mInflater.inflate(R.layout.user_list_item, parent, false);
        return new ContactViewHolder(mView);
    }


    @Override
    public void onBindViewHolder(@NonNull ContactViewHolder holder, int position) {
        if (contacts != null) {
            Contact current = contacts.get(position);
            holder.displayName.setText(current.getDisplayName());

            if (current.getLastMassage() != null) {
                String lastMessage = current.getLastMassage();
                if (lastMessage.length() > 15) {
                    lastMessage = lastMessage.substring(0, 15) + "...";
                }
                holder.lastMsg.setText(lastMessage);
            } else {
                holder.lastMsg.setText("");
            }

            if (current.getLastMassageSendingTime() != null && android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                try {
                    // Parse the string from format "MM/dd/yyyy, HH:mm:ss" to "h:mm a"
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy, HH:mm:ss", Locale.US);

                    LocalDateTime dateTime = LocalDateTime.parse(current.getLastMassageSendingTime(), formatter);
                    holder.timeLastMsg.setText(dateTime.format(DateTimeFormatter.ofPattern("h:mm a", Locale.US)));
                } catch (Exception e) {
                    holder.timeLastMsg.setText("");
                }
            } else {
                holder.timeLastMsg.setText("");
            }
            holder.profilePic.setImageResource(current.getPicture());

            // On click listener for the contact
            holder.itemView.setOnClickListener(v -> {
                // Invoke the listener passed in the constructor
                if (mContactClickListener != null) {
                    mContactClickListener.onContactClick(v, current);
                }
            });
        }
    }
    @SuppressLint("NotifyDataSetChanged")
    public void setContacts(List<Contact> contactsList) {
        contacts = contactsList;
        notifyDataSetChanged();
    }

    public int getItemCount() {
        if (contacts != null) {
            return contacts.size();
        }
        return 0;
    }

    public List<Contact> getContacts() {
        return contacts;
    }
}
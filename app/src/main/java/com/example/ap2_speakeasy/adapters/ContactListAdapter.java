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
import android.widget.ArrayAdapter;

import androidx.annotation.Nullable;
import java.util.ArrayList;

public class ContactListAdapter extends ArrayAdapter<Contact> {

    LayoutInflater inflater;


    public ContactListAdapter(Context context, ArrayList<Contact> contactList) {
        super(context, 0, contactList);
        this.inflater = LayoutInflater.from(context);

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        Contact contact = getItem(position);

        convertView = inflater.inflate(R.layout.user_list_item, parent, false);

        ImageView imageView = convertView.findViewById(R.id.profile_image_user_view);
        TextView displayName = convertView.findViewById(R.id.user_display_name);
        TextView lastMsg = convertView.findViewById(R.id.last_massage_user_view);
        TextView time = convertView.findViewById(R.id.time_user_view);

        imageView.setImageResource(R.drawable.profilepic);
        displayName.setText(contact.getUser().getDisplayName());
        String lm = "";
        String created = "";
        if (contact.getLastMessage() != null) {
            if (contact.getLastMessage().getContent() != null)
                lm = contact.getLastMessage().getContent();
            if (contact.getLastMessage().getCreated() != null)
                created = contact.getLastMessage().getCreated();
        }
        lastMsg.setText(lm);
        time.setText(created);

        return convertView;
    }

    public void setContacts(List<Contact> contacts) {
        this.clear();
        if (contacts != null) {
            addAll(contacts);
        }
        notifyDataSetChanged();
    }
}
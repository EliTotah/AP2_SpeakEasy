package com.example.ap2_speakeasy.adapters;


import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.annotation.NonNull;
import com.example.ap2_speakeasy.R;
import com.example.ap2_speakeasy.entities.Contact;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import android.widget.ArrayAdapter;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.Locale;

public class ContactListAdapter extends ArrayAdapter<Contact> {

    LayoutInflater inflater;
    private boolean isNightMode = false;


    public ContactListAdapter(Context context, ArrayList<Contact> contactList, boolean isNightMode) {
        super(context, 0, contactList);
        this.inflater = LayoutInflater.from(context);
        this.isNightMode = isNightMode;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        Contact contact = getItem(position);

        convertView = inflater.inflate(R.layout.user_list_item, parent, false);

        ImageView imageView = convertView.findViewById(R.id.profile_image_user_view);
        TextView displayName = convertView.findViewById(R.id.display_name_user_view);
        TextView lastMsg = convertView.findViewById(R.id.last_massage_user_view);
        TextView time = convertView.findViewById(R.id.time_user_view);

        // Customize the colors based on night mode
        int textColor = isNightMode ? R.color.night_mode_text_color : R.color.day_mode_text_color;
        int backgroundColor = isNightMode ? R.color.night_contact_color : R.color.day_mode_background_color;

        // Apply the colors to the views
        displayName.setTextColor(ContextCompat.getColor(getContext(), textColor));
        lastMsg.setTextColor(ContextCompat.getColor(getContext(), textColor));
        time.setTextColor(ContextCompat.getColor(getContext(), textColor));
        convertView.setBackgroundColor(ContextCompat.getColor(getContext(), backgroundColor));

        if (contact.getUser() != null) {
            Bitmap im = decodeImage(contact.getUser().getProfilePic());
            imageView.setImageBitmap(im);
            displayName.setText(contact.getUser().getDisplayName());
            String lm = "";
            String created = "";
            if (contact.getLastMessage() != null) {
                if (contact.getLastMessage().getContent() != null)
                    lm = contact.getLastMessage().getContent();
                if (contact.getLastMessage().getCreated() != null)
                    created = contact.getLastMessage().getCreated();
            }
            SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US);
            SimpleDateFormat outputFormat = new SimpleDateFormat("HH:mm dd/MM/yyyy", Locale.US);
            String formattedTime = "";

                // Parse the date string to a Date object
            Date date = new Date();
            try {
                if(!(created.equals(""))) {
                    date = inputFormat.parse(created);
                }
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
            // Format the Date object to the desired output format
            formattedTime = outputFormat.format(date);

            lastMsg.setText(lm);
            time.setText(formattedTime);
        }
        return convertView;
    }

    private Bitmap decodeImage(String imageString) {
        try {
            byte[] imageBytes = Base64.decode(imageString, Base64.DEFAULT);
            return BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;}

    public void setContacts(List<Contact> contacts) {
        this.clear();
        if (contacts != null) {
            addAll(contacts);
        }
        notifyDataSetChanged();
    }
    public void setNightMode(boolean isNightMode) {
        this.isNightMode = isNightMode;
        notifyDataSetChanged();
    }
}
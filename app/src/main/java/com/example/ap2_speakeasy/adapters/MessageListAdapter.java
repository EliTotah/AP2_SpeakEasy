package com.example.ap2_speakeasy.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.ap2_speakeasy.R;
import com.example.ap2_speakeasy.entities.Contact;
import com.example.ap2_speakeasy.entities.Message;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class MessageListAdapter extends ArrayAdapter<Message> {
    LayoutInflater inflater;
    String ActiveUser;
    public MessageListAdapter(Context ctx, ArrayList<Message> messagesArrayList, String activeUser) {
        super(ctx, 0, messagesArrayList);
        this.inflater = LayoutInflater.from(ctx);
        this.ActiveUser=activeUser;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        Message message = getItem(position);

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.message_list_item, parent, false);
        }

        LinearLayout zone = convertView.findViewById(R.id.Message_zone);
        TextView content = convertView.findViewById(R.id.Message_content);
        TextView time = convertView.findViewById(R.id.Time_sent);
        if (message != null) {
            if ((Objects.equals(message.getSender().get("username"), ActiveUser))) {
                zone.setBackgroundResource(R.color.my_message_background);
            } else {
                content.setBackgroundResource(R.color.friend_message_background);
            }
            SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US);
            SimpleDateFormat outputFormat = new SimpleDateFormat("HH:mm dd/MM/yyyy", Locale.US);
            String formattedTime = "";

            // Parse the date string to a Date object
            Date date = new Date();
            try {
                if(!(message.getCreated().equals(""))) {
                    date = inputFormat.parse(message.getCreated());
                }
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
            // Format the Date object to the desired output format
            formattedTime = outputFormat.format(date);
            content.setText(message.getContent());
            time.setText(formattedTime);
        }

        return convertView;
    }

    public void setMessages(List<Message> messages) {
        this.clear();
        if (messages != null) {
            addAll(messages);
        }
        notifyDataSetChanged();
    }
}

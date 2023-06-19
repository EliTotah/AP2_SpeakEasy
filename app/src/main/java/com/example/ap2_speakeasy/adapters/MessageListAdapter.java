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

import java.util.ArrayList;
import java.util.List;
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

        if ((Objects.equals(message.getSender(), ActiveUser))) {
            zone.setBackgroundResource(R.color.my_message_background);
        }
        else {
            content.setBackgroundResource(R.color.friend_message_background);
        }

        content.setText(message.getContent());
        time.setText(message.getCreated());

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

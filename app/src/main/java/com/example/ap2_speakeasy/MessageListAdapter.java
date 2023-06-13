package com.example.ap2_speakeasy;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class MessageListAdapter extends ArrayAdapter<Message> {
    LayoutInflater inflater;
    public MessageListAdapter(Context ctx, List<Message> messagesArrayList) {
        super(ctx, R.layout.message_list_item, messagesArrayList);
        this.inflater = LayoutInflater.from(ctx);
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

        if (!(message.isSent())) {
            zone.setBackgroundResource(R.color.my_message_background);
        }
        else {
            content.setBackgroundResource(R.color.friend_message_background);
        }

        content.setText(message.getContent());
        time.setText(message.getCreated());

        return convertView;
    }
}

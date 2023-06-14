package com.example.ap2_speakeasy.adapters;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.ap2_speakeasy.R;
import com.example.ap2_speakeasy.entities.User;

import java.util.List;

public class UserListAdapter extends ArrayAdapter<User> {
    LayoutInflater inflater;

    public UserListAdapter(Context ctx, List<User> userArrayList) {
        super(ctx, R.layout.user_list_item, userArrayList);
        this.inflater = LayoutInflater.from(ctx);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        User user = getItem(position);

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.user_list_item, parent, false);
        }

        ImageView imageView = convertView.findViewById(R.id.profile_image);
        TextView userName = convertView.findViewById(R.id.user_name);
        TextView lastMsg = convertView.findViewById(R.id.last_massage);
        TextView time = convertView.findViewById(R.id.time);

        imageView.setImageResource(user.getPicture());
        userName.setText(user.getUserName());
        lastMsg.setText(user.getLastMassage());
        time.setText(user.getLastMassageSendingTime());

        return convertView;
    }
}
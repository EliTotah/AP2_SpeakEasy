package com.example.ap2_speakeasy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class ChatContactsActivity extends AppCompatActivity {

    final private int[] profilePictures = {
            R.drawable.kroos, R.drawable.neuer, R.drawable.ramos,
            R.drawable.modric, R.drawable.muller,
            R.drawable.mbappe, R.drawable.neymar
    };

    final private String[] userNames = {
            "Toni Kroos", "Manuel Neuer", "Sergio Ramos", "Luka Modrić ", "Thomas Müller",
            "Kylian Mbappe", "Neymar Jr"
    };

    final private String[] lastMassages = {
            "Hi, how are you?", "24K Magic", "Missing Madrid :(", "Wanna hear a joke?", "Yo!",
            "Well....", "Did you see the latest John Wick?"
    };

    final private String[] times = {
            "12:00", "00:30", "03:23", "08:59", "12:23", "22:54", "11:47"
    };

    ListView listView;
    CustomListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_contacts);

        ArrayList<User> users = new ArrayList<>();

        for (int i = 0; i < profilePictures.length; i++) {
            User aUser = new User(
                    userNames[i], profilePictures[i],
                    lastMassages[i], times[i]
            );
            users.add(aUser);
        }

        listView = findViewById(R.id.list_view);
        adapter = new CustomListAdapter(getApplicationContext(), users);
        listView.setAdapter(adapter);
        listView.setClickable(true);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getApplicationContext(), ChatWindowActivity.class);

                intent.putExtra("userName", userNames[i]);
                intent.putExtra("profilePicture", profilePictures[i]);
                intent.putExtra("lastMassage", lastMassages[i]);
                intent.putExtra("time", times[i]);

                startActivity(intent);
            }
        });
    }
}
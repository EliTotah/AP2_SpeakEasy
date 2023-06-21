package com.example.ap2_speakeasy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.ap2_speakeasy.API.MessageAPI;
import com.example.ap2_speakeasy.Dao.AppDB;
import com.example.ap2_speakeasy.Dao.ContactDao;
import com.example.ap2_speakeasy.Dao.MessageDao;
import com.example.ap2_speakeasy.ViewModels.ContactViewModel;
import com.example.ap2_speakeasy.ViewModels.MessageViewModel;
import com.example.ap2_speakeasy.adapters.ContactListAdapter;
import com.example.ap2_speakeasy.adapters.MessageListAdapter;
import com.example.ap2_speakeasy.databinding.ActivityChatContactsBinding;
import com.example.ap2_speakeasy.databinding.ActivityChatWindowBinding;
import com.example.ap2_speakeasy.entities.Contact;
import com.example.ap2_speakeasy.entities.Message;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ChatWindowActivity extends AppCompatActivity implements SharedPreferences.OnSharedPreferenceChangeListener{

    int chatID;
    String userToken;
    String displayName;
    String friendPic;
    private String activeUserName;
    private ActivityChatWindowBinding binding;
    private MessageViewModel viewModel;
    private boolean isNightMode;
    MessageListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChatWindowBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        // Retrieve the initial value of the preference and set the theme
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        isNightMode = sharedPreferences.getBoolean("dark_mode", false);


        // Register the shared preference change listener
        sharedPreferences.registerOnSharedPreferenceChangeListener(this);

        Intent intent = getIntent();

        if (intent != null) {
            String idString = getIntent().getStringExtra("chatID");
            chatID = Integer.parseInt(idString);
            userToken = getIntent().getStringExtra("token");
            activeUserName = getIntent().getStringExtra("activeUserName");
            friendPic = getIntent().getStringExtra("friendPic");
            displayName = getIntent().getStringExtra("friendDisplayName");
        }

        binding.friendName.setText(displayName);
        binding.friendImage.setImageBitmap(decodeImage(friendPic));

        AppDB db = DatabaseManager.getDatabase();

        MessageDao messageDao = db.messageDao();
        this.viewModel = new MessageViewModel(userToken,chatID);
        List<Message> messages = new ArrayList<>();

        ListView lvMessages = binding.listViewMessages;
        adapter = new MessageListAdapter(getApplicationContext(), (ArrayList<Message>) messages, activeUserName,isNightMode);
        adapter.setNightMode(isNightMode);

        viewModel.getMessages().observe(this, adapter::setMessages);

        lvMessages.setAdapter(adapter);

        binding.sendIcon.setClickable(true);
        binding.sendIcon.setOnClickListener(v -> {
            handleSend();
        });

        binding.returnButton.setOnClickListener(view -> {
            finish();
        });

        MutableLiveData<Message> messageFirebase = SingeltonFireBase.getMessageFirebase();
        messageFirebase.observe(this,message -> {
            viewModel.addMessage(message);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void handleSend() {
        EditText ContentMessage = binding.messageInput;
        String content = ContentMessage.getText().toString().trim();
        if (!content.isEmpty()) {
            viewModel.insertMessage(content);
            ContentMessage.setText("");
        } else {
            Toast.makeText(ChatWindowActivity.this, "Please enter a message",
                    Toast.LENGTH_SHORT).show();
        }
    }

    private Bitmap decodeImage(String imageString) {
        try {
            byte[] imageBytes = Base64.decode(imageString, Base64.DEFAULT);
            return BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (key.equals("dark_mode")) {
            isNightMode = sharedPreferences.getBoolean(key, false);
        }
    }
}

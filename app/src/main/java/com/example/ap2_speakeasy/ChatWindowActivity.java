package com.example.ap2_speakeasy;

import androidx.appcompat.app.AppCompatActivity;
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

public class ChatWindowActivity extends AppCompatActivity {

    int chatID;
    String userToken;
    String displayName;
    String friendPic;
    private String activeUserName;
    private ActivityChatWindowBinding binding;
    private AppDB db;
    private List<Message> messages;
    private List<Message> dbMessages;
    private MessageDao messageDao;
    private ListView lvMessages;
    private MessageListAdapter adapter;
    private MessageViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChatWindowBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

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

        this.db = DatabaseManager.getDatabase();

        this.messageDao = db.messageDao();
        this.viewModel = new MessageViewModel(userToken,chatID);
        this.messages = new ArrayList<>();

        ListView lvMessages = binding.listViewMessages;
        adapter = new MessageListAdapter(getApplicationContext(), (ArrayList<Message>) this.messages,activeUserName);

        viewModel.getMessages().observe(this, adapter::setMessages);

        lvMessages.setAdapter(adapter);

        binding.sendIcon.setClickable(true);
        binding.sendIcon.setOnClickListener(v -> {
            handleSend();
        });

        binding.returnButton.setOnClickListener(view -> {
            finish();
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        //loadMessages();
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
}

/*
private void handleMessages() {
        messages = new ArrayList<>();
        adapter = new MessageListAdapter(getApplicationContext(), messages);
        lvMessages = binding.listViewMessages;

        loadMessages();

        lvMessages.setAdapter(adapter);
        lvMessages.setClickable(true);

        lvMessages.setOnItemLongClickListener((adapterView, view, i, l) -> {
            messages.remove(i);
            Message message = dbMessages.remove(i);
            messageDao.delete(message);
            adapter.notifyDataSetChanged();
            return true;
        });
    }

    private void loadMessages() {
        messages.clear();
        dbMessages = messageDao.getMessages();
        for (Message message : dbMessages) {
            Message aMessage = new Message(
                    message.getContent(), message.getCreated(),
                    message.isSent(), message.getContactId()
            );
            messages.add(aMessage);
        }
        adapter.notifyDataSetChanged();
    }
 */

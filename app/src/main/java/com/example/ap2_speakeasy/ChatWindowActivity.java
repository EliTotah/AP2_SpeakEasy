package com.example.ap2_speakeasy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ap2_speakeasy.databinding.ActivityChatWindowBinding;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.EnumSet;
import java.util.List;

public class ChatWindowActivity extends AppCompatActivity {

    private ActivityChatWindowBinding binding;
    private AppDB db;
    private List<Message> messages;
    private List<Message> dbMessages;
    private MessageDao messageDao;
    private ListView lvMessages;
    private MessageListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChatWindowBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        db = DatabaseManager.getDatabase(getApplicationContext());

        messageDao = db.messageDao();

        handleMessages();

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
        loadMessages();
    }


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

    private void handleSend() {
        EditText ContentMessage = binding.messageInput;
        String content = ContentMessage.getText().toString().trim();
        if (!content.isEmpty()) {
            Calendar calendar = Calendar.getInstance();
            int currentHour = calendar.get(Calendar.HOUR_OF_DAY);
            int currentMinute = calendar.get(Calendar.MINUTE);

            // Combine hour and minute into a single variable
            String currentTime = String.format("%02d:%02d", currentHour, currentMinute);
            boolean sent = false;
            Message message = new Message(content, currentTime, sent, 0);
            messageDao.insert(message);
            loadMessages();
        } else {
            Toast.makeText(ChatWindowActivity.this, "Please enter a message",
                    Toast.LENGTH_SHORT).show();
        }
    }
}

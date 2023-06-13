package com.example.ap2_speakeasy;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.ap2_speakeasy.databinding.ActivityChatContactsBinding;

import java.util.ArrayList;
import java.util.List;

public class ChatContactsActivity extends AppCompatActivity {
    private ActivityChatContactsBinding binding;
    private AppDB db;
    private List<User> users;
    private List<User> dbUsers;
    private UserDao userDao;
    private ListView lvUsers;
    private UserListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityChatContactsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        db = DatabaseManager.getDatabase(getApplicationContext());

        userDao = db.userDao();
        handlePosts();

        binding.addContactButton.setOnClickListener(view -> showAddContactDialog());

        binding.logoutButton.setOnClickListener(view -> {
            finish();
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadPosts();
    }


    private void handlePosts() {
        users = new ArrayList<>();
        adapter = new UserListAdapter(getApplicationContext(), users);
        lvUsers = binding.listViewChats;

        loadPosts();

        lvUsers.setAdapter(adapter);
        lvUsers.setClickable(true);

        lvUsers.setOnItemClickListener((adapterView, view, i, l) -> {
            Intent intent = new Intent(getApplicationContext(), ChatWindowActivity.class);
            intent.putExtra("userName", dbUsers.get(i).getUserName());
            intent.putExtra("profilePicture", R.drawable.profilepic);
            intent.putExtra("lastMassage", dbUsers.get(i).getLastMassage());
            intent.putExtra("time", dbUsers.get(i).getLastMassageSendingTime());
            startActivity(intent);
        });

        lvUsers.setOnItemLongClickListener((adapterView, view, i, l) -> {
            users.remove(i);
            User post = dbUsers.remove(i);
            userDao.delete(post);
            adapter.notifyDataSetChanged();
            return true;
        });
    }

    private void loadPosts() {
        users.clear();
        dbUsers = userDao.index();
        for (User user : dbUsers) {
            User aUser = new User(
                    user.getUserName(), 0,
                    user.getLastMassage(), user.getLastMassageSendingTime()
            );
            users.add(aUser);
        }
        adapter.notifyDataSetChanged();
    }

    private void showAddContactDialog() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.activity_add_contact, null);
        dialogBuilder.setView(dialogView);

        EditText usernameEditText = dialogView.findViewById(R.id.usernameEditText);
        dialogBuilder.setPositiveButton("OK", (dialogInterface, i) -> {

            String username = usernameEditText.getText().toString().trim();
            if (!username.isEmpty()) {
                User user = new User(username,0,"","");
                userDao.insert(user);
                loadPosts();
            }
            else {
                Toast.makeText(ChatContactsActivity.this, "Please enter a username",
                        Toast.LENGTH_SHORT).show();
            }
        });

        dialogBuilder.setNegativeButton("Cancel", null);
        AlertDialog dialog = dialogBuilder.create();
        dialog.show();
    }

}


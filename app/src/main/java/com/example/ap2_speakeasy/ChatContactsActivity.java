package com.example.ap2_speakeasy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ap2_speakeasy.API.ChatAPI;
import com.example.ap2_speakeasy.API.MessageAPI;
import com.example.ap2_speakeasy.databinding.ActivityChatContactsBinding;

import java.util.ArrayList;
import java.util.List;

public class ChatContactsActivity extends AppCompatActivity {
    private ActivityChatContactsBinding binding;
    private AppDB db;
    private List<Contact> contacts;
    private List<Contact> dbContacts;
    private ContactDao contactDao;
    private RecyclerView lvUsers;
    //private ContactListAdapter adapter;

    private ContactViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityChatContactsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        viewModel = new ViewModelProvider(this).get(ContactViewModel.class);

        RecyclerView lvContacts = binding.listViewChats;
        final ContactListAdapter adapter = new ContactListAdapter(this);
        lvContacts.setAdapter(adapter);
        lvContacts.setLayoutManager(new LinearLayoutManager(this));

        viewModel.getContacts().observe(this, adapter::setContacts);

        binding.addContactButton.setOnClickListener(view -> showAddContactDialog());

        binding.logoutButton.setOnClickListener(view -> {
            finish();
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        //loadPosts();
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
                Contact contact = new Contact(username, 0, "", "");
                contactDao.insert(contact);
                //loadPosts();
            } else {
                Toast.makeText(ChatContactsActivity.this, "Please enter a username",
                        Toast.LENGTH_SHORT).show();
            }
        });

        dialogBuilder.setNegativeButton("Cancel", null);
        AlertDialog dialog = dialogBuilder.create();
        dialog.show();
    }
}

/*
    private void handlePosts() {
        contacts = new ArrayList<>();
        adapter = new ContactListAdapter(getApplicationContext(), contacts);
        lvUsers = binding.listViewChats;

        loadPosts();

        lvUsers.setAdapter(adapter);
        lvUsers.setClickable(true);

        lvUsers.setOnItemClickListener((adapterView, view, i, l) -> {
            Intent intent = new Intent(getApplicationContext(), ChatWindowActivity.class);
            intent.putExtra("userName", dbContacts.get(i).getDisplayName());
            intent.putExtra("profilePicture", R.drawable.profilepic);
            intent.putExtra("lastMassage", dbContacts.get(i).getLastMassage());
            intent.putExtra("time", dbContacts.get(i).getLastMassageSendingTime());
            startActivity(intent);
        });

        lvUsers.setOnItemLongClickListener((adapterView, view, i, l) -> {
            contacts.remove(i);
            Contact post = dbContacts.remove(i);
            contactDao.delete(post);
            adapter.notifyDataSetChanged();
            return true;
        });
    }

    private void loadPosts() {
        contacts.clear();
        dbContacts = contactDao.index();
        for (Contact contact : dbContacts) {
            Contact aContact = new Contact(
                    contact.getDisplayName(), 0,
                    contact.getLastMassage(), contact.getLastMassageSendingTime()
            );
            contacts.add(aContact);
        }
        adapter.notifyDataSetChanged();
    }
*/



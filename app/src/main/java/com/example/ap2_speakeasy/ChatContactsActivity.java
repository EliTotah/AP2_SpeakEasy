package com.example.ap2_speakeasy;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import com.example.ap2_speakeasy.API.CallBackFlag;
import com.example.ap2_speakeasy.API.UserAPI;
import com.example.ap2_speakeasy.Dao.AppDB;
import com.example.ap2_speakeasy.Dao.ContactDao;
import com.example.ap2_speakeasy.ViewModels.ContactViewModel;
import com.example.ap2_speakeasy.adapters.ContactListAdapter;

import com.example.ap2_speakeasy.databinding.ActivityChatContactsBinding;
import com.example.ap2_speakeasy.entities.Contact;
import com.example.ap2_speakeasy.entities.User;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import okhttp3.Request;
import okio.Timeout;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChatContactsActivity extends AppCompatActivity {
    private ActivityChatContactsBinding binding;
    String activeUserName;
    String userToken;
    private AppDB db;
    private List<Contact> contacts;
    private List<Contact> dbContacts;
    private ContactDao contactDao;
    private RecyclerView lvUsers;
    private ContactListAdapter adapter;

    private ContactViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityChatContactsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Intent intent = getIntent();

        if (intent != null) {
             activeUserName = getIntent().getStringExtra("activeUserName");
             userToken = getIntent().getStringExtra("token");
        }

        UserAPI userAPI = new UserAPI();
        userAPI.getUserDetails(activeUserName, userToken, callback -> {
            if(callback == 200) {
                User u = userAPI.getUser();
                if (u!=null) {
                    binding.userDisplayName.setText(u.getDisplayName());
                    binding.userImage.setImageBitmap(decodeImage(u.getProfilePic()));
                }
                else {
                    //error
                }
            }
            else {
                //error
            }
        });

        this.db = DatabaseManager.getDatabase();

        this.contactDao = db.contactDao();
        this.viewModel = new ContactViewModel(userToken);
        this.contacts = new ArrayList<>();

        ListView lvContacts = binding.listViewChats;
        adapter = new ContactListAdapter(getApplicationContext(), (ArrayList<Contact>) this.contacts);

        viewModel.getContacts().observe(this, adapter::setContacts);

        lvContacts.setAdapter(adapter);
        lvContacts.setClickable(true);

        lvContacts.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Contact clicked = contacts.get(position);
                Intent intent2 = new Intent(ChatContactsActivity.this, ChatWindowActivity.class);
                intent2.putExtra("token", userToken);
                intent2.putExtra("friendDisplayName", clicked.getUser().getDisplayName());
                intent2.putExtra("friendPic", clicked.getUser().getProfilePic());
                intent2.putExtra("chatID", String.valueOf(clicked.getId()));
                intent2.putExtra("activeUserName", activeUserName);
                startActivity(intent2);
            }
        });

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
                viewModel.insertContact(usernameEditText.getText().toString());
            } else {
                Toast.makeText(ChatContactsActivity.this, "Please enter a username",
                        Toast.LENGTH_SHORT).show();
            }
        });

        dialogBuilder.setNegativeButton("Cancel", null);
        AlertDialog dialog = dialogBuilder.create();
        dialog.show();
        adapter.notifyDataSetChanged();
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



package com.example.ap2_speakeasy.repositories;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;


import com.example.ap2_speakeasy.API.ChatAPI;
import com.example.ap2_speakeasy.Dao.AppDB;
import com.example.ap2_speakeasy.Dao.ContactDao;
import com.example.ap2_speakeasy.DatabaseManager;
import com.example.ap2_speakeasy.entities.Contact;
import java.util.List;

public class ContactRepository {

    private ContactDao contactDao;
    private ContactListData contactListData;
    private ChatAPI chatAPI;
    private String token;
    private AppDB db;

    public ContactRepository(String token) {
        this.token = token;
        this.db = DatabaseManager.getDatabase();
        this.contactDao = db.contactDao();
        this.contactListData = new ContactListData();
        this.chatAPI = new ChatAPI();
    }

    public LiveData<List<Contact>> getAll() {
        reload();
        return contactListData;
    }

    public void insertContact(String username) {
        chatAPI.createChat(token,username,contactListData);
    }

    public  void addContact(Contact c) {
        contactDao.insert(c);
    }

    public void reload() {
        ChatAPI chatAPI = new ChatAPI();
        chatAPI.getAllChats(contactListData,token);
        contactDao.deleteContacts();
        List<Contact> contactsList = contactListData.getValue();
        if (contactsList != null) {
            for (Contact c : contactsList) {
                addContact(c);
            }
        }
        contactListData.postValue(contactDao.index());
    }


    class ContactListData extends MutableLiveData<List<Contact>> {
        public ContactListData() {
            super();
           setValue(contactDao.index());

        }

        @Override
        protected void onActive() {
            super.onActive();
            new Thread(() -> {
                chatAPI.getAllChats(this,token);
            }).start();
        }
    }
}

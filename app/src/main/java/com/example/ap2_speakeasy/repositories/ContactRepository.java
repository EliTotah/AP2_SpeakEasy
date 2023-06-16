package com.example.ap2_speakeasy.repositories;

import androidx.lifecycle.MutableLiveData;

import com.example.ap2_speakeasy.AP2_SpeakEasy;
import com.example.ap2_speakeasy.Dao.AppDB;
import com.example.ap2_speakeasy.Dao.ContactDao;
import com.example.ap2_speakeasy.DatabaseManager;
import com.example.ap2_speakeasy.LoginActivity;
import com.example.ap2_speakeasy.MainActivity;
import com.example.ap2_speakeasy.entities.Contact;

import java.util.LinkedList;
import java.util.List;

public class ContactRepository {

    private ContactDao contactDao;
    private  ContactListData contactListData;
    //private final MessageListData messageListData;

    public ContactRepository() {
        AppDB db = DatabaseManager.getDatabase(AP2_SpeakEasy.context);
        contactDao = db.contactDao();
        contactListData = new ContactListData();
        //messageListData = new MessageListData();
    }

    public void insertContact(Contact contact) {
        contactDao.insert(contact);
        List<Contact> contactsList = contactListData.getValue();
        if (contactsList == null) {
            contactsList = new LinkedList<>();
        }
        contactsList.add(contact);
        contactListData.setValue(contactsList);
    }

    public void deleteContact(Contact contact) {
        contactDao.delete(contact);
        List<Contact> contactsList = contactListData.getValue();
        if (contactsList == null) {
            return;
        }
        contactsList.remove(contact);
        contactListData.setValue(contactsList);
    }

    // Get contact by id
    public Contact getContact(int id) {
        return contactDao.get(id);
    }

    // Get contacts list
    public MutableLiveData<List<Contact>> getContacts() {
        return contactListData;
    }

    public MutableLiveData<List<Contact>> reload() {
        return contactListData;
    }
    class ContactListData extends MutableLiveData<List<Contact>> {
        public ContactListData() {
            super();
            setValue(new LinkedList<>());
        }

        @Override
        protected void onActive() {
            super.onActive();

            new Thread(() -> {
                List<Contact> contacts = contactDao.index();
                contactListData.postValue(contacts);
            }).start();
        }
    }



}

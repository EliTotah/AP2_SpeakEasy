package com.example.ap2_speakeasy;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Room;

import java.util.LinkedList;
import java.util.List;

public class ContactRepository {

    private  ContactDao contactDao;
    private  ContactListData contactListData;
    //private final MessageListData messageListData;

    public ContactRepository() {
        //DatabaseManager db = DatabaseManager.getDatabase(AP2_SpeakEasy);
        //contactsDao = db.();
        ContactListData contactListData = new ContactListData();
        //messageListData = new MessageListData();
    }

    public void insertContact(Contact contact) {
        //contactsDao.insertContact(contact);
        List<Contact> contactsList = contactListData.getValue();
        if (contactsList == null) {
            contactsList = new LinkedList<>();
        }
        contactsList.add(contact);
        contactListData.setValue(contactsList);
    }

    public void deleteContacts() {
        //contactsDao.deleteContacts();
        contactListData.setValue(null);
    }

    // Get contact by id
    public Contact getContact(String id) {
        return  null;
        //return contactsDao.getContact(id);
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

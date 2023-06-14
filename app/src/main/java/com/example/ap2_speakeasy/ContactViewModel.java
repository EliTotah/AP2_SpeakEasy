package com.example.ap2_speakeasy;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

public class ContactViewModel extends ViewModel {
    private final ContactRepository contactsRepository;
    private final LiveData<List<Contact>> contacts;
    //private String contactId;


    public ContactViewModel() {
        contactsRepository = new ContactRepository();
        contacts = contactsRepository.getContacts();
        //contactId = null;
    }

    public LiveData<List<Contact>> getContacts() {
        return contacts;
    }

    // Get contact by id
    public Contact getContact(String id) {
        return contactsRepository.getContact(id);
    }

    public void insertContact(Contact contact) {
        contactsRepository.insertContact(contact);
    }

    public void reload() {
        contactsRepository.reload();
    }
}

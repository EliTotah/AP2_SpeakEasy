package com.example.ap2_speakeasy.ViewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.ap2_speakeasy.entities.Contact;
import com.example.ap2_speakeasy.repositories.ContactRepository;

import java.util.List;

public class ContactViewModel extends ViewModel {
    private ContactRepository contactsRepository;
    private LiveData<List<Contact>> contacts;


    public ContactViewModel(String token) {
        this.contactsRepository = new ContactRepository(token);
        this.contacts = contactsRepository.getAll();
    }

    public LiveData<List<Contact>> getContacts() {
        return this.contacts;
    }


    public void insertContact(String username) {
        contactsRepository.insertContact(username);
        reload();
    }

    public void reload() {
        this.contacts = contactsRepository.getAll();
    }
}

package com.example.ap2_speakeasy.ViewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.ap2_speakeasy.entities.Contact;
import com.example.ap2_speakeasy.entities.Message;
import com.example.ap2_speakeasy.repositories.ContactRepository;
import com.example.ap2_speakeasy.repositories.MessageRepository;

import java.util.List;

public class   MessageViewModel extends ViewModel {
    private MessageRepository messageRepository;
    private LiveData<List<Message>> messages;


    public MessageViewModel(String token, int chatID) {
        this.messageRepository = new MessageRepository(token, chatID);
        this.messages = messageRepository.getAll();
    }

    public LiveData<List<Message>> getMessages() {
        return this.messages;
    }

    public void insertMessage(String content) {
        messageRepository.insertMessage(content);
        reload();
    }

    public void reload() {
        this.messages = messageRepository.getAll();
    }
}

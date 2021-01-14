package com.epam.chat.servlets;

import com.epam.chat.datalayer.dto.Message;
import com.epam.chat.datalayer.dto.User;
import com.epam.chat.servicelayer.MessageService;
import com.epam.chat.servicelayer.MessageServiceImpl;
import com.epam.chat.servicelayer.UserService;
import com.epam.chat.servicelayer.UserServiceImpl;

import java.util.Collections;
import java.util.List;

public class ChatDataProvider {

    public static final int COUNT = 20;
    public UserService userService;
    public MessageService messageService;

    public ChatDataProvider(UserService userService, MessageService messageService) {
        this.userService = userService;
        this.messageService = messageService;
    }

    public List<Message> getMessageList() {
        List<Message> messageList = messageService.getLast(COUNT);
        Collections.reverse(messageList);
        return messageList;
    }

    public List<User> getUserListOnline() {
        return userService.getAllLogged();
    }

    public List<User> getUserListKickedAndOffline() {
        List<User> getUserListNotKicked = userService.getAllKicked();
        getUserListNotKicked.addAll(userService.getAllOffline());
        return getUserListNotKicked;
    }
}

package com.epam.chat;


import com.epam.chat.datalayer.dto.User;
import com.epam.chat.servicelayer.MessageServiceImpl;
import com.epam.chat.servicelayer.UserServiceImpl;

import java.util.List;

public class Application {


    public static void main(String[] args) {
        Application application = new Application();
        application.start();

    }

    private void start() {

        UserServiceImpl.getInstance().login("lanimagajy@epam.com");
        UserServiceImpl.getInstance().unkick("lanimagajy@epam.com", "lani");
        UserServiceImpl.getInstance().login("lani");
        UserServiceImpl.getInstance().kick("lanimagajy@epam.com", "lani");

        UserServiceImpl.getInstance().logout("lanimagajy@epam.com");
        List<User> userListLogged = UserServiceImpl.getInstance().getAllLogged();
        List<User> userListKicked = UserServiceImpl.getInstance().getAllKicked();
        System.out.println(userListLogged);
        System.out.println(userListKicked);
        System.out.println(UserServiceImpl.getInstance().isKicked("lani"));
        System.out.println(UserServiceImpl.getInstance().isLoggedIn("lani"));

        System.out.println(MessageServiceImpl.getInstance().getLast(3));
        MessageServiceImpl.getInstance().sendMessage("Yellowstone", 1);

//        UserServiceImpl.getInstance().login("lani");


    }
}

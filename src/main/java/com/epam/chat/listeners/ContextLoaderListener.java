package com.epam.chat.listeners;

import com.epam.chat.datalayer.db.connection.ConnectionPool;
import com.epam.chat.servicelayer.MessageServiceImpl;
import com.epam.chat.servicelayer.UserService;
import com.epam.chat.servicelayer.UserServiceImpl;
import com.epam.chat.servlets.ChatDataProvider;
import com.epam.chat.utils.ParameterNames;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class ContextLoaderListener implements ServletContextListener {

    private ConnectionPool connectionPool;

    public ContextLoaderListener() {
        connectionPool = ConnectionPool.getInstance();
    }

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        sce.getServletContext().setAttribute(ParameterNames.CHAT_DATA_PROVIDER,
                new ChatDataProvider(UserServiceImpl.getInstance(), MessageServiceImpl.getInstance()));
        sce.getServletContext().setAttribute(ParameterNames.USER_SERVICE, UserServiceImpl.getInstance());
        sce.getServletContext().setAttribute(ParameterNames.MESSAGE_SERVICE, MessageServiceImpl.getInstance());
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        connectionPool.closeConnection();
    }
}

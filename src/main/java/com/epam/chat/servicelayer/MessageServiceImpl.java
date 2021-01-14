package com.epam.chat.servicelayer;


import com.epam.chat.datalayer.DAOFactory;
import com.epam.chat.datalayer.DBType;
import com.epam.chat.datalayer.MessageDAO;
import com.epam.chat.datalayer.dto.Message;
import com.epam.chat.datalayer.dto.Status;
import com.epam.chat.datalayer.dto.User;
import com.epam.chat.exception.KickStatusException;
import com.epam.chat.utils.PropertyProvider;

import java.util.List;


public class MessageServiceImpl implements MessageService {
    public static final String DATASOURCE = "datasource";
    private MessageDAO messageDAO;
    private static MessageServiceImpl instance;


    private MessageServiceImpl() {
        String xmlType = PropertyProvider.get(DATASOURCE);
        messageDAO = DAOFactory.getInstance(DBType.getTypeByName(xmlType)).getMessageDAO();
    }

    public static MessageServiceImpl getInstance() {
        if (instance == null) {
            instance = new MessageServiceImpl();
        }
        return instance;
    }

    @Override
    public void sendMessage(String message, int userID) {
        User user = UserServiceImpl.getInstance().userDAO.getUserByUserID(userID);
        if (user != null) {
            if (!(user.getStatus().equals(Status.KICKED))) {
                messageDAO.sendMessage(message, userID);
            } else if (user.getStatus().equals(Status.KICKED)) {
                throw new KickStatusException("This user is blocked");
            }
        }
    }

    @Override
    public List<Message> getLast(int count) {
        return messageDAO.getLast(count);
    }

    public void sendSystemMessage(String message) {
        User systemUser = UserServiceImpl.getInstance().userDAO.getSystemUser();
        sendMessage(message, systemUser.getUserID());
    }

}

package com.epam.chat.datalayer.xml;

import com.epam.chat.datalayer.DAOFactory;
import com.epam.chat.datalayer.MessageDAO;
import com.epam.chat.datalayer.UserDAO;


public class XMLDAOFactory extends DAOFactory {

    public static final String SRC_MAIN_RESOURCES_XML_XSD_CHAT_USER_XSD =
            "src\\main\\resources\\xml_xsd\\chat_user.xsd";
    public static final String SRC_MAIN_RESOURCES_XML_XSD_CHAT_MESSAGE_XSD =
            "src\\main\\resources\\xml_xsd\\chat_message.xsd";
    public static final String SRC_MAIN_RESOURCES_XML_XSD_CHAT_USER_XML =
            "src\\main\\resources\\xml_xsd\\chat_user.xml";
    public static final String SRC_MAIN_RESOURCES_XML_XSD_CHAT_MESSAGE_XML =
            "src\\main\\resources\\xml_xsd\\chat_message.xml";

    @Override
    public MessageDAO getMessageDAO() {
        return new XMLMessageDAO();
    }

    @Override
    public UserDAO getUserDAO(){
        return new XMLUserDAO();
    }

}

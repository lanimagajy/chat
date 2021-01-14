package com.epam.chat.datalayer.xml;

import com.epam.chat.datalayer.MessageDAO;
import com.epam.chat.datalayer.dto.Message;
import com.epam.chat.datalayer.xml.parser.MessageParser;
import com.epam.chat.datalayer.xml.writer.MessageWriter;

import java.util.ArrayList;
import java.util.List;

public class XMLMessageDAO extends XMLDAOFactory implements MessageDAO {

    private static final int ONE = 1;
    private MessageParser messageParser;
    private MessageWriter writerMessage;


    public XMLMessageDAO() {
        messageParser = new MessageParser();
        messageParser.xmlDomParserMessage();
        writerMessage = new MessageWriter();
    }

    //place XMLDOMParser or XMLMessageParser here

    @Override
    /**
     * Get last messages from xml file using parser
     */
    public List<Message> getLast(int count) {
        List<Message> countMessage = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            countMessage.add(messageParser.parseMessage().get(messageParser.parseMessage().size() - i - ONE));
        }
        return countMessage;
    }


    /**
     * Send message using xml file and parse
     *
     * @param message - message to send
     */
    @Override
    public void sendMessage(String message, int userID) {
        writerMessage.writeMessage(message, userID);
    }


}

package com.epam.chat.datalayer.xml.parser;

import com.epam.chat.datalayer.dto.Message;
import com.epam.chat.datalayer.xml.XMLDAOFactory;
import com.epam.chat.datalayer.xml.XMLParser;
import com.epam.chat.utils.PropertyProvider;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class MessageParser {

    public static final int I = 0;
    public static final String MESSAGE = "message";
    public static final String USER_ID = "UserID";
    public static final String TIME_STAMP = "timeStamp";
    public static final String MESSAGE_TEG = "Message";
    public static final String ID = "ID";


    private String xmlPathMessage;
    private XMLParser xmlParser;
    private UserParser userParser;
    private Element root;
    public static final Logger log = Logger.getLogger(MessageParser.class);


    public MessageParser() {
        xmlPathMessage = XMLDAOFactory.SRC_MAIN_RESOURCES_XML_XSD_CHAT_MESSAGE_XML;
        xmlParser = new XMLParser();
    }

    public List<Message> xmlDomParserMessage() {
        Document document = xmlParser.getDocument(xmlPathMessage);
        userParser = new UserParser();
        this.root = document.getDocumentElement();
        return parseMessage();
    }


    public List<Message> parseMessage() {
        List<Message> messages = new ArrayList<>();
        NodeList nodeList = root.getElementsByTagName(MESSAGE_TEG);

        for (int i = I; i < nodeList.getLength(); i++) {
            Message message = buildElement((Element) nodeList.item(i));
            messages.add(message);
        }
        return messages;
    }

    private Message buildElement(Element element) {
        Message message = new Message();
        message.setId(Integer.parseInt(userParser.getStringValueElementForUser(element, ID)));
        message.setUserID(Integer.parseInt(userParser.getStringValueElementForUser(element, USER_ID)));
        message.setDate(parseDate(element, TIME_STAMP));
        message.setMessage(userParser.getStringValueElementForUser(element, MESSAGE));
        return message;
    }

    private Date parseDate(Element element, String timeStamp) {
        String patternDate = PropertyProvider.get("patternDate");
        NodeList nodeList = element.getChildNodes();
        Node node;
        Date date = null;
        for (int i = 0; i < nodeList.getLength(); i++) {
            node = nodeList.item(i);
            if (timeStamp.equals(node.getNodeName())) {
                try {
                    date = new SimpleDateFormat(patternDate).parse(node.getTextContent());
                } catch (ParseException e) {
                    log.error("The date format is entered incorrectly", e);
                }
            }
        }
        return date;
    }
}

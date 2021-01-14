package com.epam.chat.datalayer.xml.parser;


import com.epam.chat.datalayer.dto.User;
import com.epam.chat.datalayer.xml.XMLDAOFactory;
import com.epam.chat.datalayer.xml.XMLParser;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class UserParser {


    public static final String USER = "User";
    public static final String ID = "ID";
    public static final String NICK = "nick";
    public static final String TITLE = "title";
    private File xmlFileUser;
    private String xmlPathUser;
    private XMLParser xmlParser;
    private UserParser userParser;
    private Element root;
    private List<User> users;


    public UserParser() {
        xmlPathUser = XMLDAOFactory.SRC_MAIN_RESOURCES_XML_XSD_CHAT_USER_XML;
        xmlFileUser = new File(xmlPathUser);
        xmlParser = new XMLParser();
    }


    public List<User> xmlDomParserUser() {
        Document document = xmlParser.getDocument(xmlPathUser);
        userParser = new UserParser();
        this.root = document.getDocumentElement();
        return parseUser();
    }

    public List<User> parseUser() {
        users = new ArrayList<>();
        NodeList nodeList = root.getElementsByTagName(USER);

        for (int i = 0; i < nodeList.getLength(); i++) {
            User user = buildElementForUser((Element) nodeList.item(i));
            users.add(user);
        }
        return users;
    }


    public User buildElementForUser(Element element) {
        RoleParser roleParser = new RoleParser();
        StatusParser statusParser = new StatusParser();
        User user = new User();
        user.setUserID(Integer.parseInt(getStringValueElementForUser(element, ID)));
        user.setNick(getStringValueElementForUser(element, NICK));
        user.setRole(roleParser.getRoleValueElement(element, TITLE));
        user.setStatus(statusParser.getStatusValueElement(element, TITLE));
        return user;
    }

    public String getStringValueElementForUser(Element element, String s) {
        NodeList nodeList = element.getChildNodes();
        Node node = null;
        for (int i = 0; i < nodeList.getLength(); i++) {
            node = nodeList.item(i);
            if (s.equals(node.getNodeName())) {
                return node.getTextContent();
            }
        }
        assert node != null;
        return node.getTextContent();
    }

}

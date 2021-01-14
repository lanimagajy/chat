package com.epam.chat.datalayer.xml;

import com.epam.chat.datalayer.UserDAO;
import com.epam.chat.datalayer.dto.Role;
import com.epam.chat.datalayer.dto.Status;
import com.epam.chat.datalayer.dto.User;
import com.epam.chat.datalayer.xml.parser.UserParser;
import com.epam.chat.datalayer.xml.writer.UserWriter;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

public class XMLUserDAO extends XMLDAOFactory implements UserDAO {

    private static final org.apache.log4j.Logger log = Logger.getLogger(XMLUserDAO.class);
    public static final int ZERO = 0;
    public static final String TITLE = "title";
    public static final String STATUS = "Status";
    public static final String NICK = "nick";
    public static final String NO = "no";
    private String path;
    private File file;
    private UserWriter writerUser;


    public XMLUserDAO() {
        path = XMLDAOFactory.SRC_MAIN_RESOURCES_XML_XSD_CHAT_USER_XML;
        file = new File(path);
        writerUser = new UserWriter();
    }


    public void updateDataInXMLFile(String user, Status kick) {
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder;
        try {
            documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(file);
            document.getDocumentElement();

            NodeList nicks = document.getElementsByTagName(NICK);
            Element nick;

            for (int i = 0; i < nicks.getLength(); i++) {
                nick = (Element) nicks.item(i);
                if (nick.getTextContent().equals(user)) {
                    NodeList statuses = document.getElementsByTagName(STATUS);
                    Element status = (Element) statuses.item(i);
                    Node titleStatus = status.getElementsByTagName(TITLE).item(ZERO).getFirstChild();
                    titleStatus.setNodeValue(kick.name().toUpperCase());
                }
            }
            document.getDocumentElement().normalize();
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(document);
            StreamResult result = new StreamResult(file);

            transformer.setOutputProperty(OutputKeys.INDENT, NO);

            transformer.transform(source, result);

        } catch (ParserConfigurationException e) {
            log.error("Serious parser configuration error", e);
        } catch (SAXException e) {
            log.error("SAX processing error", e);
        } catch (IOException e) {
            log.error("Data input output error", e);
        } catch (TransformerConfigurationException e) {
            log.error("Serious transformer configuration error", e);
        } catch (TransformerException e) {
            log.error("Error of an exceptional state that occurred during the conversion process", e);
        }
    }

    @Override
    public boolean checkingStatus(String user, Status status) {
        UserParser userParser = new UserParser();
        List<User> userList = userParser.xmlDomParserUser();
        boolean isStatus = false;
        for (User value : userList) {
            if (value.getNick().equals(user)) {
                if (value.getStatus().equals(status)) {
                    isStatus = true;
                }
            }
        }
        return isStatus;
    }



    public List<User> getAllUsersByStatus(Status status) {
        List<User> userListStatus = new ArrayList<>();
        UserParser userParser = new UserParser();
        List<User> userList = userParser.xmlDomParserUser();
        for (User value : userList) {
            if (!(value.getRole().equals(Role.SYSTEM))) {
                if (value.getStatus().equals(status)) {
                    userListStatus.add(value);
                }
            }
        }
        return userListStatus;
    }


    /**
     * Get role from xml file using parser by user nick
     *
     * @param nick nick of user to find the role
     * @return user role
     */

    public Role getRole(String nick) {
        Role role = null;
        UserParser userParser = new UserParser();
        List<User> userList = userParser.xmlDomParserUser();
        for (User user : userList) {
            if (nick.equals(user.getNick())) {
                role = user.getRole();
            }
        }
        return role;
    }


    @Override
    public User getUserByNick(String nick) {
        User user = null;
        UserParser userParser = new UserParser();
        List<User> userList = userParser.xmlDomParserUser();
        for (User value : userList) {
            if (nick.equals(value.getNick())) {
                user = value;
            }
        }
        return user;
    }

    @Override
    public void createNewUser(String nick, Role role) {
        UserParser userParser = new UserParser();
        List<User> userList = userParser.xmlDomParserUser();
        int id = userList.size();
        User newUser = new User(id, nick, role, Status.OFFLINE);
        writerUser.writeUser(newUser);
    }

    @Override
    public void changeUserStatus(int userID, Status status) {
        UserParser userParser = new UserParser();
        List<User> userList = userParser.xmlDomParserUser();
        for (User user : userList) {
            if (userID == user.getUserID()) {
                updateDataInXMLFile(user.getNick(), status);
            }
        }
    }

    @Override
    public User getSystemUser() {
        UserParser userParser = new UserParser();
        List<User> userList = userParser.xmlDomParserUser();
        User userSystem = null;
        for (User user : userList) {
            if (user.getRole().equals(Role.SYSTEM)) {
                if (userSystem == null) {
                    userSystem = user;
                }
            }
        }
        return userSystem;
    }

    @Override
    public User getUserByUserID(int userID) {
        UserParser userParser = new UserParser();
        List<User> userList = userParser.xmlDomParserUser();
        User user = null;
        for (User value : userList) {
            if (value.getUserID() == userID) {
                user = value;
            }
        }
        return user;
    }
}

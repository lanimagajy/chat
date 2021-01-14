package com.epam.chat.datalayer.xml.writer;


import com.epam.chat.datalayer.dto.User;
import com.epam.chat.datalayer.xml.XMLDAOFactory;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.IOException;

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

public class UserWriter {

    public static final String USER = "User";
    public static final String ID = "ID";
    public static final String NICK = "nick";
    public static final String ROLE = "Role";
    public static final String TITLE = "title";
    public static final String DESCRIPTION = "description";
    public static final String STATUS = "Status";
    public static final String NO = "no";
    private File xmlFileUsers;
    private String xmlPathUser;
    private LanguageElementsProvider languageElements;
    private static final org.apache.log4j.Logger log = Logger.getLogger(UserWriter.class);

    public UserWriter(){
        xmlPathUser = XMLDAOFactory.SRC_MAIN_RESOURCES_XML_XSD_CHAT_USER_XML;
        xmlFileUsers = new File(xmlPathUser);
        languageElements = new LanguageElementsProvider();
    }

    public void writeUser(User user){

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;
        try {
            builder = factory.newDocumentBuilder();

            Document doc = builder.parse(xmlFileUsers);
            Node users = doc.getDocumentElement();


            Element userElement = doc.createElement(USER);
            users.appendChild(userElement);

            String userID = String.valueOf(user.getUserID());
            userElement.appendChild(languageElements.getLanguageElements(doc, ID, userID));

            String nick = user.getNick();
            userElement.appendChild(languageElements.getLanguageElements(doc, NICK, nick));

            Element roleElement = doc.createElement(ROLE);
            userElement.appendChild(roleElement);

            String role = user.getRole().name();
            roleElement.appendChild(languageElements.getLanguageElements(doc, TITLE, role));

            String roleDescription = user.getRole().getDescription();
            roleElement.appendChild(languageElements.getLanguageElements(doc, DESCRIPTION, roleDescription));

            Element statusElement = doc.createElement(STATUS);
            userElement.appendChild(statusElement);

            String status = user.getStatus().name();
            statusElement.appendChild(languageElements.getLanguageElements(doc, TITLE, status));

            doc.getDocumentElement().normalize();
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();

            transformer.setOutputProperty(OutputKeys.INDENT, NO);

            DOMSource source = new DOMSource(doc);

            StreamResult file = new StreamResult(xmlFileUsers);
            transformer.transform(source, file);

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
}

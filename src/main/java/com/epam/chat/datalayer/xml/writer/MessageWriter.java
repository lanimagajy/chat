package com.epam.chat.datalayer.xml.writer;


import com.epam.chat.datalayer.dto.Message;
import com.epam.chat.datalayer.xml.XMLDAOFactory;
import com.epam.chat.datalayer.xml.parser.MessageParser;
import com.epam.chat.utils.PropertyProvider;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
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

public class MessageWriter {

    public static final String USER_ID = "UserID";
    private static final org.apache.log4j.Logger log = Logger.getLogger(MessageWriter.class);
    public static final String NO = "no";
    public static final String MESSAGE = "message";
    public static final String TIME_STAMP = "timeStamp";
    public static final String MESSAGE_TEG = "Message";
    public static final String ID = "ID";
    public static final int ONE = 1;
    private File xmlFileMessages;
    private String xmlPathMessage;
    private LanguageElementsProvider languageElements;

    public MessageWriter() {
        xmlPathMessage = XMLDAOFactory.SRC_MAIN_RESOURCES_XML_XSD_CHAT_MESSAGE_XML;
        xmlFileMessages = new File(xmlPathMessage);
        languageElements = new LanguageElementsProvider();
    }


    public void writeMessage(String message, int userID) {

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;
        try {
            builder = factory.newDocumentBuilder();

            Document doc = builder.parse(xmlFileMessages);
            Node messages = doc.getDocumentElement();

            Element messageElement = doc.createElement(MESSAGE_TEG);
            messages.appendChild(messageElement);

            MessageParser messageParser = new MessageParser();
            List<Message> messageList = messageParser.xmlDomParserMessage();
            String id = String.valueOf(messageList.size() + ONE);
            messageElement.appendChild(languageElements.getLanguageElements(doc, ID, id));

            String userIDStr = String.valueOf(userID);
            messageElement.appendChild(languageElements.getLanguageElements(doc, USER_ID, userIDStr));

            String patternDate = PropertyProvider.get("patternDate");
            DateFormat dateFormat = new SimpleDateFormat(patternDate);
            Date date = new Date();

            String timeStamp = dateFormat.format(date);
            messageElement.appendChild(languageElements.getLanguageElements(doc, TIME_STAMP, timeStamp));

            String messageText = message;
            messageElement.appendChild(languageElements.getLanguageElements(doc, MESSAGE, messageText));

            doc.getDocumentElement().normalize();
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();

            transformer.setOutputProperty(OutputKeys.INDENT, NO);

            DOMSource source = new DOMSource(doc);

            StreamResult file = new StreamResult(xmlFileMessages);
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

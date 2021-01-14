package com.epam.chat.datalayer.xml;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class XMLParser {

    private static final org.apache.log4j.Logger log = Logger.getLogger(XMLParser.class);
    public static final String HTTP_XML_ORG_SAX_FEATURES_VALIDATION = "http://xml.org/sax/features/validation";

    public Document getDocument(String fileXML) {
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        documentBuilderFactory.setAttribute(HTTP_XML_ORG_SAX_FEATURES_VALIDATION, false);
        DocumentBuilder documentBuilder;
        Document document = null;
        try {
            documentBuilder = documentBuilderFactory.newDocumentBuilder();

            document = documentBuilder.parse(fileXML);
        } catch (SAXException e) {
            log.error("Resource null value error", e);
        } catch (IOException e) {
            log.error("Error: data input output", e);
        } catch (ParserConfigurationException e) {
            log.error("The parser configuration data is not correct", e);
        }
        return document;
    }
}

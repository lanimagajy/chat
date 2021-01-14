package com.epam.chat.datalayer.xml.validator;

import com.epam.chat.datalayer.xml.XMLUserDAO;

import org.apache.log4j.Logger;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.IOException;

import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

public class Validation {

    public static final String FILE = "File \"";
    public static final String IS_VALID = "\" is valid.";
    public static final String HTTP_WWW_W_3_ORG_2001_XMLSCHEMA = "http://www.w3.org/2001/XMLSchema";
    public static final int BEGIN_INDEX = 1;
    public static final org.apache.log4j.Logger log = Logger.getLogger(XMLUserDAO.class);
    public static final String STR = "\\";

    public void validator(String pathXSD, String pathXML) {
        SchemaFactory schemaFactory = SchemaFactory.newInstance(HTTP_WWW_W_3_ORG_2001_XMLSCHEMA);
        try {
            Schema schema = schemaFactory.newSchema(new File(pathXSD));
            Validator validator = schema.newValidator();
            validator.validate(new StreamSource(new File(pathXML)));
            log.info(FILE + pathXML.substring(pathXML.lastIndexOf(STR)).substring(BEGIN_INDEX) + IS_VALID);
        } catch (SAXException e) {
            log.error("Resource null value error", e);
        } catch (IOException e) {
            log.error("Error: data input output", e);
        }
    }
}

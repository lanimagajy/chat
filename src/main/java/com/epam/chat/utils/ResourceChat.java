package com.epam.chat.utils;

import com.epam.chat.exception.NullValueResourceException;

import org.apache.log4j.Logger;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class ResourceChat {

    public static final Logger log = Logger.getLogger(ResourceChat.class);

    public static String getResourceFile(String key, String fileName) {
        ResourceBundle resourceBundle = ResourceBundle.getBundle(fileName);
        try {
            return resourceBundle.getString(key);
        } catch (MissingResourceException e) {
            log.error("Resource null value error", e);
            throw new NullValueResourceException("Error: null value of the array resource", e);
        }
    }

}

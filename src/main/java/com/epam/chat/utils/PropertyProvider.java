package com.epam.chat.utils;


public class PropertyProvider {

    public static String get(String key) {
        return ResourceChat.getResourceFile(key, "elements_language");
    }
}




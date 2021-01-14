package com.epam.chat.utils;


public class ResourceConnection {

    public static String get(String key) {
        return ResourceChat.getResourceFile(key, "connection_resources");
    }

}

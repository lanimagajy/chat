package com.epam.chat.utils;


public class ResourceStatement {
    
    public static String get(String key) {
        return ResourceChat.getResourceFile(key, "queries");
    }
}

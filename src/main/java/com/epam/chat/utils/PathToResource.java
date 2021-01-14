package com.epam.chat.utils;

public class PathToResource {
    public static String get(String key) {
        return ResourceChat.getResourceFile(key, "path");
    }
}

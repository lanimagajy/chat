package com.epam.chat.datalayer.dto;

public enum Status {
    ONLINE,
    OFFLINE,
    KICKED;


    @Override
    public String toString() {
        return "Status{" + Status.this.name() + "}";
    }
}

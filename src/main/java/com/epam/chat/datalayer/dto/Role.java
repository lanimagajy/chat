package com.epam.chat.datalayer.dto;


public enum Role {
    ADMIN("Administrator role"),
    USER("User role"),
    SYSTEM("System user");

    private String description;

    Role(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return "Role{ title='" + Role.this.name() + "', " +
                "description='" + description + '\'' +
                '}';
    }
}

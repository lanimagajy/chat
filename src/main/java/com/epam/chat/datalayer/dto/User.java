package com.epam.chat.datalayer.dto;


import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Represents chat user
 */
@Getter
@Setter
@EqualsAndHashCode
@ToString
@NoArgsConstructor
public class User {
    private int userID;
    private String nick;
    private Role role;
    private Status status;

    public User(int userID, String nick, Role role, Status status) {
        this.userID = userID;
        this.nick = nick;
        this.role = role;
        this.status = status;
    }


    public String getUserListOnline() {
        return "lksdajfhkasdhfjkashdfjklasdlfja;";
    }
}

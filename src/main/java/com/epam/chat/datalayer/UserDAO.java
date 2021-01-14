package com.epam.chat.datalayer;

import com.epam.chat.datalayer.dto.Role;
import com.epam.chat.datalayer.dto.Status;
import com.epam.chat.datalayer.dto.User;

import java.util.List;


/**
 * Describes access interface to user Data Access Object
 */
public interface UserDAO {


    /**
     * Get role of user by his nickname
     *
     * @param nick nick of user to find the role
     * @return role os user
     */

    User getUserByNick(String nick);

    void createNewUser(String nick, Role role);

    void changeUserStatus(int userID, Status status);

    User getSystemUser();

    User getUserByUserID(int userID);

    boolean checkingStatus(String user, Status status);

    List<User> getAllUsersByStatus(Status status);

}

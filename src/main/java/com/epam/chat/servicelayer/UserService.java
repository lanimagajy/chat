package com.epam.chat.servicelayer;

import com.epam.chat.datalayer.dto.Role;
import com.epam.chat.datalayer.dto.User;

import java.util.List;


/**
 * Describes access interface to user Data Access Object
 */
public interface UserService {

    /**
     * Get all users logged in the system
     *
     * @return list of users
     */
    List<User> getAllLogged();

    /**
     * Get all kicked users
     *
     * @return list of users
     */
    List<User> getAllKicked();

    /**
     * Get role of user by his nickname
     *
     * @param nick nick of user to find the role
     * @return role os user
     */
    Role getRole(String nick);

    boolean isKicked(String nick);

    boolean isLoggedIn(String nick);

    User getUserByNick(String nick);

    List<User> getAllOffline();

    void logout(String userToLogout);
}

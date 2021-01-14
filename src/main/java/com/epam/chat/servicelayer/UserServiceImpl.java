package com.epam.chat.servicelayer;

import com.epam.chat.datalayer.DAOFactory;
import com.epam.chat.datalayer.DBType;
import com.epam.chat.datalayer.UserDAO;
import com.epam.chat.datalayer.dto.Role;
import com.epam.chat.datalayer.dto.Status;
import com.epam.chat.datalayer.dto.User;
import com.epam.chat.exception.KickStatusException;
import com.epam.chat.exception.StatusException;
import com.epam.chat.utils.PropertyProvider;

import java.util.List;

public class UserServiceImpl implements UserService {


    public static final String EPAM_COM = "@epam.com";
    public static final String STRING_FORMAT_WITH_ADMIN_AND_USER = "%s: %s now is %s";
    public static final String STRING_FORMAT_WITH_USER = "%s now is %s";
    public static final String DATASOURCE = "datasource";
    public UserDAO userDAO;
    private static UserServiceImpl instance;

    private UserServiceImpl() {
        String type = PropertyProvider.get(DATASOURCE);
        userDAO = DAOFactory.getInstance(DBType.getTypeByName(type)).getUserDAO();
    }

    public static UserServiceImpl getInstance() {
        if (instance == null) {
            instance = new UserServiceImpl();
        }
        return instance;
    }

    public void login(String userNick) {
        User user = userDAO.getUserByNick(userNick);

        if (user != null) {
            if (!user.getStatus().equals(Status.KICKED)) {
                userDAO.changeUserStatus(user.getUserID(), Status.ONLINE);
            }
            else  {
                throw new KickStatusException("This user is blocked");
            }
        }
        else  {
            userDAO.createNewUser(userNick, getRole(userNick));
        }
        MessageServiceImpl.getInstance().sendSystemMessage(String.format(
                STRING_FORMAT_WITH_USER, userNick, Status.ONLINE));
    }


@Override
    public void logout(String userToLogout) {
        User user = userDAO.getUserByNick(userToLogout);
        if (!(user.getStatus().equals(Status.KICKED))) {
            userDAO.changeUserStatus(user.getUserID(), Status.OFFLINE);
            MessageServiceImpl.getInstance().sendSystemMessage(String.format(
                    STRING_FORMAT_WITH_USER, userToLogout, Status.OFFLINE));
        }
    }

    public void unkick(String adminUser, String userUnkick) {
        User user = userDAO.getUserByNick(userUnkick);
        User admin = userDAO.getUserByNick(adminUser);
        if (admin.getRole().equals(Role.ADMIN)) {
            if (user.getRole().equals(Role.USER)) {
                if (user.getStatus().equals(Status.KICKED)) {
                    userDAO.changeUserStatus(user.getUserID(), Status.OFFLINE);
                    MessageServiceImpl.getInstance().sendSystemMessage(
                            String.format(STRING_FORMAT_WITH_ADMIN_AND_USER, adminUser, userUnkick, Status.OFFLINE));
                }
            }
        } else if (admin.getRole().equals(Role.USER)) {
            throw new StatusException("Only the administrator can remove the ban of another user");
        } else if (user.getStatus() != Status.KICKED) {
            throw new StatusException("This user is not blocked");
        } else {
            throw new StatusException("There is no such user");
        }
    }

    public void kick(String adminUser, String kickableUser) {
        User user = userDAO.getUserByNick(kickableUser);
        User admin = userDAO.getUserByNick(adminUser);
        if (admin.getRole().equals(Role.ADMIN)) {
            if (user.getRole().equals(Role.USER)) {
                if (!(user.getStatus().equals(Status.KICKED))) {
                    userDAO.changeUserStatus(user.getUserID(), Status.KICKED);
                    MessageServiceImpl.getInstance().sendSystemMessage(
                            String.format(STRING_FORMAT_WITH_ADMIN_AND_USER, adminUser, kickableUser, Status.KICKED));
                }
            }
        } else if (admin.getRole().equals(Role.USER)) {
            throw new StatusException("Only the administrator can ban another user");
        } else if (user.getStatus().equals(Status.KICKED)) {
            throw new StatusException("This user is already banned");
        } else if (user.getRole().equals(Role.ADMIN)) {
            throw new StatusException("This user is an administrator");
        }
    }

    @Override
    public boolean isKicked(String nick) {
        return userDAO.checkingStatus(nick, Status.KICKED);
    }

    @Override
    public boolean isLoggedIn(String nick) {
        return userDAO.checkingStatus(nick, Status.ONLINE);
    }

    @Override
    public User getUserByNick(String nick) {
        return userDAO.getUserByNick(nick);
    }


    @Override
    public List<User> getAllLogged() {
        return userDAO.getAllUsersByStatus(Status.ONLINE);
    }

    @Override
    public List<User> getAllKicked() {
        return userDAO.getAllUsersByStatus(Status.KICKED);
    }

    @Override
    public List<User> getAllOffline() {
        return userDAO.getAllUsersByStatus(Status.OFFLINE);
    }

    @Override
    public Role getRole(String nick) {
        Role role;
        if (nick.endsWith(EPAM_COM)) {
            role = Role.ADMIN;
        } else {
            role = Role.USER;
        }
        return role;
    }
}

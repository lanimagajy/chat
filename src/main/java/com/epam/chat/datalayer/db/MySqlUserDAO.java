package com.epam.chat.datalayer.db;

import com.epam.chat.datalayer.UserDAO;
import com.epam.chat.datalayer.db.connection.ConnectionPool;
import com.epam.chat.datalayer.dto.Role;
import com.epam.chat.datalayer.dto.Status;
import com.epam.chat.datalayer.dto.User;
import com.epam.chat.exception.DatabaseOperationException;
import com.epam.chat.utils.ResourceStatement;
import lombok.extern.log4j.Log4j;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Log4j

public class MySqlUserDAO implements UserDAO {


    private static final String CREATE_NEW_USER_REQUEST = "createNewUserRequest";
    private static final String GET_USER_BY_NICK_REQUEST = "getUserByNickRequest";
    private static final String CHANGE_USER_STATUS_REQUEST = "changeUserStatusRequest";
    private static final String CHECKING_STATUS_REQUEST = "checkingStatusRequest";
    private static final String GET_ALL_USERS_BY_STATUS_REQUEST = "getAllUsersByStatusRequest";
    private static final String GET_SYSTEM_USER_REQUEST = "getSystemUserRequest";
    private static final String GET_USER_BY_USER_ID_REQUEST = "getUserByUserIDRequest";
    private static final int ONE = 1;
    private static final int TWO = 2;
    private static final int THREE = 3;
    private static final String ROLE_USER = "role_user";
    private static final String ID = "ID";
    private static final String NICK_USER = "nick_user";
    private static final String STATUS_USER = "status_user";
    private ConnectionPool connectionPool;

    public MySqlUserDAO() {
        connectionPool = ConnectionPool.getInstance();
    }

    @Override
    public User getUserByNick(String nick) {
        User user = null;
        try (Connection connection = connectionPool.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    ResourceStatement.get(GET_USER_BY_NICK_REQUEST));
            preparedStatement.setString(ONE, nick);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    int id = resultSet.getInt(ID);
                    Role role = Role.valueOf(resultSet.getString(ROLE_USER));
                    Status status = Status.valueOf(resultSet.getString(STATUS_USER));
                    user = new User(id, nick, role, status);
                }
            }
        } catch (SQLException throwables) {
            log.error("Error in getting a user by nickname", throwables);
            throw new DatabaseOperationException("Error working with the database", throwables);
        }
        return user;
    }


    @Override
    public void changeUserStatus(int userID, Status status) {
        try (Connection connection = connectionPool.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    ResourceStatement.get(CHANGE_USER_STATUS_REQUEST));
            preparedStatement.setObject(ONE, status.name());
            preparedStatement.setInt(TWO, userID);
            preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            log.error("Check error status of the user", throwables);
            throw new DatabaseOperationException("Error working with the database", throwables);
        }
    }

    @Override
    public void createNewUser(String nick, Role role) {
        try (Connection connection = connectionPool.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    ResourceStatement.get(CREATE_NEW_USER_REQUEST));
            preparedStatement.setString(ONE, nick);
            preparedStatement.setObject(TWO, role.name());
            preparedStatement.setObject(THREE, Status.ONLINE.name());
            preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            log.error("Error creating a new user", throwables);
            throw new DatabaseOperationException("Error working with the database", throwables);
        }
    }


    @Override
    public User getSystemUser() {
        User user = null;
        try (Connection connection = connectionPool.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    ResourceStatement.get(GET_SYSTEM_USER_REQUEST));
            preparedStatement.setObject(ONE, Role.SYSTEM.name());
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    if (user == null) {
                        Role role = Role.valueOf(resultSet.getString(ROLE_USER));
                        if (Role.SYSTEM.name().equals(role.name())) {
                            int userID = resultSet.getInt(ID);
                            String nick = resultSet.getString(NICK_USER);
                            Status status = Status.valueOf(resultSet.getString(STATUS_USER));
                            user = new User(userID, nick, role, status);
                        }
                    }
                }
            }
        } catch (SQLException throwables) {
            log.error("Error getting the system user", throwables);
            throw new DatabaseOperationException("Error working with the database", throwables);
        }
        return user;
    }

    @Override
    public User getUserByUserID(int userID) {
        User user = null;
        try (Connection connection = connectionPool.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    ResourceStatement.get(GET_USER_BY_USER_ID_REQUEST));
            preparedStatement.setInt(ONE, userID);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    String nick = resultSet.getString(NICK_USER);
                    Role role = Role.valueOf(resultSet.getString(ROLE_USER));
                    Status status = Status.valueOf(resultSet.getString(STATUS_USER));
                    user = new User(userID, nick, role, status);
                }
            }
        } catch (SQLException throwables) {
            log.error("Error getting a user by User ID", throwables);
            throw new DatabaseOperationException("Error working with the database", throwables);
        }
        return user;
    }

    @Override
    public boolean checkingStatus(String user, Status status) {
        boolean isStatus = false;
        try (Connection connection = connectionPool.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    ResourceStatement.get(CHECKING_STATUS_REQUEST));
            preparedStatement.setString(ONE, user);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    Status statusUser = Status.valueOf(resultSet.getString(STATUS_USER));
                    if (statusUser.equals(status)) {
                        isStatus = true;
                    }
                }
            }
        } catch (SQLException throwables) {
            log.error("Status check error", throwables);
            throw new DatabaseOperationException("Error working with the database", throwables);
        }
        return isStatus;
    }

    @Override
    public List<User> getAllUsersByStatus(Status status) {
        List<User> userList = new ArrayList<>();
        try (Connection connection = connectionPool.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    ResourceStatement.get(GET_ALL_USERS_BY_STATUS_REQUEST));
            preparedStatement.setString(ONE, status.name());
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    int id = resultSet.getInt(ID);
                    Role role = Role.valueOf(resultSet.getString(ROLE_USER));
                    String nick = resultSet.getString(NICK_USER);
                    Status statusUser = Status.valueOf(resultSet.getString(STATUS_USER));
                    userList.add(new User(id, nick, role, statusUser));
                }
            }
        } catch (SQLException throwables) {
            log.error("Error getting all users by status", throwables);
            throw new DatabaseOperationException("Error working with the database", throwables);
        }
        return userList;
    }
}

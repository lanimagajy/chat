package com.epam.chat.datalayer.db;

import com.epam.chat.datalayer.MessageDAO;
import com.epam.chat.datalayer.db.connection.ConnectionPool;
import com.epam.chat.datalayer.dto.Message;
import com.epam.chat.datalayer.dto.Role;
import com.epam.chat.datalayer.dto.Status;
import com.epam.chat.datalayer.dto.User;
import com.epam.chat.exception.DatabaseOperationException;
import com.epam.chat.utils.ResourceStatement;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


import lombok.extern.log4j.Log4j;


@Log4j
public class MySqlMessageDAO implements MessageDAO {
    private static final String SEND_MESSAGE_REQUEST = "sendMessageRequest";
    private static final String GET_LAST_REQUEST = "getLastRequest";
    private static final int ONE = 1;
    private static final int TWO = 2;
    private static final int THREE = 3;
    private static final String ID = "ID";
    private static final String USER_ID = "user_ID";
    private static final String DATA_MESSAGE = "data_message";
    private static final String TEXT_MESSAGE = "text_message";
    private static final String NICK_USER = "nick_user";
    private static final String ROLE_USER = "role_user";
    private static final String STATUS_USER = "status_user";
    private ConnectionPool connectionPool;

    public MySqlMessageDAO() {
        connectionPool = ConnectionPool.getInstance();
    }

    @Override
    public void sendMessage(String message, int userID) {

        try (Connection connection = connectionPool.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    ResourceStatement.get(SEND_MESSAGE_REQUEST));
            preparedStatement.setInt(ONE, userID);
            Date date = new Date();
            preparedStatement.setTimestamp(TWO, new Timestamp(date.getTime()));
            preparedStatement.setObject(THREE, message);
            preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            log.error("Error writing a message to the database", throwables);
            throw new DatabaseOperationException("Error working with the database", throwables);
        }
    }

    @Override
    public List<Message> getLast(int count) {
        List<Message> messageList = new ArrayList<>();
        try (Connection connection = connectionPool.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    ResourceStatement.get(GET_LAST_REQUEST));
            preparedStatement.setInt(ONE, count);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    int id = resultSet.getInt(ID);
                    int userID = resultSet.getInt(USER_ID);
                    Date date= resultSet.getTimestamp(DATA_MESSAGE);
                    String textMessage = resultSet.getString(TEXT_MESSAGE);
                    String nickUser = resultSet.getString(NICK_USER);
                    Role role = Role.valueOf(resultSet.getString(ROLE_USER));
                    Status status= Status.valueOf(resultSet.getString(STATUS_USER));
                    messageList.add(new Message(id, new User(userID, nickUser, role, status), userID, date, textMessage));
                }
            }
        } catch (SQLException throwables) {
            log.error("Error reading data from the database", throwables);
            throw new DatabaseOperationException("Error working with the database", throwables);
        }
        return messageList;
    }

}

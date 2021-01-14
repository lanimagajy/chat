package com.epam.chat.datalayer.db.connection;

import com.epam.chat.exception.ConnectionException;
import com.epam.chat.utils.ResourceConnection;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import javax.sql.DataSource;

import lombok.extern.log4j.Log4j;

@Log4j
public class ConnectionPool {

    private String sizePoolConnection;
    private int sizePool;
    private static ConnectionPool connectionPool;
    private DataSource dataSource;
    private BlockingQueue<Connection> connectionBlockingQueue;


    public ConnectionPool() {
        sizePoolConnection = ResourceConnection.get("sizePoolConnection");
        sizePool = Integer.parseInt(sizePoolConnection);
        this.dataSource = DataSourceFactory.getPGSQLDataSource();
        connectionBlockingQueue = new ArrayBlockingQueue<>(sizePool);
    }

    public static ConnectionPool getInstance() {
        if (connectionPool == null) {
            connectionPool = new ConnectionPool();
            connectionPool.offerConnection();
        }
        return connectionPool;
    }

    public Connection getConnection() {
        try {
            synchronized (this) {
                return new IntermediaryConnection(connectionBlockingQueue.take(), this);
            }
        } catch (InterruptedException e) {
            log.error("Connection received error", e);
            throw new ConnectionException();
        }
    }


    public BlockingQueue<Connection> offerConnection() {
        for (int i = 0; i < sizePool; i++) {
            try {
                connectionBlockingQueue.offer(dataSource.getConnection());
            } catch (SQLException e) {
                log.error("Error to try open connection", e);
            }
        }
        return connectionBlockingQueue;
    }

    public void closeConnection (){
        try {
            this.getConnection().close();
            System.out.println(this.getConnection().getClientInfo());
        } catch (SQLException e) {
            log.error("Error close connection", e);
        }
    }


    public void realiseConnection(Connection connection) {
        connectionBlockingQueue.offer(connection);
    }


}

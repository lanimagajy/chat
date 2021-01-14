package com.epam.chat.datalayer.db.connection;

import com.epam.chat.utils.ResourceConnection;

import org.postgresql.ds.PGSimpleDataSource;

import java.util.Locale;

import javax.sql.DataSource;


public class DataSourceFactory {

    private static final String SERVER_NAME = "serverName";
    private static final String PORT_NUMBER = "portNumber";
    private static final String DATABASE_NAME = "databaseName";
    private static final String USER = "user";
    private static final String PASSWORD = "password";
    private static volatile PGSimpleDataSource pgSimpleDataSource;


    public static DataSource getPGSQLDataSource() {
        PGSimpleDataSource dataSource = pgSimpleDataSource;
        if (dataSource == null) {
            synchronized (DataSourceFactory.class) {
                dataSource = pgSimpleDataSource;
                if (dataSource == null) {
                    pgSimpleDataSource = new PGSimpleDataSource();
                    dataSource = pgSimpleDataSource;
                    initPGSQLDataSource();
                }
            }
        }
        return dataSource;
    }

    private static void initPGSQLDataSource() {
        Locale.setDefault(Locale.ENGLISH);
        pgSimpleDataSource.setServerName(ResourceConnection.get(SERVER_NAME));
        pgSimpleDataSource.setPortNumber(Integer.parseInt(ResourceConnection.get(PORT_NUMBER)));
        pgSimpleDataSource.setDatabaseName(ResourceConnection.get(DATABASE_NAME));
        pgSimpleDataSource.setUser(ResourceConnection.get(USER));
        pgSimpleDataSource.setPassword(ResourceConnection.get(PASSWORD));
    }

}


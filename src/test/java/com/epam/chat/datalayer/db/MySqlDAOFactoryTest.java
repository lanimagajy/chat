package com.epam.chat.datalayer.db;


import com.epam.chat.datalayer.DAOFactory;
import com.epam.chat.datalayer.DBType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.IOException;

import static junit.framework.TestCase.assertTrue;

@RunWith(JUnit4.class)
public class MySqlDAOFactoryTest {

    private DAOFactory daoFactory = DAOFactory.getInstance(DBType.MYSQL);

    @Test
    public void getMessageDAO_whenMethodCalled_thenReturnXMLImplementation() {
        assertTrue(daoFactory.getMessageDAO() instanceof MySqlMessageDAO);
    }

    @Test
    public void getUserDAO_whenMethodCalled_thenReturnXMLImplementation() {
        assertTrue(daoFactory.getUserDAO() instanceof MySqlUserDAO);
    }
}

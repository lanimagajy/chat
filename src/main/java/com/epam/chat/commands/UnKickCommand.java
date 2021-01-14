package com.epam.chat.commands;

import com.epam.chat.datalayer.dto.User;
import com.epam.chat.servicelayer.UserServiceImpl;
import com.epam.chat.utils.ParameterNames;
import com.epam.chat.utils.PathToResource;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class UnKickCommand implements Command {

    public static final String PATH_TO_PAGE_CH = "pathToPageCH";

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession httpSession = req.getSession();
        User admin = (User) httpSession.getAttribute(ParameterNames.USER_SESSION);
        String adminName = admin.getNick();
        String userName = req.getParameter(ParameterNames.UNKICK_USER);
        UserServiceImpl.getInstance().unkick(adminName, userName);
        String path = req.getContextPath() + PathToResource.get(PATH_TO_PAGE_CH);
        resp.sendRedirect(path);
    }
}

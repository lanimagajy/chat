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

public class LoginCommand implements Command {

    public static final String THIS_NICKNAME_DOES_NOT_MEET_THE_STANDARDS = " this nickname does not meet the standards";
    public static final String PATH_TO_PAGE_CH = "pathToPageCH";
    public static final String PATH_TO_LOGIN_JSP = "pathToLoginJSP";

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        HttpSession httpSession = req.getSession();
        String userName = req.getParameter(ParameterNames.USER_LOGIN);

        if (checkingNickname(userName)) {
            if (userName.length() <= 25 && userName.length() >= 3) {
                UserServiceImpl.getInstance().login(userName);
                User user = UserServiceImpl.getInstance().getUserByNick(userName);
                httpSession.setAttribute(ParameterNames.USER_SESSION, user);
                String path = req.getContextPath() + PathToResource.get(PATH_TO_PAGE_CH);
                resp.sendRedirect(path);
            }
        } else {
            req.setAttribute(ParameterNames.MESSAGE_ERROR, userName + THIS_NICKNAME_DOES_NOT_MEET_THE_STANDARDS);
            req.getRequestDispatcher(PathToResource.get(PATH_TO_LOGIN_JSP)).forward(req, resp);
        }
    }

    private boolean checkingNickname(String nick) {
        String validNick = nick.trim();
        return validNick.equals(nick);
    }
}

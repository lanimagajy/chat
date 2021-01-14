package com.epam.chat.commands;


import com.epam.chat.utils.PathToResource;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class LogoutCommand implements Command {

    public static final String PATH_TO_LOGIN_JSP = "pathToLoginJSP";

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        HttpSession httpSession = req.getSession();
        httpSession.invalidate();
        req.getRequestDispatcher(PathToResource.get(PATH_TO_LOGIN_JSP)).forward(req, resp);
    }
}

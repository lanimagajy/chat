package com.epam.chat.filters;


import com.epam.chat.commands.CommandTypes;
import com.epam.chat.datalayer.dto.User;
import com.epam.chat.servicelayer.UserService;
import com.epam.chat.servicelayer.UserServiceImpl;
import com.epam.chat.utils.ParameterNames;
import com.epam.chat.utils.PathToResource;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Implementation of servlet filter
 */
public class ChatFilter implements Filter {

    public static final String CP_1251 = "Cp1251";
    public static final String TEXT_HTML_CHARSET_CP_1251 = "text/html; charset=Cp1251";
    public static final String PATH_TO_LOGIN_JSP = "pathToLoginJSP";


    @Override
    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        request.setCharacterEncoding(CP_1251);
        response.setCharacterEncoding(CP_1251);
        response.setContentType(TEXT_HTML_CHARSET_CP_1251);
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        HttpSession httpSession = req.getSession();
        User user = (User) httpSession.getAttribute(ParameterNames.USER_SESSION);

        if (user != null) {
            if (isKickUser(req)) {
                getLoginPageWithError(req, resp);
            } else {
                chain.doFilter(request, response);
            }
        } else {
            if (isLoginCommand(req)) {
                if (!isKick(req)) {
                    chain.doFilter(request, response);
                } else {
                    getLoginPageWithError(req, resp);
                }
            } else {
                req.getRequestDispatcher(PathToResource.get(PATH_TO_LOGIN_JSP)).forward(req, resp);
            }
        }
    }

    private boolean isKick(HttpServletRequest req) {
        String nick = req.getParameter(ParameterNames.USER_LOGIN);
        UserService userService = (UserService) req.getServletContext().getAttribute(ParameterNames.USER_SERVICE);
        try {
            return userService.isKicked(nick);
        } catch (NullPointerException e) {
            return false;
        }
    }

    private void getLoginPageWithError(HttpServletRequest req, ServletResponse resp) throws ServletException, IOException {
        req.setAttribute(ParameterNames.MESSAGE_ERROR, ParameterNames.THIS_NICKNAME_WERE_BLOCKED);
        req.getRequestDispatcher(PathToResource.get(PATH_TO_LOGIN_JSP)).forward(req, resp);
    }

    private boolean isKickUser(HttpServletRequest request) {
        UserServiceImpl userService = (UserServiceImpl) request.getServletContext().getAttribute(ParameterNames.USER_SERVICE);
        try {
            User user = (User) request.getSession().getAttribute(ParameterNames.USER_SESSION);
            if (userService.isKicked(user.getNick())) {
                request.getSession().invalidate();
                return true;
            } else {
                return false;
            }
        } catch (NullPointerException e) {
            return false;
        }
    }

    private boolean isLoginCommand(HttpServletRequest request) {
        try {
            return (CommandTypes.LOGIN.name()).equals(request.getParameter(ParameterNames.COMMAND));
        } catch (NullPointerException e) {
            return false;
        }
    }


    @Override
    public void init(FilterConfig fConfig) {

    }

}

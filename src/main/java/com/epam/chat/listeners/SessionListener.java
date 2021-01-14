package com.epam.chat.listeners;

import com.epam.chat.datalayer.dto.User;
import com.epam.chat.servicelayer.UserService;
import com.epam.chat.utils.ParameterNames;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;


public class SessionListener implements HttpSessionListener {


	@Override
	public void sessionCreated(HttpSessionEvent event) {

	}

	@Override
	public void sessionDestroyed(HttpSessionEvent event) {
		User user = (User) event.getSession().getAttribute(ParameterNames.USER_SESSION);
		if (user != null) {
			UserService userService = (UserService) event.getSession().getServletContext().getAttribute(ParameterNames.USER_SERVICE);
			userService.logout(user.getNick());
		}
	}
}

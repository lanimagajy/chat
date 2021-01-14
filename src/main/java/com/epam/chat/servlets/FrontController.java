package com.epam.chat.servlets;

import com.epam.chat.commands.*;
import com.epam.chat.exception.InvalidArgumentException;
import com.epam.chat.utils.ParameterNames;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import com.epam.chat.utils.PathToResource;
import lombok.extern.log4j.Log4j;

/**
 * Front controller implementation
 */
@Log4j
public class FrontController extends HttpServlet {

	private static final long serialVersionUID = 1L;
	public static final String PATH_TO_CHAT_JSP = "pathToChatJSP";

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.getServletContext().getRequestDispatcher(PathToResource.get(PATH_TO_CHAT_JSP)).forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		gerCommand(req, resp);
	}

	protected Command defineCommand(String commandName) {
		try {
			return CommandTypes.valueOf(commandName.toUpperCase()).getCommand();
		} catch (IllegalArgumentException e) {
			log.error("Error: an illegal or inappropriate parameter was accepted", e);
			throw new InvalidArgumentException("The wrong command was received", e);
		}
	}

	private void gerCommand(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Command command = defineCommand(req.getParameter(ParameterNames.COMMAND));
		command.execute(req, resp);
	}
}

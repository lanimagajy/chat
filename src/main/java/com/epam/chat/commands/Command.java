package com.epam.chat.commands;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 
 * Interface for commands
 *
 */
public interface Command {

	/**
	 * Perform action provided by command
	 *
	 * @param req
	 * @param resp
	 */
	void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException;

}

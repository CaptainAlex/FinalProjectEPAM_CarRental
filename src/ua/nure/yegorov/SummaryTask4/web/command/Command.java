package ua.nure.yegorov.SummaryTask4.web.command;

import java.io.IOException;
import java.io.Serializable;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ua.nure.yegorov.SummaryTask4.exception.AppException;

/**
 * Main interface for the Command pattern implementation.
 * 
 * @author A.Yegorov
 *
 */
public abstract class Command implements Serializable{
	
	private static final long serialVersionUID = -4092484511343869925L;

	/**
	 * Execution method for command.
	 * 
	 * @return Address to go once the command is executed.
	 */
	public abstract String execute(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException,
			AppException;

	@Override
	public final String toString() {
		return getClass().getSimpleName();
	}

}

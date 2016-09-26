package ua.nure.yegorov.SummaryTask4.web.command;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import ua.nure.yegorov.SummaryTask4.Path;
import ua.nure.yegorov.SummaryTask4.exception.AppException;

/**
 * View change user status command.
 * 
 * @author A.Yegorov
 *
 */
public class ViewChangeUserStatusCommand extends Command {

	private static final long serialVersionUID = 6877145183829473251L;

	private static final Logger LOG = Logger
			.getLogger(ViewChangeUserStatusCommand.class);

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException,
			AppException {
		LOG.debug("ViewChangeUserStatusCommand starts");
		LOG.debug("ViewChangeUserStatusCommand finished");
		return Path.PAGE_CHANGE_USER_STATUS;
	}
}

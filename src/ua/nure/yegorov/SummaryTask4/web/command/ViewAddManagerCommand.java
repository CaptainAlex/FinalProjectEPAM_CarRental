package ua.nure.yegorov.SummaryTask4.web.command;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import ua.nure.yegorov.SummaryTask4.Path;
import ua.nure.yegorov.SummaryTask4.exception.AppException;

/**
 * View add manager command.
 * 
 * @author A.Yegorov
 *
 */
public class ViewAddManagerCommand extends Command {

	private static final long serialVersionUID = 8634451502222349682L;

	private static final Logger LOG = Logger
			.getLogger(ViewAddManagerCommand.class);

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException,
			AppException {
		LOG.debug("ViewAddManagerCommand starts");
		LOG.debug("ViewAddManagerCommand finished");
		return Path.PAGE_ADD_MANAGER;
	}
}

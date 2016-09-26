package ua.nure.yegorov.SummaryTask4.web.command;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import ua.nure.yegorov.SummaryTask4.Path;
import ua.nure.yegorov.SummaryTask4.exception.AppException;

/**
 * View list cars command.
 * 
 * @author A.Yegorov
 *
 */
public class ViewListCarsCommand extends Command {

	private static final long serialVersionUID = 542160985642352754L;

	private static final Logger LOG = Logger
			.getLogger(ViewListCarsCommand.class);

	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException,
			AppException {
		LOG.debug("ViewListCarsCommand starts");
		LOG.debug("ViewListCarsCommand finished");
		return Path.PAGE_LIST_CARS_SORT;

	}
}

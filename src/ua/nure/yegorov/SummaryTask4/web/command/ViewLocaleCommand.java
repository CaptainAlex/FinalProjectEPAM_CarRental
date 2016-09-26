package ua.nure.yegorov.SummaryTask4.web.command;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import ua.nure.yegorov.SummaryTask4.Path;
import ua.nure.yegorov.SummaryTask4.exception.AppException;

/**
 * View locale command.
 * 
 * @author A.Yegorov
 *
 */
public class ViewLocaleCommand extends Command {

	private static final long serialVersionUID = -6147369041314448450L;

	private static final Logger LOG = Logger.getLogger(ViewLocaleCommand.class);

	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException,
			AppException {
		LOG.debug("ViewLocaleCommand starts");
		LOG.debug("ViewLocaleCommand finished");
		return Path.PAGE_LOCALE;

	}
}

package ua.nure.yegorov.SummaryTask4.web.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.jstl.core.Config;

import org.apache.log4j.Logger;

import ua.nure.yegorov.SummaryTask4.Path;
import ua.nure.yegorov.SummaryTask4.exception.DBException;

/**
 * Change locale command.
 * 
 * @author A.Yegorov
 *
 */
public class ChangeLocaleCommand extends Command {
	private static final long serialVersionUID = 1L;

	private static final Logger LOG = Logger
			.getLogger(ChangeLocaleCommand.class);

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws DBException {
		LOG.debug("ChangeLocaleCommand starts");
		HttpSession session = request.getSession();
		String locale = request.getParameter("locale");
		// set new locale to session
		session.setAttribute("currentLocale", locale);
		LOG.trace("Set the session attribute: currentLocale --> " + locale);

		// set new locale to fmt
		Config.set(session, Config.FMT_LOCALE, new java.util.Locale(locale));
		LOG.trace("Set the session attribute: locale --> " + locale);

		LOG.debug("ChangeLocaleCommand finished");
		return Path.PAGE_LOCALE;
	}

}

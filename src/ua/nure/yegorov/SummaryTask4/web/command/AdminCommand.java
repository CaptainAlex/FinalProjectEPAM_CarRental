package ua.nure.yegorov.SummaryTask4.web.command;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import ua.nure.yegorov.SummaryTask4.Path;
import ua.nure.yegorov.SummaryTask4.exception.AppException;

/**
 * Admin command.
 * 
 * @author A.Yegorov
 *
 */
public class AdminCommand extends Command {

	private static final long serialVersionUID = 8935684691170457139L;

	private static final Logger LOG = Logger.getLogger(ViewAddCarCommand.class);

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException,
			AppException {
		LOG.debug("AdminCommand starts");
		LOG.debug("AdminCommand finished");
		return Path.PAGE_ADMIN;
	}
}

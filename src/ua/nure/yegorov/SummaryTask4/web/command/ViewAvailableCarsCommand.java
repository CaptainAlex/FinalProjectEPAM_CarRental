package ua.nure.yegorov.SummaryTask4.web.command;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import ua.nure.yegorov.SummaryTask4.Path;
import ua.nure.yegorov.SummaryTask4.db.DBManager;
import ua.nure.yegorov.SummaryTask4.db.entity.Car;
import ua.nure.yegorov.SummaryTask4.exception.AppException;

/**
 * View available cars command.
 * 
 * @author A.Yegorov
 *
 */
public class ViewAvailableCarsCommand extends Command {
	private static final long serialVersionUID = 1L;

	private static final Logger LOG = Logger
			.getLogger(ViewAvailableCarsCommand.class);

	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException,
			AppException {
		LOG.debug("ViewAvailableCarsCommand starts");
		
		List<Car> cars = DBManager.getInstance().findAvailableCars();
		LOG.trace("Found in DB: availableCarsList --> " + cars);
		
		request.setAttribute("availableCarsListById", cars);
		LOG.trace("Set the request attribute: availableCarsListById --> " + cars);
		
		LOG.debug("ViewAvailableCarsCommand finished");
		return Path.PAGE_LIST_AVAILABLE_CARS;
	}
}

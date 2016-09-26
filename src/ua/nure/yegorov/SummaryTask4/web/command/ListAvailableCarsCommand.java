package ua.nure.yegorov.SummaryTask4.web.command;

import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
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
 * List available cars command.
 * 
 * @author A.Yegorov
 *
 */
public class ListAvailableCarsCommand extends Command {

	private static final long serialVersionUID = -8877189976158489449L;

	private static final Logger LOG = Logger
			.getLogger(ListAvailableCarsCommand.class);

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException,
			AppException {
		LOG.debug("ListAvailableCarsCommand starts");

		List<Car> cars = DBManager.getInstance().findAvailableCars();
		LOG.trace("Found in DB: availableCarsList --> " + cars);

		String sortCommand = request.getParameter("sort");

		if (sortCommand.equalsIgnoreCase("price")) {
			Collections.sort(cars, new Comparator<Car>() {
				public int compare(Car c1, Car c2) {
					return c1.getCarPrice() - c2.getCarPrice();
				}
			});
		} else if (sortCommand.equalsIgnoreCase("name")) {
			Collections.sort(cars, new Comparator<Car>() {
				public int compare(Car c1, Car c2) {
					return c1.getName().compareTo(c2.getName());
				}
			});
		}
			

		request.setAttribute("availableCarsList", cars);
		LOG.trace("Set the request attribute: availableCarsList --> " + cars);

		LOG.debug("ListAvailableCarsCommand finished");
		return Path.PAGE_LIST_AVAILABLE_CARS;
	}

}

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
 * List cars command.
 * 
 * @author A.Yegorov
 *
 */
public class ListCarsCommand extends Command {

	private static final long serialVersionUID = -2944053832470658789L;

	private static final Logger LOG = Logger.getLogger(ListCarsCommand.class);

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException,
			AppException {
		LOG.debug("ListCarCommand starts");

		List<Car> cars = DBManager.getInstance().findCars();
		LOG.trace("Found in DB: cars --> " + cars);

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

		request.setAttribute("carsList", cars);
		LOG.trace("Set the request attribute: carsList --> " + cars);

		LOG.debug("ListCarCommand finished");
		return Path.PAGE_LIST_CARS_SORT;
	}
}

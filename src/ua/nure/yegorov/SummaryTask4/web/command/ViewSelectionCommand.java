package ua.nure.yegorov.SummaryTask4.web.command;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import ua.nure.yegorov.SummaryTask4.Path;
import ua.nure.yegorov.SummaryTask4.db.DBManager;
import ua.nure.yegorov.SummaryTask4.db.entity.Car;
import ua.nure.yegorov.SummaryTask4.exception.AppException;

/**
 * View selection car command.
 * 
 * @author A.Yegorov
 *
 */
public class ViewSelectionCommand extends Command {

	private static final long serialVersionUID = 5436792136235525560L;

	private static final Logger LOG = Logger
			.getLogger(ViewSelectionCommand.class);

	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException,
			AppException {
		LOG.debug("ViewSelectionCommand starts");

		List<Car> cars = DBManager.getInstance().findAvailableCars();
		LOG.trace("Found in DB: cars --> " + cars);

		Set<String> selectCarsClass = new HashSet<String>();
		for (Car car : cars) {
			selectCarsClass.add(car.getCarClass());
		}

		request.setAttribute("selectCarsClass", selectCarsClass);
		LOG.trace("Set the request attribute: selectCarsClass --> "
				+ selectCarsClass);

		Set<String> selectCarsMark = new HashSet<String>();
		for (Car car : cars) {
			selectCarsMark.add(car.getMark());
		}

		request.setAttribute("selectCarsMark", selectCarsMark);
		LOG.trace("Set the request attribute: selectCarsMark --> "
				+ selectCarsMark);

		LOG.debug("ViewSelectionCommand finished");
		return Path.PAGE_SORT_BY_SOMETHING;
	}
}

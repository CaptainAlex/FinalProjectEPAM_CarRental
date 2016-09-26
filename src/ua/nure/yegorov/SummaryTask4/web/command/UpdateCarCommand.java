package ua.nure.yegorov.SummaryTask4.web.command;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import ua.nure.yegorov.SummaryTask4.Path;
import ua.nure.yegorov.SummaryTask4.db.DBManager;
import ua.nure.yegorov.SummaryTask4.db.entity.Car;
import ua.nure.yegorov.SummaryTask4.exception.AppException;

/**
 * Update car command.
 * 
 * @author A.Yegorov
 *
 */
public class UpdateCarCommand extends Command {

	private static final long serialVersionUID = -237904424337914430L;

	private static final Logger LOG = Logger.getLogger(UpdateCarCommand.class);

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException,
			AppException {
		LOG.debug("UpdateCarCommand starts");
		DBManager manager = DBManager.getInstance();
		HttpSession session = request.getSession();

		int carId = Integer.valueOf(request.getParameter("id"));
		LOG.trace("Request parameter: carId --> " + carId);

		Car car = manager.findCarById(carId);
		LOG.trace("Found in DB: car --> " + car);

		if (car == null) {
			String errorMessasge = "Car in DB not found";
			session.setAttribute("errorMessage", errorMessasge);
			LOG.error("Set the session attribute: errorMessage --> "
					+ errorMessasge);
			LOG.debug("UpdateCarCommand finished");
			return Path.PAGE_ERROR_PAGE;
		}

		String carPriceS = request.getParameter("carPrice");
		String carDriverPriceS = request.getParameter("carDriverPrice");

		if (carPriceS == null || carPriceS.isEmpty() || carDriverPriceS == null
				|| carDriverPriceS.isEmpty()) {
			String errorMessasge = "Price cannot be empty";
			session.setAttribute("errorMessage", errorMessasge);
			LOG.error("Set the session attribute: errorMessage --> "
					+ errorMessasge);
			LOG.debug("UpdateCarCommand finished");
			return Path.PAGE_ERROR_PAGE;
		}

		int carPrice = Integer.valueOf(carPriceS);
		car.setCarPrice(carPrice);
		LOG.trace("Request parameter: carPrice --> " + car.getCarPrice());

		int carDriverPrice = Integer.valueOf(carDriverPriceS);
		car.setCarDriverPrice(carDriverPrice);
		LOG.trace("Request parameter: carDriverPrice --> "
				+ car.getCarDriverPrice());

		manager.updateCarPrice(car);
		LOG.trace("Update order car --> " + car);

		LOG.debug("UpdateCarCommand finished");
		return Path.PAGE_ADMIN;
	}
}

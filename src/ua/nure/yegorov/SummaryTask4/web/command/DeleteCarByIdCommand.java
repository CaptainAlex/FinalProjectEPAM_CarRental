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
 * Delete car by id command.
 * 
 * @author A.Yegorov
 *
 */
public class DeleteCarByIdCommand extends Command {

	private static final long serialVersionUID = -621617257255814394L;

	private static final Logger LOG = Logger
			.getLogger(DeleteCarByIdCommand.class);

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException,
			AppException {
		LOG.debug("DeleteCarByIdCommand starts");
		DBManager manager = DBManager.getInstance();
		HttpSession session = request.getSession();
		String forward = Path.PAGE_ERROR_PAGE;

		String idS = request.getParameter("id");

		if (idS == null || idS.isEmpty()) {
			String errorMessasge = "Car Id cannot be empty";
			session.setAttribute("errorMessage", errorMessasge);
			LOG.error("Set the session attribute: errorMessage --> "
					+ errorMessasge);
			LOG.debug("DeleteCarByIdCommand finished");
			return forward;
		}

		int id = Integer.parseInt(idS);
		Car car = manager.findCarById(id);
		if (car == null) {
			String errorMessasge = "Car in DB not found";
			session.setAttribute("errorMessage", errorMessasge);
			LOG.error("Set the session attribute: errorMessage --> "
					+ errorMessasge);
			LOG.debug("DeleteCarByIdCommand finished");
			return forward;
		}

		boolean b = manager.deleteCarById(id);
		if (!b) {
			String errorMessasge = "Car didn't delete";
			session.setAttribute("errorMessage", errorMessasge);
			LOG.error("Set the session attribute: errorMessage --> "
					+ errorMessasge);
			LOG.debug("DeleteCarByIdCommand finished");
			return forward;
		}

		forward = Path.PAGE_ADMIN;
		LOG.trace("Delete car by id --> " + id);

		LOG.debug("DeleteCarByIdCommand finished");
		return forward;
	}
}

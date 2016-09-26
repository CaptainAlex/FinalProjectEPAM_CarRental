package ua.nure.yegorov.SummaryTask4.web.command;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import ua.nure.yegorov.SummaryTask4.Path;
import ua.nure.yegorov.SummaryTask4.db.DBManager;
import ua.nure.yegorov.SummaryTask4.db.Status;
import ua.nure.yegorov.SummaryTask4.db.entity.Order;
import ua.nure.yegorov.SummaryTask4.exception.AppException;

/**
 * Confirm order command.
 * 
 * @author A.Yegorov
 *
 */
public class ConfirmOrderCommand extends Command {

	private static final long serialVersionUID = -6240793727453182133L;

	private static final Logger LOG = Logger
			.getLogger(ConfirmOrderCommand.class);

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException,
			AppException {
		LOG.debug("ConfirmOrderCommand starts");
		DBManager manager = DBManager.getInstance();
		HttpSession session = request.getSession();

		String orderIdS = request.getParameter("id");
		if (orderIdS == null || orderIdS.isEmpty()) {
			String errorMessasge = "Order Id cannot be empty";
			session.setAttribute("errorMessage", errorMessasge);
			LOG.error("Set the session attribute: errorMessage --> "
					+ errorMessasge);
			LOG.debug("ConfirmOrderCommand finished");
			return Path.PAGE_ERROR_PAGE;
		}

		int orderId = Integer.valueOf(orderIdS);
		LOG.trace("Request parameter: orderId --> " + orderId);

		Order order = manager.findOrderById(orderId);
		LOG.trace("Found in DB: order --> " + order);

		if (order.getId() == 0) {
			String errorMessasge = "Order in DB not found";
			session.setAttribute("errorMessage", errorMessasge);
			LOG.error("Set the session attribute: errorMessage --> "
					+ errorMessasge);
			LOG.debug("ConfirmOrderCommand finished");
			return Path.PAGE_ERROR_PAGE;
		}

		int statusConfirm = Status.CONFIRMED.ordinal();
		order.setStatusId(statusConfirm);

		manager.updateOrderStatusRej(order);
		LOG.trace("Update order status --> " + order);

		LOG.debug("ConfirmOrderCommand finished");
		return Path.PAGE_MANAGER;
	}
}

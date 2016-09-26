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
 * Return car command.
 * 
 * @author A.Yegorov
 *
 */
public class ReturnCarCommand extends Command {

	private static final long serialVersionUID = -5830610361996655024L;

	private static final Logger LOG = Logger.getLogger(ReturnCarCommand.class);

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException,
			AppException {
		LOG.debug("ReturnCarCommand starts");
		DBManager manager = DBManager.getInstance();
		HttpSession session = request.getSession();

		String orderIdS = request.getParameter("id");

		if (orderIdS == null || orderIdS.isEmpty()) {
			String errorMessasge = "Order Id cannot be empty";
			session.setAttribute("errorMessage", errorMessasge);
			LOG.error("Set the session attribute: errorMessage --> "
					+ errorMessasge);
			LOG.debug("ReturnCarCommand finished");
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
			LOG.debug("ReturnCarCommand finished");
			return Path.PAGE_ERROR_PAGE;
		}

		int statusReject = Status.CLOSED.ordinal();
		order.setStatusId(statusReject);

		String status = request.getParameter("damage");
		boolean damage = true;
		if (status == null) {
			damage = false;
		}
		order.setDamage(damage);
		LOG.trace("Request parameter: damage --> " + order.isDamage());

		if (order.isDamage()) {
			String price = request.getParameter("priceForRepairs");
			if (price.isEmpty()) {
				String errorMessasge = "Price cannot be empty";
				session.setAttribute("errorMessage", errorMessasge);
				LOG.error("Set the session attribute: errorMessage --> "
						+ errorMessasge);
				LOG.debug("ReturnCarCommand finished");
				return Path.PAGE_ERROR_PAGE;
			} else {
				int priceForRepairs = Integer.valueOf(price);
				order.setPriceForRepairs(priceForRepairs);
				LOG.trace("Request parameter: priceForRepairs --> "
						+ order.getPriceForRepairs());
			}
		}

		manager.updateOrderReturn(order);
		LOG.trace("Update order status --> " + order);

		LOG.debug("ReturnCarCommand finished");
		return Path.PAGE_MANAGER;
	}

}

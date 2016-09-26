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
 * Payment command.
 * 
 * @author A.Yegorov
 *
 */
public class PaymentCommand extends Command {

	private static final long serialVersionUID = 1460935973291429009L;

	private static final Logger LOG = Logger.getLogger(PaymentCommand.class);

	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException,
			AppException {
		LOG.debug("PaymentCommand starts");
		DBManager manager = DBManager.getInstance();
		HttpSession session = request.getSession();

		int orderId = (int) session.getAttribute("orderId");
		LOG.trace("Get parameter: orderId --> " + orderId);

		Order order = manager.findOrderById(orderId);
		LOG.trace("Found in BD: order --> " + order);

		int statusIdConfirm = Status.PAID.ordinal();
		int statusIdRefuse = Status.CLOSED.ordinal();

		String result = request.getParameter("result");
		if (result != null) {
			if (result.equalsIgnoreCase("reject")) {
				order.setStatusId(statusIdRefuse);
				LOG.trace("Request parameter: status_id --> "
						+ order.getStatusId());
			}
			if (result.equalsIgnoreCase("success")) {
				order.setStatusId(statusIdConfirm);
				LOG.trace("Request parameter: status_id --> "
						+ order.getStatusId());
			}
		} else {
			order.setStatusId(Status.CLOSED.ordinal());
			LOG.trace("Request parameter: status_id --> " + order.getStatusId());
		}

		manager.updateOrderStatus(order);
		LOG.trace("Update parameter: statusId --> " + result);

		LOG.debug("PaymentCommand finished");
		return Path.PAGE_CLIENT;
	}
}
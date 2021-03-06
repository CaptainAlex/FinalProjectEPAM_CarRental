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
import ua.nure.yegorov.SummaryTask4.db.entity.Order;
import ua.nure.yegorov.SummaryTask4.exception.AppException;

/**
 * Get orders list command.
 * 
 * @author A.Yegorov
 *
 */
public class GetOrdersListCommand extends Command {

	private static final long serialVersionUID = -8011052320323983859L;

	private static final Logger LOG = Logger
			.getLogger(GetOrdersListCommand.class);

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException,
			AppException {
		LOG.debug("GetOrdersList starts");

		// get order items list
		List<Order> orderList = DBManager.getInstance().findOrders();
		LOG.trace("Found in DB: orderList --> " + orderList);

		// sort orders by id
		Collections.sort(orderList, new Comparator<Order>() {
			public int compare(Order o1, Order o2) {
				return (int) (o1.getId() - o2.getId());
			}
		});

		// put order items list to the request
		request.setAttribute("orderList", orderList);
		LOG.trace("Set the request attribute: orderList --> " + orderList);

		LOG.debug("GetOrdersList finished");
		return Path.PAGE_LIST_ORDER;
	}
}

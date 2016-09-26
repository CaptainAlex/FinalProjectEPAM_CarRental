package ua.nure.yegorov.SummaryTask4.web.command;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import ua.nure.yegorov.SummaryTask4.Path;
import ua.nure.yegorov.SummaryTask4.db.DBManager;
import ua.nure.yegorov.SummaryTask4.db.entity.User;
import ua.nure.yegorov.SummaryTask4.exception.AppException;

/**
 * List users command.
 * 
 * @author A.Yegorov
 *
 */
public class ListUsersCommand extends Command {

	private static final long serialVersionUID = 6523679761308137371L;

	private static final Logger LOG = Logger.getLogger(ListUsersCommand.class);

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException,
			AppException {
		LOG.debug("ListUsersCommand starts");

		List<User> userList = DBManager.getInstance().findUsers();
		LOG.trace("Found in DB: usersList: " + userList);

		request.getSession().setAttribute("userList", userList);
		LOG.trace("Set the request attribute: usersList: " + userList);
		System.out.println(userList);

		LOG.debug("ListUsersCommand finished");
		return Path.PAGE_LIST_USERS;
	}
}

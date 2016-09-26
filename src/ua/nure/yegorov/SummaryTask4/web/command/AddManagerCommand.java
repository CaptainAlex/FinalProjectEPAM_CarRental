package ua.nure.yegorov.SummaryTask4.web.command;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import ua.nure.yegorov.SummaryTask4.Path;
import ua.nure.yegorov.SummaryTask4.db.DBManager;
import ua.nure.yegorov.SummaryTask4.db.Role;
import ua.nure.yegorov.SummaryTask4.db.entity.User;
import ua.nure.yegorov.SummaryTask4.exception.AppException;

/**
 * Add manager command.
 * 
 * @author A.Yegorov
 *
 */
public class AddManagerCommand extends Command {

	private static final long serialVersionUID = 2429350443577661410L;

	private static final Logger LOG = Logger.getLogger(AddManagerCommand.class);

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException,
			AppException {
		LOG.debug("AddManagerCommand starts");
		DBManager manager = DBManager.getInstance();
		HttpSession session = request.getSession();
		User user = new User();

		int idRole = Role.MANAGER.ordinal();
		user.setRoleId(idRole);

		String login = String.valueOf(request.getParameter("login"));
		user.setLogin(login);
		LOG.trace("Request parameter: loging --> " + user.getLogin());
		String password = String.valueOf(request.getParameter("password"));
		user.setPassword(password);
		LOG.trace("Request parameter: password --> " + user.getPassword());

		if (login == null || password == null || login.isEmpty()
				|| password.isEmpty()) {
			String errorMessasge = "Login/password cannot be empty";
			session.setAttribute("errorMessage", errorMessasge);
			LOG.error("Set the session attribute: errorMessage --> "
					+ errorMessasge);
			LOG.debug("AddManagerCommand finished");
			return Path.PAGE_ERROR_PAGE;
		}

		manager.addUser(user);
		LOG.trace("Create new manager --> " + user);

		LOG.debug("AddManagerCommand finished");
		return Path.PAGE_ADMIN;
	}
}

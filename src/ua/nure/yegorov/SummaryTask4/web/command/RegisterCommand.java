package ua.nure.yegorov.SummaryTask4.web.command;

import java.io.IOException;
import java.util.List;

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
 * Register command.
 * 
 * @author A.Yegorov
 *
 */
public class RegisterCommand extends Command {

	private static final long serialVersionUID = -628192664155997686L;

	private static final Logger LOG = Logger.getLogger(RegisterCommand.class);

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException,
			AppException {
		LOG.debug("RegisterCommand starts");
		HttpSession session = request.getSession();
		String forward = Path.PAGE_REGISTER;
		DBManager manager = DBManager.getInstance();

		User user = new User();
		user.setRoleId(Role.CLIENT.ordinal());

		String login = request.getParameter("login");
		user.setLogin(login);
		LOG.trace("Request parameter: loging --> " + user.getLogin());
		String password = request.getParameter("password");
		user.setPassword(password);
		LOG.trace("Request parameter: password --> " + user.getPassword());

		if (login.isEmpty() || password.isEmpty() || login.equals("")
				|| password.equals("")) {
			String errorMessasge = "Login/password cannot be empty";
			session.setAttribute("errorMessage", errorMessasge);
			LOG.error("Set the session attribute: errorMessage --> "
					+ errorMessasge);
			forward = Path.PAGE_ERROR_PAGE;
			LOG.debug("RegisterCommand finished");
			return forward;
		}

		List<User> list = manager.findUsers();
		for (User user2 : list) {
			if (user2.getLogin().equals(login)) {
				String errorMessasge = "Login already exist";
				session.setAttribute("errorMessage", errorMessasge);
				LOG.error("Set the session attribute: errorMessage --> "
						+ errorMessasge);
				forward = Path.PAGE_ERROR_PAGE;
				LOG.debug("RegisterCommand finished");
				return forward;
			}
		}

		manager.addUser(user);
		LOG.trace("Create new user --> " + user);

		session.setAttribute("login", user.getLogin());

		forward = Path.PAGE_LOGIN;
		LOG.debug("RegisterCommand finished");
		return forward;
	}
}

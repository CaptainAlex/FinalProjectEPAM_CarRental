package ua.nure.yegorov.SummaryTask4.web.command;

import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;

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
import ua.nure.yegorov.SummaryTask4.util.DateUtil;

/**
 * Login command.
 * 
 * @author A.Yegorov
 *
 */
public class LoginCommand extends Command {

	private static final long serialVersionUID = -1071012092354190852L;

	private static final Logger LOG = Logger.getLogger(LoginCommand.class);

	private SimpleDateFormat sdf = new SimpleDateFormat(DateUtil.DATE_FORMAT);

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException,
			AppException {
		LOG.debug("LoginCommand starts");
		DBManager manager = DBManager.getInstance();
		String forward = Path.PAGE_ERROR_PAGE;
		HttpSession session = request.getSession();

		User user = null;
		// get login and password form request
		String login = request.getParameter("login");
		LOG.trace("Request parameter: loging --> " + login);
		String password = request.getParameter("password");
		LOG.trace("Request parameter: password --> " + password);

		if (login == null || password == null || login.isEmpty()
				|| password.isEmpty()) {
			String errorMessasge = "Login/password cannot be empty";
			session.setAttribute("errorMessage", errorMessasge);
			LOG.error("Set the session attribute: errorMessage --> "
					+ errorMessasge);
			forward = Path.PAGE_ERROR_PAGE;
			LOG.debug("LoginCommand finished");
			return forward;
		}

		user = manager.findUserByLogin(login);
		LOG.trace("Found in DB: user --> " + user);

		if (user == null || !password.equals(user.getPassword())) {
			String errorMessasge = "Cannot find user with such login/password";
			session.setAttribute("errorMessage", errorMessasge);
			LOG.error("Set the session attribute: errorMessage --> "
					+ errorMessasge);
			forward = Path.PAGE_ERROR_PAGE;
			LOG.debug("LoginCommand finished");
			return forward;
		}

		// if user is blocked
		if (user.isStatus()) {
			String errorMessasge = "You was blocked!";
			session.setAttribute("errorMessage", errorMessasge);
			LOG.error("Set the session attribute: errorMessage --> "
					+ errorMessasge);
			forward = Path.PAGE_ERROR_PAGE;
			LOG.debug("LoginCommand finished");
			return forward;
		}

		// set user config to session
		Role userRole = Role.getRole(user);
		LOG.trace("userRole --> " + userRole);

		if (userRole == Role.ADMIN) {
			forward = Path.PAGE_ADMIN;
		}

		if (userRole == Role.MANAGER) {
			forward = Path.PAGE_MANAGER;
		}

		if (userRole == Role.CLIENT) {
			forward = Path.PAGE_CLIENT;
		}

		Date setCurrentDate = DateUtil.getCurrentDate();
		String setCurrentDateStr = sdf.format(setCurrentDate);
		Date setYearsAfterDate = DateUtil.afterOneYearDate(setCurrentDate);
		String setYearsAfterDateStr = sdf.format(setYearsAfterDate);
		session.setAttribute("currentDate", setCurrentDateStr);
		session.setAttribute("yearsAfterDate", setYearsAfterDateStr);

		session.setAttribute("user", user);
		LOG.trace("Set the session attribute: user --> " + user);

		session.setAttribute("userRole", userRole);
		LOG.trace("Set the session attribute: userRole --> " + userRole);

		session.setAttribute("userId", user.getId());
		LOG.trace("Set the session attribute: userId --> " + user.getId());

		LOG.info("User " + user + " logged as "
				+ userRole.toString().toLowerCase());

		LOG.debug("LoginCommand finished");
		return forward;
	}
}

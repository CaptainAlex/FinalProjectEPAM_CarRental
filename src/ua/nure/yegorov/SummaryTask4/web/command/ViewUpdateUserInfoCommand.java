package ua.nure.yegorov.SummaryTask4.web.command;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import ua.nure.yegorov.SummaryTask4.Path;
import ua.nure.yegorov.SummaryTask4.db.DBManager;
import ua.nure.yegorov.SummaryTask4.exception.AppException;

/**
 * View update user info command.
 * 
 * @author A.Yegorov
 *
 */
public class ViewUpdateUserInfoCommand extends Command {

	private static final long serialVersionUID = -8007538086621939654L;

	private static final Logger LOG = Logger
			.getLogger(ViewUpdateUserInfoCommand.class);

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException,
			AppException {
		LOG.debug("ViewUpdateUserInfoCommand starts");
		DBManager manager = DBManager.getInstance();
		HttpSession session = request.getSession();

		int userId = (int) session.getAttribute("userId");
		try {
			int userInfoId = manager.findUserInfoByUserId(userId).getId();
			LOG.trace("Get parametr: userInfoId --> " + userInfoId);
			LOG.debug("ViewUpdateUserInfoCommand finished");
			return Path.PAGE_UPDATE_CLIENT_INFO;
		} catch (NullPointerException ex) {
			String errorMessasge = "UserInfo in DB not found";
			session.setAttribute("errorMessage", errorMessasge);
			LOG.error("Set the session attribute: errorMessage --> "
					+ errorMessasge);
			LOG.debug("ViewUpdateUserInfoCommand finished");
			return Path.PAGE_ERROR_PAGE;
		}
	}
}

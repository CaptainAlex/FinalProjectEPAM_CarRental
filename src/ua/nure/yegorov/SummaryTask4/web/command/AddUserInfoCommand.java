package ua.nure.yegorov.SummaryTask4.web.command;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import ua.nure.yegorov.SummaryTask4.Path;
import ua.nure.yegorov.SummaryTask4.db.DBManager;
import ua.nure.yegorov.SummaryTask4.db.entity.UserInfo;
import ua.nure.yegorov.SummaryTask4.exception.AppException;

/**
 * Add user info command.
 * 
 * @author A.Yegorov
 *
 */
public class AddUserInfoCommand extends Command {

	private static final long serialVersionUID = 2804659692088333640L;

	private static final Logger LOG = Logger
			.getLogger(AddUserInfoCommand.class);

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException,
			AppException {
		LOG.debug("AddUserInfo starts");
		DBManager manager = DBManager.getInstance();
		HttpSession session = request.getSession();
		UserInfo userInfo = new UserInfo();

		int userId = (int) session.getAttribute("userId");

		userInfo.setUserId(userId);
		LOG.trace("Set parameter: userId --> " + userId);

		String firstName = String.valueOf(request.getParameter("firstName"));
		userInfo.setFirstName(firstName);
		LOG.trace("Request parameter: firstName --> " + userInfo.getFirstName());

		String lastName = String.valueOf(request.getParameter("lastName"));
		userInfo.setLastName(lastName);
		LOG.trace("Request parameter: lastName --> " + userInfo.getLastName());

		String passportNumber = String.valueOf(request
				.getParameter("passportNumber"));
		userInfo.setPassportNumber(passportNumber);
		LOG.trace("Request parameter: passportNumber --> "
				+ userInfo.getPassportNumber());

		String phoneNumber = String
				.valueOf(request.getParameter("phoneNumber"));
		userInfo.setPhoneNumber(phoneNumber);
		LOG.trace("Request parameter: phoneNumber --> "
				+ userInfo.getPhoneNumber());

		String email = String.valueOf(request.getParameter("email"));
		userInfo.setEmail(email);
		LOG.trace("Request parameter: email --> " + userInfo.getEmail());

		if (firstName == null || lastName == null || passportNumber == null
				|| phoneNumber == null || email == null || firstName.isEmpty()
				|| lastName.isEmpty() || passportNumber.isEmpty()
				|| phoneNumber.isEmpty() || email.isEmpty()) {
			String errorMessasge = "empty field";
			session.setAttribute("errorMessage", errorMessasge);
			LOG.error("Set the session attribute: errorMessage --> "
					+ errorMessasge);
			LOG.debug("AddUserInfo finished");
			return Path.PAGE_ERROR_PAGE;
		}

		manager.addUserInfo(userInfo);
		LOG.trace("Create new userInfo --> " + userInfo);

		LOG.debug("AddUserInfo finished");
		return Path.PAGE_ADD_ORDER;
	}
}

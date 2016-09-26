package ua.nure.yegorov.SummaryTask4.web.command;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
 * Client command.
 * 
 * @author A.Yegorov
 *
 */
public class ClientCommand extends Command {

	private static final long serialVersionUID = -7187214861283892786L;

	private static final Logger LOG = Logger.getLogger(ClientCommand.class);

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException,
			AppException {
		LOG.debug("ClientCommand starts");

		DBManager manager = DBManager.getInstance();
		HttpSession session = request.getSession();
		int userId = (int) session.getAttribute("userId");

		UserInfo userInfo = manager.findUserInfoByUserId(userId);
		LOG.trace("Found in DB: userInfo --> " + userInfo);

		List<UserInfo> userInfoList = new ArrayList<UserInfo>();
		if (userInfo == null) {
			userInfoList.clear();
		} else {
			userInfoList.add(userInfo);
		}
		request.setAttribute("userProfile", userInfoList);
		LOG.trace("Set the request attribute: userProfile --> " + userInfoList);

		LOG.debug("ClientCommand finished");
		return Path.PAGE_CLIENT;
	}
}

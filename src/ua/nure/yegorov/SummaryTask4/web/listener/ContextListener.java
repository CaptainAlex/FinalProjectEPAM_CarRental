package ua.nure.yegorov.SummaryTask4.web.listener;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

/**
 * Context listener.
 * 
 * @author A.Yegorov
 * 
 */
public class ContextListener implements ServletContextListener {

	private static final Logger LOG = Logger.getLogger(ContextListener.class);

	public void contextDestroyed(ServletContextEvent event) {
		log("Servlet context destruction starts");
		// no op
		log("Servlet context destruction finished");
	}

	public void contextInitialized(ServletContextEvent event) {
		log("Servlet context initialization starts");

		ServletContext servletContext = event.getServletContext();
		initLog4J(servletContext);

		try {
			initI18n(servletContext);
		} catch (IOException e) {
			log(e.getMessage());
		}

		initCommandContainer();
		log("Servlet context initialization finished");
	}

	/**
	 * Initializes log4j framework.
	 * 
	 * @param servletContext
	 */
	private void initLog4J(ServletContext servletContext) {
		log("Log4J initialization started");
		try {
			PropertyConfigurator.configure(servletContext
					.getRealPath("WEB-INF/log4j.properties"));
			LOG.debug("Log4j has been initialized");
		} catch (Exception ex) {
			log("Cannot configure Log4j");
			log(ex.getMessage());
		}
		log("Log4J initialization finished");
	}

	/**
	 * Initializes i18n framework.
	 * 
	 * @param servletContext
	 * @throws IOException
	 */
	private void initI18n(ServletContext context) throws IOException {
		String localesFileName = context.getInitParameter("locales");

		// obtain reale path on server
		String localesFileRealPath = context.getRealPath(localesFileName);
		FileInputStream fin = null;
		// locad descriptions
		Properties locales = new Properties();
		try {
			fin = new FileInputStream(localesFileRealPath);
			if (fin != null) {
				locales.load(fin);
			}
		} catch (IOException e) {
			log(e.getMessage());
		} finally {
			if (fin != null) {
				fin.close();
			}
		}

		// save descriptions to servlet context
		context.setAttribute("locales", locales);
		locales.list(System.out);
	}

	/**
	 * Initializes CommandContainer.
	 * 
	 * @param servletContext
	 */
	private void initCommandContainer() {

		// initialize commands container
		// just load class to JVM
		try {
			Class.forName("ua.nure.yegorov.SummaryTask4.web.command.CommandContainer");
		} catch (ClassNotFoundException ex) {
			throw new IllegalStateException(
					"Cannot initialize Command Container");
		}
	}

	private void log(String msg) {
		System.out.println("[ContextListener] " + msg);
	}
}
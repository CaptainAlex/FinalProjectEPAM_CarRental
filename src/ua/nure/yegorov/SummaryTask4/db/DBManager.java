package ua.nure.yegorov.SummaryTask4.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.apache.log4j.Logger;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

import ua.nure.yegorov.SummaryTask4.db.bean.UserInfoBean;
import ua.nure.yegorov.SummaryTask4.db.bean.UserOrderBean;
import ua.nure.yegorov.SummaryTask4.db.entity.Car;
import ua.nure.yegorov.SummaryTask4.db.entity.Order;
import ua.nure.yegorov.SummaryTask4.db.entity.User;
import ua.nure.yegorov.SummaryTask4.db.entity.UserInfo;
import ua.nure.yegorov.SummaryTask4.exception.DBException;
import ua.nure.yegorov.SummaryTask4.exception.Messages;
import ua.nure.yegorov.SummaryTask4.util.DateUtil;

/**
 * DB manager. Works with MySQL DB. Only the required DAO methods are defined!
 * 
 * @author A.Yegorov
 *
 */
public final class DBManager {

	private static final Logger LOG = Logger.getLogger(DBManager.class);


	private static DBManager instance;

	public static synchronized DBManager getInstance() throws DBException {
		if (instance == null) {
			instance = new DBManager();
		}
		return instance;
	}

	public static synchronized DBManager getInstance(boolean notPool)
			throws DBException {
		if (instance == null) {
			instance = new DBManager(notPool);
		}
		return instance;
	}

	private DBManager() throws DBException {
		try {
			Context initContext = new InitialContext();
			Context envContext = (Context) initContext.lookup("java:/comp/env");
			ds = (DataSource) envContext.lookup("jdbc/rent_a_car");
			LOG.trace("Data source ==> " + ds);
		} catch (NamingException ex) {
			LOG.error(Messages.ERR_CANNOT_OBTAIN_DATA_SOURCE, ex);
			throw new DBException(Messages.ERR_CANNOT_OBTAIN_DATA_SOURCE, ex);
		}
	}

	private DBManager(boolean notPool) throws DBException {
		if (notPool) {
			MysqlDataSource dataSource = new MysqlDataSource();
			dataSource.setServerName("localhost");
			dataSource.setDatabaseName("rent_a_car");
			dataSource.setUser("root");
			dataSource.setPassword("1sensys1");
			ds = dataSource;
		}
	}

	private DataSource ds;

	// //////////////////////////////////////////////////////////
	// SQL queries
	// //////////////////////////////////////////////////////////

	private static final String SQL_FIND_ALL_USERS = "SELECT * FROM users";

	private static final String SQL_FIND_USER_BY_ID = "SELECT * FROM users WHERE id=?";

	private static final String SQL_FIND_USER_BY_LOGIN = "SELECT * FROM users WHERE login=?";

	private static final String SQL_FIND_USER_INFO_BY_USER_ID = "SELECT * FROM users_info WHERE user_id=?";

	private static final String SQL_FIND_ALL_ORDERS = "SELECT * FROM car_orders";

	private static final String SQL_FIND_ORDERS_BY_USER_ID = "SELECT * FROM car_orders WHERE user_id=?";

	private static final String SQL_FIND_ORDERS_BY_ID = "SELECT * FROM car_orders WHERE id=?";

	private static final String SQL_FIND_ALL_CARS = "SELECT * FROM cars";

	private static final String SQL_FIND_CAR_BY_ID = "SELECT * FROM cars WHERE id=?";

	private static final String SQL_CREATE_NEW_USER = "INSERT INTO users (role_id , login, password) VALUES (?, ?, ?)";

	private static final String SQL_CREATE_NEW_ORDER = "INSERT INTO car_orders (user_id, car_id, driver_status, "
			+ "order_data, return_data, order_price) VALUES (?, ?, ?, ?, ?, ?)";

	private static final String SQL_CREATE_NEW_CAR = "INSERT INTO cars (name, mark, car_class, car_price, car_driver_price) VALUES (?, ?, ?, ?, ?)";

	private static final String SQL_CREATE_NEW_USER_INFO = "INSERT INTO users_info (user_id , first_name, last_name, passport_number, phone_number, email) VALUES (?, ?, ?, ?, ?, ?)";

	private static final String SQL_UPDATE_USER = "UPDATE users SET user_status=? WHERE id=?";

	private static final String SQL_UPDATE_USER_INFO = "UPDATE users_info SET first_name=?, last_name=?, passport_number=?, phone_number=?, email=? WHERE id=?";

	private static final String SQL_UPDATE_CAR_PRICE = "UPDATE cars SET car_price=?, car_driver_price=? WHERE id=?";

	private static final String SQL_UPDATE_ORDER_STATUS = "UPDATE car_orders SET status_id=? WHERE id=?";

	private static final String SQL_UPDATE_ORDER_STATUS_REJ = "UPDATE car_orders SET status_id=?, rejection_reason=? WHERE id=?";

	private static final String SQL_UPDATE_ORDER_RETURN = "UPDATE car_orders SET status_id=?, damage=?, price_for_repairs=? WHERE id=?";

	private static final String SQL_GET_USER_ORDER_BEANS = "SELECT o.id, i.last_name, i.first_name, "
			+ "c.name, o.driver_status, o.order_data, o.return_data, o.order_price, "
			+ "s.name AS status_name, o.rejection_reason, o.damage, o.price_for_repairs "
			+ "FROM car_orders o, cars c, statuses s,  users_info i WHERE o.user_id=i.user_id "
			+ "AND o.car_id=c.id AND o.status_id=s.id;";

	private static final String SQL_GET_USER_ORDER_BEANS_BY_USER_ID = "SELECT o.id, i.last_name, i.first_name, "
			+ "c.name, o.driver_status, o.order_data, o.return_data, o.order_price, "
			+ "s.name AS status_name, o.rejection_reason, o.damage, o.price_for_repairs "
			+ "FROM car_orders o, cars c, statuses s,  users_info i WHERE o.user_id=i.user_id "
			+ "AND o.car_id=c.id AND o.status_id=s.id AND o.user_id=?;";

	private static final String SQL_GET_USER_INFO_BEANS = "SELECT u.id, u.login, u.password, "
			+ "i.first_name, i.last_name, i.passport_number, i.phone_number, i.email, "
			+ "u.user_status FROM users u, users_info i WHERE i.user_id=u.id;";

	private static final String SQL_DELETE_CAR_BY_ID = "DELETE FROM cars WHERE id=?";

	private static final String SQL_GET_CARS_IN_RENT = "select c.id, c.name, c.mark, c.car_class, c.car_price, "
			+ "c.car_driver_price  from cars c, car_orders o where c.id=o.car_id and "
			+ "(o.status_id=1 or o.status_id=3 or o.status_id=0);";

	/**
	 * Returns a DB connection from the Pool Connections. Before using this
	 * method you must configure the Date Source and the Connections Pool in
	 * your WEB_APP_ROOT/META-INF/context.xml file.
	 * 
	 * @return DB connection.
	 * @throws DBException
	 */

	public Connection getConnection() throws DBException {
		Connection con = null;
		try {
			con = ds.getConnection();
		} catch (SQLException ex) {
			LOG.error(Messages.ERR_CANNOT_OBTAIN_CONNECTION, ex);
			throw new DBException(Messages.ERR_CANNOT_OBTAIN_CONNECTION, ex);
		}
		return con;
	}

	// //////////////////////////////////////////////////////////
	// Methods to obtain beans
	// //////////////////////////////////////////////////////////

	/**
	 * Returns all UserOrderBean items.
	 * 
	 * @return List of UserOrderBean item entities.
	 * @throws DBException
	 */
	public List<UserOrderBean> getUserOrderBeans() throws DBException {
		List<UserOrderBean> orderUserBeanList = new ArrayList<UserOrderBean>();
		Statement stmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = getConnection();
			stmt = con.createStatement();
			rs = stmt.executeQuery(SQL_GET_USER_ORDER_BEANS);
			while (rs.next()) {
				orderUserBeanList.add(extractUserOrderBean(rs));
			}
			con.commit();
		} catch (SQLException ex) {
			rollback(con);
			LOG.error(Messages.ERR_CANNOT_OBTAIN_USER_ORDER_BEANS, ex);
			throw new DBException(Messages.ERR_CANNOT_OBTAIN_USER_ORDER_BEANS,
					ex);
		} finally {
			close(con, stmt, rs);
		}
		return orderUserBeanList;
	}

	/**
	 * Returns a UserOrderBean with the given user id.
	 * 
	 * @param userId
	 *            user id.
	 * @return List of UserOrderBean item entities.
	 * @throws DBException
	 */
	public List<UserOrderBean> getUserOrderBeansByUserId(int userId)
			throws DBException {
		List<UserOrderBean> orderUserBeanList = new ArrayList<UserOrderBean>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = getConnection();
			pstmt = con.prepareStatement(SQL_GET_USER_ORDER_BEANS_BY_USER_ID);
			pstmt.setInt(1, userId);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				orderUserBeanList.add(extractUserOrderBean(rs));
			}
			con.commit();
		} catch (SQLException ex) {
			rollback(con);
			LOG.error(Messages.ERR_CANNOT_OBTAIN_USER_ORDER_BEANS, ex);
			throw new DBException(Messages.ERR_CANNOT_OBTAIN_USER_ORDER_BEANS,
					ex);
		} finally {
			close(con, pstmt, rs);
		}
		return orderUserBeanList;
	}

	/**
	 * Returns all UserInfoBean items.
	 * 
	 * @return List of UserInfoBean item entities.
	 * @throws DBException
	 */
	public List<UserInfoBean> getUserInfoBeans() throws DBException {
		List<UserInfoBean> infoUserBeanList = new ArrayList<UserInfoBean>();
		Statement stmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = getConnection();
			stmt = con.createStatement();
			rs = stmt.executeQuery(SQL_GET_USER_INFO_BEANS);
			while (rs.next()) {
				infoUserBeanList.add(extractUserInfoBean(rs));
			}
			con.commit();
		} catch (SQLException ex) {
			rollback(con);
			LOG.error(Messages.ERR_CANNOT_OBTAIN_USER_INFO_BEANS, ex);
			throw new DBException(Messages.ERR_CANNOT_OBTAIN_USER_INFO_BEANS,
					ex);
		} finally {
			close(con, stmt, rs);
		}
		return infoUserBeanList;
	}

	// //////////////////////////////////////////////////////////
	// Entity access methods
	// //////////////////////////////////////////////////////////

	/**
	 * Returns all cars.
	 * 
	 * @return List of car entities.
	 * @throws DBException
	 */
	public List<Car> findCars() throws DBException {
		List<Car> carList = new ArrayList<Car>();
		Statement stmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = getConnection();
			stmt = con.createStatement();
			rs = stmt.executeQuery(SQL_FIND_ALL_CARS);
			while (rs.next()) {
				carList.add(extractCar(rs));
			}
			con.commit();
		} catch (SQLException ex) {
			rollback(con);
			LOG.error(Messages.ERR_CANNOT_OBTAIN_CARS, ex);
			throw new DBException(Messages.ERR_CANNOT_OBTAIN_CARS, ex);
		} finally {
			close(con, stmt, rs);
		}
		return carList;
	}

	/**
	 * Returns a car with the given identifier.
	 * 
	 * @param id
	 *            Car identifier.
	 * @return Car entity.
	 * @throws DBException
	 */
	public Car findCarById(int id) throws DBException {
		Car car = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = getConnection();
			pstmt = con.prepareStatement(SQL_FIND_CAR_BY_ID);
			pstmt.setInt(1, id);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				car = extractCar(rs);
			}
			con.commit();
		} catch (SQLException ex) {
			rollback(con);
			throw new DBException(Messages.ERR_CANNOT_OBTAIN_CAR_BY_ID, ex);
		} finally {
			close(con, pstmt, rs);
		}
		return car;
	}

	/**
	 * Returns cars in rent.
	 * 
	 * @return List of car entities.
	 * @throws DBException
	 */
	public List<Car> findCarsInRent() throws DBException {
		List<Car> carList = new ArrayList<Car>();
		Statement stmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = getConnection();
			stmt = con.createStatement();
			rs = stmt.executeQuery(SQL_GET_CARS_IN_RENT);
			while (rs.next()) {
				carList.add(extractCar(rs));
			}
			con.commit();
		} catch (SQLException ex) {
			rollback(con);
			LOG.error(Messages.ERR_CANNOT_OBTAIN_CARS, ex);
			throw new DBException(Messages.ERR_CANNOT_OBTAIN_CARS, ex);
		} finally {
			close(con, stmt, rs);
		}
		return carList;
	}

	/**
	 * Returns available cars.
	 * 
	 * @return List of car entities.
	 * @throws DBException
	 */
	public List<Car> findAvailableCars() throws DBException {
		List<Car> allCars = findCars();
		List<Car> carsInRent = findCarsInRent();

		int[] masId = new int[carsInRent.size()];

		for (int i = 0; i < masId.length; i++) {
			masId[i] = carsInRent.get(i).getId();
		}

		for (int i = 0; i < masId.length; i++) {
			for (int j = 0; j < allCars.size(); j++) {
				if (masId[i] == allCars.get(j).getId()) {
					allCars.remove(j);
				}
			}
		}
		return allCars;
	}

	/**
	 * Returns all orders.
	 * 
	 * @return List of order entities.
	 * @throws DBException
	 */
	public List<Order> findOrders() throws DBException {
		List<Order> ordersList = new ArrayList<Order>();
		Statement stmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = getConnection();
			stmt = con.createStatement();
			rs = stmt.executeQuery(SQL_FIND_ALL_ORDERS);
			while (rs.next()) {
				ordersList.add(extractOrder(rs));
			}
			con.commit();
		} catch (SQLException ex) {
			rollback(con);
			throw new DBException(Messages.ERR_CANNOT_OBTAIN_ORDERS, ex);
		} finally {
			close(con, stmt, rs);
		}
		return ordersList;
	}

	/**
	 * Returns orders with the given identifier.
	 * 
	 * @param userId
	 *            User identifier.
	 * @return List of order entities.
	 * @throws DBException
	 */
	public List<Order> findOrdersByUserId(int userId) throws DBException {
		List<Order> ordersList = new ArrayList<Order>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = getConnection();
			pstmt = con.prepareStatement(SQL_FIND_ORDERS_BY_USER_ID);
			pstmt.setInt(1, userId);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				ordersList.add(extractOrder(rs));
			}
			con.commit();
		} catch (SQLException ex) {
			rollback(con);
			throw new DBException(Messages.ERR_CANNOT_OBTAIN_ORDERS_BY_USER_ID,
					ex);
		} finally {
			close(con, pstmt, rs);
		}
		return ordersList;
	}

	/**
	 * Returns orders with the given identifier.
	 * 
	 * @param id
	 *            Order identifier.
	 * @return Order entity.
	 * @throws DBException
	 */
	public Order findOrderById(int id) throws DBException {
		Order order = new Order();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = getConnection();
			pstmt = con.prepareStatement(SQL_FIND_ORDERS_BY_ID);
			pstmt.setLong(1, id);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				order = extractOrder(rs);
			}
			con.commit();
		} catch (SQLException ex) {
			rollback(con);
			throw new DBException(Messages.ERR_CANNOT_OBTAIN_ORDER_BY_ID, ex);
		} finally {
			close(con, pstmt, rs);
		}
		return order;
	}

	/**
	 * Returns all users.
	 * 
	 * @return List of user entities.
	 * @throws DBException
	 */
	public List<User> findUsers() throws DBException {
		List<User> userList = new ArrayList<User>();
		Statement stmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = getConnection();
			stmt = con.createStatement();
			rs = stmt.executeQuery(SQL_FIND_ALL_USERS);
			while (rs.next()) {
				userList.add(extractUser(rs));
			}
			con.commit();
		} catch (SQLException ex) {
			rollback(con);
			LOG.error(Messages.ERR_CANNOT_OBTAIN_USERS, ex);
			throw new DBException(Messages.ERR_CANNOT_OBTAIN_USERS, ex);
		} finally {
			close(con, stmt, rs);
		}
		return userList;
	}

	/**
	 * Returns a user with the given identifier.
	 * 
	 * @param id
	 *            User identifier.
	 * @return User entity.
	 * @throws DBException
	 */
	public User findUserById(int id) throws DBException {
		User user = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = getConnection();
			pstmt = con.prepareStatement(SQL_FIND_USER_BY_ID);
			pstmt.setLong(1, id);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				user = extractUser(rs);
			}
			con.commit();
		} catch (SQLException ex) {
			rollback(con);
			throw new DBException(Messages.ERR_CANNOT_OBTAIN_USER_BY_ID, ex);
		} finally {
			close(con, pstmt, rs);
		}
		return user;
	}

	/**
	 * Returns a user with the given login.
	 * 
	 * @param login
	 *            User login.
	 * @return User entity.
	 * @throws DBException
	 */
	public User findUserByLogin(String login) throws DBException {
		User user = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = getConnection();
			pstmt = con.prepareStatement(SQL_FIND_USER_BY_LOGIN);
			pstmt.setString(1, login);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				user = extractUser(rs);
			}
			con.commit();
		} catch (SQLException ex) {
			rollback(con);
			throw new DBException(Messages.ERR_CANNOT_OBTAIN_USER_BY_LOGIN, ex);
		} finally {
			close(con, pstmt, rs);
		}
		return user;
	}

	/**
	 * Returns a user info with the given identifier.
	 * 
	 * @param id
	 *            User identifier.
	 * @return UserInfo entity.
	 * @throws DBException
	 */
	public UserInfo findUserInfoByUserId(int id) throws DBException {
		UserInfo userInfo = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = getConnection();
			pstmt = con.prepareStatement(SQL_FIND_USER_INFO_BY_USER_ID);
			pstmt.setLong(1, id);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				userInfo = extractUserInfo(rs);
			}
			con.commit();
		} catch (SQLException ex) {
			rollback(con);
			throw new DBException(Messages.ERR_CANNOT_OBTAIN_USER_INFO_BY_ID,
					ex);
		} finally {
			close(con, pstmt, rs);
		}
		return userInfo;
	}

	/**
	 * Ñreates an entry for the new user in the database. Returns user with
	 * generated userID.
	 * 
	 * @param u
	 *            new User().
	 * @return User entity.
	 * @throws DBException
	 */
	public User addUser(User u) throws DBException {
		User user = u;
		PreparedStatement pstmt = null;
		Connection con = null;
		try {
			con = getConnection();
			pstmt = con.prepareStatement(SQL_CREATE_NEW_USER);
			pstmt.setInt(1, user.getRoleId());
			pstmt.setString(2, user.getLogin());
			pstmt.setString(3, user.getPassword());
			pstmt.executeUpdate();
		} catch (SQLException ex) {
			rollback(con);
			LOG.error(Messages.ERR_CANNOT_CREATE_USER, ex);
		} finally {
			close(pstmt);
			commitAndCloseConnection(con);
		}
		return user;
	}

	/**
	 * Ñreates an entry for the new car in the database. Returns user with
	 * generated carID.
	 * 
	 * @param c
	 *            new Car().
	 * @return Car entity.
	 * @throws DBException
	 */
	public Car addCar(Car c) throws DBException {
		Car car = c;
		PreparedStatement pstmt = null;
		Connection con = null;
		try {
			con = getConnection();
			pstmt = con.prepareStatement(SQL_CREATE_NEW_CAR);
			pstmt.setString(1, car.getName());
			pstmt.setString(2, car.getMark());
			pstmt.setString(3, car.getCarClass());
			pstmt.setInt(4, car.getCarPrice());
			pstmt.setInt(5, car.getCarDriverPrice());
			pstmt.executeUpdate();
		} catch (SQLException ex) {
			rollback(con);
			LOG.error(Messages.ERR_CANNOT_CREATE_CAR, ex);
		} finally {
			close(pstmt);
			commitAndCloseConnection(con);
		}
		return car;
	}

	/**
	 * Ñreates an entry for the new userInfo in the database. Returns user with
	 * generated userInfoID.
	 * 
	 * @param u
	 *            new UserInfo().
	 * @return UserInfo entity.
	 * @throws DBException
	 */
	public UserInfo addUserInfo(UserInfo u) throws DBException {
		UserInfo userInfo = u;
		PreparedStatement pstmt = null;
		Connection con = null;
		try {
			con = getConnection();
			pstmt = con.prepareStatement(SQL_CREATE_NEW_USER_INFO);
			pstmt.setInt(1, userInfo.getUserId());
			pstmt.setString(2, userInfo.getFirstName());
			pstmt.setString(3, userInfo.getLastName());
			pstmt.setString(4, userInfo.getPassportNumber());
			pstmt.setString(5, userInfo.getPhoneNumber());
			pstmt.setString(6, userInfo.getEmail());

			pstmt.executeUpdate();
		} catch (SQLException ex) {
			rollback(con);
			LOG.error(Messages.ERR_CANNOT_CREATE_CAR, ex);
		} finally {
			close(pstmt);
			commitAndCloseConnection(con);
		}
		return userInfo;
	}

	/**
	 * Ñreates an entry for the new order in the database. Returns user with
	 * generated orderID.
	 * 
	 * @param o
	 *            new Order().
	 * @return Order entity.
	 * @throws DBException
	 */
	public Order addOrder(Order o) throws DBException {
		Order order = o;
		PreparedStatement pstmt = null;
		Connection con = null;
		try {
			con = getConnection();
			pstmt = con.prepareStatement(SQL_CREATE_NEW_ORDER);
			pstmt.setInt(1, order.getUserId());
			pstmt.setInt(2, order.getCarId());
			pstmt.setBoolean(3, order.isDriverStatus());
			pstmt.setDate(4, order.getOrderData());
			pstmt.setDate(5, order.getReturnData());

			int days = (int) DateUtil.fullDaysBetweenDates(
					order.getOrderData(), order.getReturnData());
			int price = calc(order) * days;

			pstmt.setInt(6, price);
			pstmt.executeUpdate();
		} catch (SQLException ex) {
			rollback(con);
			LOG.error(Messages.ERR_CANNOT_CREATE_ORDER, ex);
		} finally {
			close(pstmt);
			commitAndCloseConnection(con);
		}
		return order;
	}

	/**
	 * Calculates the price of received order.
	 * 
	 * @param order
	 *            Order entity.
	 * @return Order price.
	 * @throws DBException
	 */
	private int calc(Order order) throws DBException {
		DBManager manager = DBManager.getInstance();
		int price = 0;
		Car car = manager.findCarById(order.getCarId());
		if (order.isDriverStatus()) {
			price = car.getCarDriverPrice();
		} else {
			price = car.getCarPrice();
		}
		return price;
	}

	/**
	 * Updates the record of user.
	 * 
	 * @param user
	 *            User entity.
	 * @throws DBException
	 */
	public void updateUserStatus(User user) throws DBException {
		Connection con = null;
		try {
			con = getConnection();
			updateUserStatus(con, user);
			con.commit();
		} catch (SQLException ex) {
			rollback(con);
			throw new DBException(Messages.ERR_CANNOT_UPDATE_USER, ex);
		} finally {
			close(con);
		}
	}

	/**
	 * Updates the record of user info.
	 * 
	 * @param userInfo
	 *            UserInfo entity.
	 * @throws DBException
	 */
	public void updateUserInfo(UserInfo userInfo) throws DBException {
		Connection con = null;
		try {
			con = getConnection();
			updateUserInfo(con, userInfo);
			con.commit();
		} catch (SQLException ex) {
			rollback(con);
			throw new DBException(Messages.ERR_CANNOT_UPDATE_USER_INFO, ex);
		} finally {
			close(con);
		}
	}

	/**
	 * Updates the record of order status.
	 * 
	 * @param order
	 *            Order entity.
	 * @throws DBException
	 */
	public void updateOrderStatus(Order order) throws DBException {
		Connection con = null;
		try {
			con = getConnection();
			updateOrderStatus(con, order);
			con.commit();
		} catch (SQLException ex) {
			rollback(con);
			throw new DBException(Messages.ERR_CANNOT_UPDATE_ORDER_STATUS, ex);
		} finally {
			close(con);
		}
	}

	/**
	 * Updates the record of order (status and rejection reason).
	 * 
	 * @param order
	 *            Order entity.
	 * @throws DBException
	 */
	public void updateOrderStatusRej(Order order) throws DBException {
		Connection con = null;
		try {
			con = getConnection();
			updateOrderStatusRej(con, order);
			con.commit();
		} catch (SQLException ex) {
			rollback(con);
			throw new DBException(Messages.ERR_CANNOT_UPDATE_ORDER_STATUS, ex);
		} finally {
			close(con);
		}
	}

	/**
	 * Updates the record of order (status, damage and price for repairs).
	 * 
	 * @param order
	 *            Order entity.
	 * @throws DBException
	 */
	public void updateOrderReturn(Order order) throws DBException {
		Connection con = null;
		try {
			con = getConnection();
			updateOrderReturn(con, order);
			con.commit();
		} catch (SQLException ex) {
			rollback(con);
			throw new DBException(Messages.ERR_CANNOT_UPDATE_ORDER_STATUS, ex);
		} finally {
			close(con);
		}
	}

	/**
	 * Updates the record of car price.
	 * 
	 * @param car
	 *            Car entity.
	 * @throws DBException
	 */
	public void updateCarPrice(Car car) throws DBException {
		Connection con = null;
		try {
			con = getConnection();
			updateCarPrice(con, car);
			con.commit();
		} catch (SQLException ex) {
			rollback(con);
			throw new DBException(Messages.ERR_CANNOT_UPDATE_CAR, ex);
		} finally {
			close(con);
		}
	}

	/**
	 * Delete an entry for the car in the database.
	 * 
	 * @param id
	 *            Id car to be removed.
	 * @return Returns true if success.
	 * @throws DBException
	 */
	public boolean deleteCarById(int id) throws DBException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = getConnection();
			pstmt = con.prepareStatement(SQL_DELETE_CAR_BY_ID);
			pstmt.setInt(1, id);
			int count = pstmt.executeUpdate();

			if (count < 1) {
				return false;
			}
			con.commit();
		} catch (SQLException ex) {
			rollback(con);
			throw new DBException(Messages.ERR_CANNOT_DELETE_CAR_BY_ID, ex);
		} finally {
			close(con, pstmt, rs);
		}
		return true;
	}

	// //////////////////////////////////////////////////////////
	// Entity access methods (for transactions)
	// //////////////////////////////////////////////////////////

	/**
	 * Update user status.
	 * 
	 * @param user
	 *            User to update.
	 * @throws SQLException
	 */
	private void updateUserStatus(Connection con, User user)
			throws SQLException {
		PreparedStatement pstmt = null;
		try {
			pstmt = con.prepareStatement(SQL_UPDATE_USER);
			pstmt.setBoolean(1, user.isStatus());
			pstmt.setInt(2, user.getId());
			pstmt.executeUpdate();
		} finally {
			close(pstmt);
		}
	}

	/**
	 * Update user info.
	 * 
	 * @param userInfo
	 *            UserInfo to update.
	 * @throws SQLException
	 */
	private void updateUserInfo(Connection con, UserInfo userInfo)
			throws SQLException {
		PreparedStatement pstmt = null;
		try {
			pstmt = con.prepareStatement(SQL_UPDATE_USER_INFO);
			int k = 1;
			pstmt.setString(k++, userInfo.getFirstName());
			pstmt.setString(k++, userInfo.getLastName());
			pstmt.setString(k++, userInfo.getPassportNumber());
			pstmt.setString(k++, userInfo.getPhoneNumber());
			pstmt.setString(k++, userInfo.getEmail());
			pstmt.setInt(k, userInfo.getId());
			pstmt.executeUpdate();
		} finally {
			close(pstmt);
		}
	}

	/**
	 * Update order status.
	 * 
	 * @param order
	 *            Order to update.
	 * @throws SQLException
	 */
	private void updateOrderStatus(Connection con, Order order)
			throws SQLException {
		PreparedStatement pstmt = null;
		try {
			pstmt = con.prepareStatement(SQL_UPDATE_ORDER_STATUS);
			pstmt.setInt(1, order.getStatusId());
			pstmt.setInt(2, order.getId());
			pstmt.executeUpdate();
		} finally {
			close(pstmt);
		}
	}

	/**
	 * Update order (status and rejection reason).
	 * 
	 * @param order
	 *            Order to update.
	 * @throws SQLException
	 */
	private void updateOrderStatusRej(Connection con, Order order)
			throws SQLException {
		PreparedStatement pstmt = null;
		try {
			pstmt = con.prepareStatement(SQL_UPDATE_ORDER_STATUS_REJ);
			pstmt.setInt(1, order.getStatusId());
			pstmt.setString(2, order.getRejectionReason());
			pstmt.setInt(3, order.getId());
			pstmt.executeUpdate();
		} finally {
			close(pstmt);
		}
	}

	/**
	 * Update order (status, damage and price for repairs).
	 * 
	 * @param order
	 *            Order to update.
	 * @throws SQLException
	 */
	private void updateOrderReturn(Connection con, Order order)
			throws SQLException {
		PreparedStatement pstmt = null;
		try {
			pstmt = con.prepareStatement(SQL_UPDATE_ORDER_RETURN);
			int k = 1;
			pstmt.setInt(k++, order.getStatusId());
			pstmt.setBoolean(k++, order.isDamage());
			pstmt.setInt(k++, order.getPriceForRepairs());
			pstmt.setInt(k, order.getId());
			pstmt.executeUpdate();
		} finally {
			close(pstmt);
		}
	}

	/**
	 * Update car price.
	 * 
	 * @param order
	 *            Car to update.
	 * @throws SQLException
	 */
	private void updateCarPrice(Connection con, Car car) throws SQLException {
		PreparedStatement pstmt = null;
		try {
			pstmt = con.prepareStatement(SQL_UPDATE_CAR_PRICE);
			int k = 1;
			pstmt.setInt(k++, car.getCarPrice());
			pstmt.setInt(k++, car.getCarDriverPrice());
			pstmt.setInt(k, car.getId());
			pstmt.executeUpdate();
		} finally {
			close(pstmt);
		}
	}

	// //////////////////////////////////////////////////////////
	// DB util methods
	// //////////////////////////////////////////////////////////

	/**
	 * Closes a connection.
	 * 
	 * @param con
	 *            Connection to be closed.
	 */
	private void close(Connection con) {
		if (con != null) {
			try {
				con.close();
			} catch (SQLException ex) {
				LOG.error(Messages.ERR_CANNOT_CLOSE_CONNECTION, ex);
			}
		}
	}

	/**
	 * Closes a statement object.
	 * 
	 * @param stmt
	 *            Statement to be closed.
	 */
	private void close(Statement stmt) {
		if (stmt != null) {
			try {
				stmt.close();
			} catch (SQLException ex) {
				LOG.error(Messages.ERR_CANNOT_CLOSE_STATEMENT, ex);
			}
		}
	}

	/**
	 * Closes a result set object.
	 * 
	 * @param rs
	 *            ResultSet to be closed.
	 */
	private void close(ResultSet rs) {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException ex) {
				LOG.error(Messages.ERR_CANNOT_CLOSE_RESULTSET, ex);
			}
		}
	}

	/**
	 * Commit and closes a connection.
	 * 
	 * @param connection
	 *            Connection to be closed.
	 */
	private void commitAndCloseConnection(Connection connection) {
		if (connection != null) {
			try {
				connection.commit();
				connection.close();
			} catch (SQLException ex) {
				LOG.error("Cannot close connection", ex);
			}
		}
	}

	/**
	 * Closes resources.
	 * 
	 * @param con
	 *            Connection to be closed.
	 * @param stmt
	 *            Statement to be closed.
	 * @param rs
	 *            ResultSet to be closed.
	 */
	private void close(Connection con, Statement stmt, ResultSet rs) {
		close(rs);
		close(stmt);
		close(con);
	}

	/**
	 * Rollbacks a connection.
	 * 
	 * @param con
	 *            Connection to be rollbacked.
	 */
	private void rollback(Connection con) {
		if (con != null) {
			try {
				con.rollback();
			} catch (SQLException ex) {
				LOG.error("Cannot rollback transaction", ex);
			}
		}
	}

	// //////////////////////////////////////////////////////////
	// Other methods
	// //////////////////////////////////////////////////////////

	/**
	 * Extracts a user order bean from the result set.
	 * 
	 * @param rs
	 *            Result set from which a user order bean will be extracted.
	 * @return UserOrderBean object
	 * @throws SQLException
	 */
	private UserOrderBean extractUserOrderBean(ResultSet rs)
			throws SQLException {
		UserOrderBean bean = new UserOrderBean();
		bean.setId(rs.getInt(Fields.USER_ORDER_BEAN_ORDER_ID));
		bean.setUserLastName(rs
				.getString(Fields.USER_ORDER_BEAN_USER_LAST_NAME));
		bean.setUserFirstName(rs
				.getString(Fields.USER_ORDER_BEAN_USER_FIRST_NAME));
		bean.setCarName(rs.getString(Fields.USER_ORDER_BEAN_CAR_NAME));
		bean.setDriverStatus(rs
				.getBoolean(Fields.USER_ORDER_BEAN_DRIVER_STATUS));
		bean.setOrderData(rs.getDate(Fields.USER_ORDER_BEAN_DATA_ORDER));
		bean.setReturnData(rs.getDate(Fields.USER_ORDER_BEAN_DATA_RETURN));
		bean.setOrderPrice(rs.getInt(Fields.USER_ORDER_BEAN_ORDER_PRICE));
		bean.setStatusName(rs.getString(Fields.USER_ORDER_BEAN_STATUS_NAME));
		bean.setRejectionReason(rs
				.getString(Fields.USER_ORDER_BEAN_REJECTION_REASON));
		bean.setDamage(rs.getBoolean(Fields.USER_ORDER_BEAN_DAMAGE));
		bean.setPriceForRepairs(rs
				.getInt(Fields.USER_ORDER_BEAN_PRICE_FOR_REPAIRS));
		return bean;
	}

	/**
	 * Extracts a user info bean from the result set.
	 * 
	 * @param rs
	 *            Result set from which a user info bean will be extracted.
	 * @return UserInfoBean object
	 * @throws SQLException
	 */
	private UserInfoBean extractUserInfoBean(ResultSet rs) throws SQLException {
		UserInfoBean bean = new UserInfoBean();
		bean.setId(rs.getInt(Fields.USER_INFO_BEAN_USER_ID));
		bean.setLogin(rs.getString(Fields.USER_INFO_BEAN_LOGIN));
		bean.setPassword(rs.getString(Fields.USER_INFO_BEAN_PASSWORD));
		bean.setFirstName(rs.getString(Fields.USER_INFO_BEAN_FIRST_NAME));
		bean.setLastName(rs.getString(Fields.USER_INFO_BEAN_LAST_NAME));
		bean.setPassportNumber(rs
				.getString(Fields.USER_INFO_BEAN_PASSPORT_NUMBER));
		bean.setPhoneNumber(rs.getString(Fields.USER_INFO_BEAN_PHONE_NUMBER));
		bean.setEmail(rs.getString(Fields.USER_INFO_BEAN_EMAIL));
		bean.setStatus(rs.getBoolean(Fields.USER_INFO_BEAN_STATUS));

		return bean;
	}

	/**
	 * Extracts a user entity from the result set.
	 * 
	 * @param rs
	 *            Result set from which a user entity will be extracted.
	 * @return User entity.
	 * @throws SQLException
	 */
	private User extractUser(ResultSet rs) throws SQLException {
		User user = new User();
		user.setId(rs.getInt(Fields.ENTITY_ID));
		user.setRoleId(rs.getInt(Fields.USER_ROLE_ID));
		user.setLogin(rs.getString(Fields.USER_LOGIN));
		user.setPassword(rs.getString(Fields.USER_PASSWORD));
		user.setStatus(rs.getBoolean(Fields.USER_STATUS));

		return user;
	}

	/**
	 * Extracts a car entity from the result set.
	 * 
	 * @param rs
	 *            Result set from which a car entity will be extracted.
	 * @return Car entity.
	 * @throws SQLException
	 */
	private Car extractCar(ResultSet rs) throws SQLException {
		Car car = new Car();
		car.setId(rs.getInt(Fields.ENTITY_ID));
		car.setName(rs.getString(Fields.CAR_NAME));
		car.setMark(rs.getString(Fields.CAR_MARK));
		car.setCarClass(rs.getString(Fields.CAR_CLASS));
		car.setCarPrice(rs.getInt(Fields.CAR_PRICE));
		car.setCarDriverPrice(rs.getInt(Fields.CAR_DRIVER_PRICE));

		return car;
	}

	/**
	 * Extracts a car entity from the result set.
	 * 
	 * @param rs
	 *            Result set from which a car entity will be extracted.
	 * @return Car entity.
	 * @throws SQLException
	 */
	private Order extractOrder(ResultSet rs) throws SQLException {
		Order order = new Order();
		order.setId(rs.getInt(Fields.ENTITY_ID));
		order.setUserId(rs.getInt(Fields.ORDER_USER_ID));
		order.setCarId(rs.getInt(Fields.ORDER_CAR_ID));
		order.setDriverStatus(rs.getBoolean(Fields.ORDER_DRIVER_STATUS));
		order.setOrderData(rs.getDate(Fields.ORDER_DATA_ORDER));
		order.setReturnData(rs.getDate(Fields.ORDER_DATA_RETURN));
		order.setOrderPrice(rs.getInt(Fields.ORDER_PRICE));
		order.setStatusId(rs.getInt(Fields.ORDER_STATUS_ID));
		order.setRejectionReason(rs.getString(Fields.ORDER_REJECTION_REASON));
		order.setDamage(rs.getBoolean(Fields.ORDER_DAMAGE));
		order.setPriceForRepairs(rs.getInt(Fields.ORDER_PRICE_FOR_REPAIRS));

		return order;
	}

	/**
	 * Extracts a user info entity from the result set.
	 * 
	 * @param rs
	 *            Result set from which a user info entity will be extracted.
	 * @return UserInfo entity.
	 * @throws SQLException
	 */
	private UserInfo extractUserInfo(ResultSet rs) throws SQLException {
		UserInfo userinfo = new UserInfo();
		userinfo.setId(rs.getInt(Fields.ENTITY_ID));
		userinfo.setUserId(rs.getInt(Fields.USER_INFO_USER_ID));
		userinfo.setFirstName(rs.getString(Fields.USER_INFO_FIRST_NAME));
		userinfo.setLastName(rs.getString(Fields.USER_INFO_LAST_NAME));
		userinfo.setPassportNumber(rs
				.getString(Fields.USER_INFO_PASSPORT_NUMBER));
		userinfo.setPhoneNumber(rs.getString(Fields.USER_INFO_PHONE_NUMBER));
		userinfo.setEmail(rs.getString(Fields.USER_INFO_EMAIL));

		return userinfo;
	}
}

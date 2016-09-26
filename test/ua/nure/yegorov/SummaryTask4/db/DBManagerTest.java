package ua.nure.yegorov.SummaryTask4.db;

import static org.junit.Assert.*;

import java.io.File;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;

import org.apache.log4j.PropertyConfigurator;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import ua.nure.yegorov.SummaryTask4.db.entity.Car;
import ua.nure.yegorov.SummaryTask4.db.entity.Order;
import ua.nure.yegorov.SummaryTask4.db.entity.User;
import ua.nure.yegorov.SummaryTask4.db.entity.UserInfo;
import ua.nure.yegorov.SummaryTask4.exception.DBException;
import ua.nure.yegorov.SummaryTask4.util.DateUtil;

public class DBManagerTest {

//	@Before
//	public void setUp() {
//		try {
//			File file = new File("WebContent/WEB-INF/log4j.properties");
//			PropertyConfigurator.configure(file.getAbsolutePath());
//		} catch (Exception ex) {
//			log("Cannot configure Log4j");
//			ex.printStackTrace();
//		}
//	}

	@Test
	public final void testConstructor() throws DBException, SQLException {
		DBManager manager = DBManager.getInstance(true);
		Connection con = manager.getConnection();
		con.close();
	}

	@Test
	public void testGetConnection() throws DBException, SQLException {
		DBManager manager = DBManager.getInstance(true);
		Connection con = manager.getConnection();
		con.close();
	}

//	@Test
//	public void testGetUserOrderBeans() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testGetUserOrderBeansByUserId() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testGetUserInfoBeans() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testFindCars() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testFindCarById() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testFindCarsInRent() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testFindAvailableCars() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testFindOrders() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testFindOrdersByStatus() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testFindOrdersByUserId() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testFindOrderById() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testFindOrdersByCarId() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testFindUsers() {
//		fail("Not yet implemented");
//	}

//	@Test
//	public void testFindUserById() throws DBException {
//		DBManager manager = DBManager.getInstance(true);
//		boolean result = true;
//		User user = new User();
//		user.setId(1);
//		user.setRoleId(0);
//		user.setLogin("admin");
//		user.setPassword("admin");
//		user = manager.addUser(user);
//		if(manager.findUserById(user.getId()) == null){
//			result = false;
//		}
//		
//	
//		Assert.assertTrue(result);
//	}

//	@Test
//	public void testFindUserByLogin() throws DBException {
//		DBManager manager = DBManager.getInstance(true);
//
//		User user = new User();
//		user.setRoleId(0);
//		user.setLogin("admin");
//		user.setPassword("admin");
//		user = manager.addUser(user);
//		
//		User actual = manager.findUserByLogin("admin");
//		User expected = user;
//		assertEquals(expected, actual);
//
//	}
//
//	@Test
//	public void testFindUserInfoById() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testFindUserInfoByUserId() {
//		fail("Not yet implemented");
//	}

	@Test
	public void testAddUser() throws DBException {
		DBManager manager = DBManager.getInstance(true);

		User user = new User();
		user.setRoleId(0);
		user.setLogin("admin");
		user.setPassword("admin");

		User actual = manager.addUser(user);
		User expected = user;
		assertEquals(expected, actual);
	}

	@Test
	public void testAddCar() throws DBException {
		DBManager manager = DBManager.getInstance(true);

		Car car = new Car();
		car.setId(1);
		car.setName("Camaro");
		car.setMark("Chevrolet");
		car.setCarClass("S");
		car.setCarPrice(5000);
		car.setCarDriverPrice(5200);

		Car actual = manager.addCar(car);
		Car expected = car;
		assertEquals(expected, actual);
	}

	@Test
	public void testAddUserInfo() throws DBException {
		DBManager manager = DBManager.getInstance(true);

		UserInfo userInfo = new UserInfo();
		userInfo.setUserId(1);
		userInfo.setFirstName("Alex");
		userInfo.setLastName("Yegorov");
		userInfo.setPassportNumber("MB562321");
		userInfo.setPhoneNumber("0507110189");
		userInfo.setEmail("yegorov@gmail.com");

		UserInfo actual = manager.addUserInfo(userInfo);
		UserInfo expected = userInfo;
		assertEquals(expected, actual);
	}

//	@Test
//	public void testAddOrder() throws DBException {
//		 fail("Not yet implemented");
////		DBManager manager = DBManager.getInstance(true);
////
////		Order order = new Order();
////		order.setUserId(1);
////		order.setCarId(1);
////		order.setDriverStatus(true);
////		Date d1 = DateUtil.getDateFromString("2016-08-25", "yyyy-MM-dd");
////		Date d2 = DateUtil.getDateFromString("2016-08-26", "yyyy-MM-dd");
////		order.setOrderData(d1);
////		order.setReturnData(d2);
////		System.out.println("1" + order);
////
////		Order actual = manager.addOrder(order);
////		System.out.println(actual);
////		order.setOrderPrice(0);
////		Order expected = order;
////		System.out.println(expected);
////		assertEquals(expected, actual);
//	}
//
//	@Test
//	public void testUpdateUserStatus() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testUpdateUserInfo() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testUpdateOrderPrice() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testUpdateOrderStatus() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testUpdateOrderStatusRej() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testUpdateOrderReturn() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testUpdateCarPrice() {
//		fail("Not yet implemented");
//	}

//	@Test
//	public void testDeleteCarById() throws DBException {
//		DBManager manager = DBManager.getInstance(true);
//		boolean result = false;
//		Car car = new Car();
//		car.setId(1);
//		car.setName("Camaro");
//		car.setMark("Chevrolet");
//		car.setCarClass("S");
//		car.setCarPrice(5000);
//		car.setCarDriverPrice(5200);
//		Car car2 = manager.addCar(car);
//		System.out.println(car2);
//		result = manager.deleteCarById(car2.getId());
//		Assert.assertTrue(result);
//	}

	private void log(String msg) {
		System.out.println("[ContextListener] " + msg);
	}

}

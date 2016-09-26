package ua.nure.yegorov.SummaryTask4.db;

import static org.junit.Assert.*;

import org.junit.Test;

import ua.nure.yegorov.SummaryTask4.db.entity.User;

public class RoleTest {

	@Test
	public void testConstructor1() {
		Role role = Role.ADMIN;
		role.toString();
	}

	@Test
	public void testGetRole() {
		User user = new User();
		user.setRoleId(2);
		Role role = Role.getRole(user);
		String actual = role.getName();
		String expected = "client";
		assertEquals(expected, actual);
	}

	@Test
	public void testGetName() {
		Role role = Role.ADMIN;
		String actual = role.getName();
		String expected = "admin";
		assertEquals(expected, actual);
	}

}

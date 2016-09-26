package ua.nure.yegorov.SummaryTask4.db;

import static org.junit.Assert.*;

import org.junit.Test;

public class StatusTest {

	@Test
	public void test1() {
		int actual = Status.CLOSED.ordinal();
		int expected = 4;
		assertEquals(expected, actual);
	}
	
	@Test
	public void test2() {
		String actual = Status.OPENED.name();
		String expected = "OPENED";
		assertEquals(expected, actual);
	}
	
	@Test
	public void test3() {
		Status s = Status.CONFIRMED;
		String actual = s.toString();
		String expected = "CONFIRMED";
		assertEquals(expected, actual);
	}
}

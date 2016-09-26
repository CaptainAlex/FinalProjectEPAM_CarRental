package ua.nure.yegorov.SummaryTask4;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import ua.nure.yegorov.SummaryTask4.db.DBManagerTest;
import ua.nure.yegorov.SummaryTask4.db.FieldsTest;
import ua.nure.yegorov.SummaryTask4.db.RoleTest;
import ua.nure.yegorov.SummaryTask4.db.StatusTest;
import ua.nure.yegorov.SummaryTask4.db.bean.UserInfoBeanTest;
import ua.nure.yegorov.SummaryTask4.db.bean.UserOrderBeanTest;
import ua.nure.yegorov.SummaryTask4.db.entity.CarTest;
import ua.nure.yegorov.SummaryTask4.db.entity.OrderTest;
import ua.nure.yegorov.SummaryTask4.db.entity.UserInfoTest;
import ua.nure.yegorov.SummaryTask4.db.entity.UserTest;
import ua.nure.yegorov.SummaryTask4.exception.AppExceptionTest;
import ua.nure.yegorov.SummaryTask4.exception.DBExceptionTest;
import ua.nure.yegorov.SummaryTask4.exception.MessagesTest;
import ua.nure.yegorov.SummaryTask4.util.DateUtilTest;
import ua.nure.yegorov.SummaryTask4.web.ControllerTest;

@RunWith(Suite.class)
@SuiteClasses({ CarTest.class, OrderTest.class, UserInfoTest.class,
		UserTest.class, UserInfoBeanTest.class, UserOrderBeanTest.class,
		FieldsTest.class, RoleTest.class, StatusTest.class,
		DBExceptionTest.class, AppExceptionTest.class, MessagesTest.class,
		DateUtilTest.class, ControllerTest.class, PathTest.class, DBManagerTest.class })
public class AllTests {

}

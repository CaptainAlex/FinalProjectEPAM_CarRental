package ua.nure.yegorov.SummaryTask4.db;

/**
 * Holder for fields names of DB tables and beans.
 * 
 * @author A.Yegorov
 *
 */

public final class Fields {

	// entities
	public static final String ENTITY_ID = "id";

	public static final String USER_ROLE_ID = "role_id";
	public static final String USER_LOGIN = "login";
	public static final String USER_PASSWORD = "password";
	public static final String USER_STATUS = "user_status";

	public static final String USER_INFO_USER_ID = "user_id";
	public static final String USER_INFO_FIRST_NAME = "first_name";
	public static final String USER_INFO_LAST_NAME = "last_name";
	public static final String USER_INFO_PASSPORT_NUMBER = "passport_number";
	public static final String USER_INFO_PHONE_NUMBER = "phone_number";
	public static final String USER_INFO_EMAIL = "email";

	public static final String CAR_NAME = "name";
	public static final String CAR_MARK = "mark";
	public static final String CAR_CLASS = "car_class";
	public static final String CAR_PRICE = "car_price";
	public static final String CAR_DRIVER_PRICE = "car_driver_price";

	public static final String ORDER_USER_ID = "user_id";
	public static final String ORDER_CAR_ID = "car_id";
	public static final String ORDER_DRIVER_STATUS = "driver_status";
	public static final String ORDER_DATA_ORDER = "order_data";
	public static final String ORDER_DATA_RETURN = "return_data";
	public static final String ORDER_PRICE = "order_price";
	public static final String ORDER_STATUS_ID = "status_id";
	public static final String ORDER_REJECTION_REASON = "rejection_reason";
	public static final String ORDER_DAMAGE = "damage";
	public static final String ORDER_PRICE_FOR_REPAIRS = "price_for_repairs";

	// beans
	public static final String USER_ORDER_BEAN_ORDER_ID = "id";
	public static final String USER_ORDER_BEAN_USER_FIRST_NAME = "first_name";
	public static final String USER_ORDER_BEAN_USER_LAST_NAME = "last_name";
	public static final String USER_ORDER_BEAN_CAR_NAME = "name";
	public static final String USER_ORDER_BEAN_DRIVER_STATUS = "driver_status";
	public static final String USER_ORDER_BEAN_DATA_ORDER = "order_data";
	public static final String USER_ORDER_BEAN_DATA_RETURN = "return_data";
	public static final String USER_ORDER_BEAN_ORDER_PRICE = "order_price";
	public static final String USER_ORDER_BEAN_STATUS_NAME = "status_name";
	public static final String USER_ORDER_BEAN_REJECTION_REASON = "rejection_reason";
	public static final String USER_ORDER_BEAN_DAMAGE = "damage";
	public static final String USER_ORDER_BEAN_PRICE_FOR_REPAIRS = "price_for_repairs";
	
	public static final String USER_INFO_BEAN_USER_ID = "id";
	public static final String USER_INFO_BEAN_LOGIN = "login";
	public static final String USER_INFO_BEAN_PASSWORD = "password";
	public static final String USER_INFO_BEAN_FIRST_NAME = "first_name";
	public static final String USER_INFO_BEAN_LAST_NAME = "last_name";
	public static final String USER_INFO_BEAN_PASSPORT_NUMBER = "passport_number";
	public static final String USER_INFO_BEAN_PHONE_NUMBER = "phone_number";
	public static final String USER_INFO_BEAN_EMAIL = "email";
	public static final String USER_INFO_BEAN_STATUS = "user_status";

}

package ua.nure.yegorov.SummaryTask4.db;

import ua.nure.yegorov.SummaryTask4.db.entity.User;

/**
 * Role entity.
 * 
 * @author A.Yegorov
 *
 */

public enum Role {

	ADMIN, MANAGER, CLIENT;
	
	public static Role getRole(User user) {
		int roleId = user.getRoleId();
		return Role.values()[roleId];
	}
	
	public String getName() {
		return name().toLowerCase();
	}
}

package ua.nure.yegorov.SummaryTask4.db.entity;

/**
 * User entity.
 * 
 * @author A.Yegorov
 *
 */

public class User extends Entity {

	private static final long serialVersionUID = 8088807085555319274L;

	private int roleId;

	private String login;

	private String password;

	private boolean status;

	public int getRoleId() {
		return roleId;
	}

	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "User [roleId=" + roleId + ", login=" + login + ", password="
				+ password + ", status=" + status + "]";
	}

}

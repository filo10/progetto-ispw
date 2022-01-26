package it.uniroma2.progettoispw.briscese.bean;

public class LoginBean {
	private int userId;
	private String password;
	private String fullName;
	private String role;


	public LoginBean(int userId, String password) {
		this.userId = userId;
		this.password = password;
	}

	public LoginBean(int userId, String fullName, String role) {
		this.userId = userId;
		this.fullName = fullName;
		this.role = role;
	}

	public int getUserId() {
		return userId;
	}

	public String getPassword() {
		return password;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getRole() {
		return role;
	}
}

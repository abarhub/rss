package org.rss.beans.metier;

/**
 * Created by Alain on 25/09/2016.
 */
public class LoginDTO {

	private String login;
	private String password;

	public LoginDTO() {
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

	@Override
	public String toString() {
		return "LoginDTO{" +
				"login='" + login + '\'' +
				", password='" + password + '\'' +
				'}';
	}
}

package org.rss.beans.metier;

/**
 * Created by Alain on 25/09/2016.
 */
public class LoginResponseDTO {

	private boolean loginOk;
	private String messageError;
	private String IdUser;

	public LoginResponseDTO() {
	}

	public boolean isLoginOk() {
		return loginOk;
	}

	public void setLoginOk(boolean loginOk) {
		this.loginOk = loginOk;
	}

	public String getMessageError() {
		return messageError;
	}

	public void setMessageError(String messageError) {
		this.messageError = messageError;
	}

	public String getIdUser() {
		return IdUser;
	}

	public void setIdUser(String idUser) {
		IdUser = idUser;
	}

	@Override
	public String toString() {
		return "LoginResponseDTO{" +
				"loginOk=" + loginOk +
				", messageError='" + messageError + '\'' +
				", IdUser='" + IdUser + '\'' +
				'}';
	}
}

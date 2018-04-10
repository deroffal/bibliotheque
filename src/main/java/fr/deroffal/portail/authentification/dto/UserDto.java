package fr.deroffal.portail.authentification.dto;

import java.io.Serializable;

public class UserDto implements Serializable {

	private Long id;

	private String login;

	private String password;

	public Long getId() {
		return id;
	}

	public void setId(final Long id) {
		this.id = id;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(final String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(final String password) {
		this.password = password;
	}
}

package fr.deroffal.bibliotheque.api.authentification.utilisateur;

import java.io.Serializable;
import java.util.List;

public class UserDto implements Serializable {

	private Long id;

	private String login;

	private String password;

	private List<String> roles;

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

	public List<String> getRoles() {
		return roles;
	}

	public void setRoles(final List<String> roles) {
		this.roles = roles;
	}
}

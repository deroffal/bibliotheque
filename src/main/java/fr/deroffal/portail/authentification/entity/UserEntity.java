package fr.deroffal.portail.authentification.entity;

import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import fr.deroffal.portail.PortailConfigutation;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "utilisateur", schema = PortailConfigutation.SCHEMA_AUTHENTIFICATION)
public class UserEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_utilisateur_generator")
	@SequenceGenerator(name = "seq_utilisateur_generator", sequenceName = "seq_utilisateur", allocationSize = 1)
	@Column(name = "id", updatable = false, nullable = false)
	private Long id;

	@Column(name = "login", unique = true, nullable = false)
	private String login;

	@Column(name = "encoded_password", nullable = false)
	private String password;

	@ManyToMany
	@JoinTable(
			schema = PortailConfigutation.SCHEMA_AUTHENTIFICATION,
			name = "utilisateur_role",
			joinColumns = @JoinColumn(name = "user_id"),
			inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Collection<RoleEntity> roles;

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

	public Collection<RoleEntity> getRoles() {
		return roles;
	}

	public void setRoles(final Collection<RoleEntity> roles) {
		this.roles = roles;
	}

	@JsonIgnore
	public String[] getRolesAsStrings() {
		return roles.stream().map(RoleEntity::getRole).toArray(String[]::new);
	}
}

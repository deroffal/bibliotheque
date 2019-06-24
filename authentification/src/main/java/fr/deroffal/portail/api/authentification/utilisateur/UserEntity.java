package fr.deroffal.portail.api.authentification.utilisateur;

import com.fasterxml.jackson.annotation.JsonIgnore;
import fr.deroffal.portail.api.authentification.role.RoleEntity;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "utilisateur")
public class UserEntity {

	@Id
	@SequenceGenerator(name = "seq_utilisateur_generator", sequenceName = "seq_utilisateur", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_utilisateur_generator")
	@Column(name = "id", updatable = false, nullable = false)
	private Long id;

	@Column(name = "login", unique = true, nullable = false)
	private String login;

	@Column(name = "encoded_password", nullable = false)
	private String password;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "utilisateur_role", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
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

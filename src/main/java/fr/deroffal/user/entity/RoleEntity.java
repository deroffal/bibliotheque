package fr.deroffal.user.entity;

import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "ROLE")
public class RoleEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", updatable = false, nullable = false)
	private Long id;

	@Column(name = "ROLE_NAME")
	private String role;

	//UserEntity.roles
	@ManyToMany(mappedBy = "roles")
	private Collection<UserEntity> users;

	public Long getId() {
		return id;
	}

	public void setId(final Long id) {
		this.id = id;
	}

	public String getRole() {
		return role;
	}

	public void setRole(final String role) {
		this.role = role;
	}

	public Collection<UserEntity> getUsers() {
		return users;
	}

	public void setUsers(final Collection<UserEntity> users) {
		this.users = users;
	}
}

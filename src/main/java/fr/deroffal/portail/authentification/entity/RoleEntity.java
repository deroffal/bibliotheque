package fr.deroffal.portail.authentification.entity;

import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "role", schema = "portail")
public class RoleEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_role_generator")
	@SequenceGenerator(name = "seq_role_generator", sequenceName = "seq_role", allocationSize = 1)
	@Column(name = "id", updatable = false, nullable = false)
	private Long id;

	@Column(name = "role_name")
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

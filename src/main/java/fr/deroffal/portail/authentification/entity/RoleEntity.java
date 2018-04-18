package fr.deroffal.portail.authentification.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import fr.deroffal.portail.PortailConfigutation;

@Entity
@Table(name = "role_applicatif", schema = PortailConfigutation.SCHEMA_AUTHENTIFICATION)
public class RoleEntity {

	@Id
	@SequenceGenerator(name = "seq_role_generator", sequenceName = "seq_role", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_role_generator")
	@Column(name = "id", updatable = false, nullable = false)
	private Long id;

	@Column(name = "role_name")
	private String role;

	public RoleEntity() {
		super();
	}

	public RoleEntity(final String role) {
		this.role = role;
	}

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

}

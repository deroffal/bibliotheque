package fr.deroffal.user.entity;

import javax.persistence.*;

@Entity
@Table(name = "UTILISATEUR_ROLE", schema = "PUBLIC")
public class UtilisateurRoleEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_utilisateur_role_generator")
	@SequenceGenerator(name = "seq_utilisateur_role_generator", sequenceName = "seq_utilisateur_role", allocationSize = 1)
	@Column(name = "id")
	private Long id;

	@Column(name = "USER_ID", nullable = false)
	private Long userId;

	@Column(name = "ROLE_ID", nullable = false)
	private Long roleId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(final Long userId) {
		this.userId = userId;
	}

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(final Long roleId) {
		this.roleId = roleId;
	}
}

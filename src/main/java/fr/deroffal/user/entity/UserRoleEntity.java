package fr.deroffal.user.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

//@Entity
//@Table(name = "USER_ROLE")
public class UserRoleEntity {

	@Column(name = "USER_ID", nullable = false)
	private Long userId;

	@Column(name = "ROLE_ID", nullable = false)
	private Long roleId;

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

package com.docker.junkstarter.model;

import java.util.UUID;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "user_group_member", uniqueConstraints = { @UniqueConstraint(columnNames = "groupMemberId")})
@AttributeOverride(name = "id", column = @Column(name = "groupMemberId"))
public class UserGroupMember extends AuditableEntity {

	private static final long serialVersionUID = 4825351414603848635L;

	@NotNull
	@Column(name = "groupId", nullable = false)
	private UUID groupId;

	@NotNull
	@Column(name = "userId", nullable = false)
	private UUID userId;

	public UserGroupMember() {

	}

	public UserGroupMember(UUID groupId, UUID userId) {
		super();
		this.groupId = groupId;
		this.userId = userId;
	}

	public UUID getGroupMemberId() {
		return getId();
	}

	public void setGroupMemberId(UUID id) {
		setId(id);
	}

	public UUID getGroupId() {
		return groupId;
	}

	public void setGroupId(UUID groupId) {
		this.groupId = groupId;
	}

	public UUID getUserId() {
		return userId;
	}

	public void setUserId(UUID userId) {
		this.userId = userId;
	}

}

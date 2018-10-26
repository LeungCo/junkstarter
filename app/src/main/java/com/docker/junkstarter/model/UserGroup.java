package com.docker.junkstarter.model;

import java.util.UUID;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "user_group", uniqueConstraints = { @UniqueConstraint(columnNames = "groupId")})
@AttributeOverride(name = "id", column = @Column(name = "groupId"))
public class UserGroup extends AuditableEntity {

	private static final long serialVersionUID = 4825351414603848635L;

	@NotNull
	@Column(name = "ownerId", nullable = false)
	private UUID ownerId;

	@NotNull
	@Column(name = "groupProfileId", nullable = false)
	private UUID groupProfileId;

	public UserGroup() {

	}

	public UserGroup(UUID ownerId, UUID groupProfileId) {
		super();
		this.ownerId = ownerId;
		this.groupProfileId = groupProfileId;
	}

	public UUID getGroupId() {
		return getId();
	}

	public void setGroupId(UUID groupId) {
		setId(groupId);
	}

	public UUID getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(UUID ownerId) {
		this.ownerId = ownerId;
	}

	public UUID getGroupProfileId() {
		return groupProfileId;
	}

	public void setGroupProfileId(UUID groupProfileId) {
		this.groupProfileId = groupProfileId;
	}

}

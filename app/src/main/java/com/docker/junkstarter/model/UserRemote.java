package com.docker.junkstarter.model;

import static com.docker.junkstarter.util.DateUtility.getDateString;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "user_remote", uniqueConstraints = { @UniqueConstraint(columnNames = "userId"),
		@UniqueConstraint(columnNames = "remoteId") })
public class UserRemote extends AuditableEntity {

	private static final long serialVersionUID = 2133036846121663021L;

	@NotNull
	@Column(name = "userId", nullable = false)
	private UUID userId;

	@NotNull
	@Column(name = "remoteId", nullable = false)
	private UUID remoteId;

	public UserRemote() {

	}

	public UserRemote(UUID userId, UUID remoteId) {
		super();
		this.userId = userId;
		this.remoteId = remoteId;
	}

	@Override
	public UUID getId() {
		return super.getId();
	}

	@Override
	public void setId(UUID uuid) {
		super.setId(uuid);
	}

	@Override
	public String toString() {
		return "UserRemote [userId=" + userId + ", remoteId=" + remoteId + " created_at=" + getDateString(createdAt)
				+ " modified_at=" + getDateString(modifiedAt) + "]";
	}

	public UUID getUserId() {
		return userId;
	}

	public void setUserId(UUID userId) {
		this.userId = userId;
	}

	public UUID getRemoteId() {
		return remoteId;
	}

	public void setRemoteId(UUID remoteId) {
		this.remoteId = remoteId;
	}

}

package com.docker.junkstarter.model;

import java.util.UUID;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import com.docker.junkstarter.enums.UserPoolHistoryType;

@Entity
@Table(name = "user_pool_history", uniqueConstraints = { @UniqueConstraint(columnNames = "userPoolHistoryId")})
@AttributeOverride(name = "id", column = @Column(name = "userPoolHistoryId"))
public class UserPoolHistory extends AuditableEntity {

	private static final long serialVersionUID = 4825351414603848635L;

	@NotNull
	@Column(name = "userPoolId", nullable = false)
	private UUID userPoolId;

	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(name = "userPoolHistoryType", nullable = false)
	private UserPoolHistoryType type;

	@NotNull
	@Column(name = "count")
	@Min(1)
	private int count;

	public UserPoolHistory() {

	}

	public UserPoolHistory(UUID userPoolId) {
		super();
		this.userPoolId = userPoolId;
		this.type = UserPoolHistoryType.START;
		this.count = 1;
	}

	public UUID getUserPoolHistoryId() {
		return getId();
	}

	public void setUserPoolHistoryId(UUID id) {
		setId(id);
	}

	public UUID getUserPoolId() {
		return userPoolId;
	}

	public void setUserPoolId(UUID userPoolId) {
		this.userPoolId = userPoolId;
	}

	public UserPoolHistoryType getType() {
		return type;
	}

	public void setType(UserPoolHistoryType type) {
		this.type = type;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

}

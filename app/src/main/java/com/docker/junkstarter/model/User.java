package com.docker.junkstarter.model;

import static com.docker.junkstarter.util.DateUtility.getDateString;

import java.util.UUID;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table(name = "user", uniqueConstraints = { @UniqueConstraint(columnNames = "userId"),
		@UniqueConstraint(columnNames = "email") })
@AttributeOverride(name = "id", column = @Column(name = "userId"))
public class User extends AuditableEntity {

	private static final long serialVersionUID = 2133036846121663021L;

	@NotEmpty
	@Column(name = "email", length = 255, nullable = false)
	private String email;

	@NotEmpty
	@Column(name = "password", length = 40, nullable = false)
	private String password;

	public User() {

	}

	public User(String email, String password) {
		super();
		this.email = email;
		this.password = password;
	}

	@Override
	public String toString() {
		return "User [email=" + email + ", password=" + password + " created_at=" + getDateString(createdAt)
				+ " modified_at=" + getDateString(modifiedAt) + "]";
	}

	public UUID getUserId() {
		return getId();
	}

	public void setUserId(UUID eventId) {
		this.setId(eventId);
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}

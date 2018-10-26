package com.docker.junkstarter.model;

import java.util.UUID;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Index;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import com.docker.junkstarter.enums.Gender;

@Entity
@Table(name = "user_profile", uniqueConstraints = { @UniqueConstraint(columnNames = "userProfileId"),
		@UniqueConstraint(columnNames = "userId") }, indexes = {@Index(name = "i_userId", columnList = "userId")})
@AttributeOverride(name = "id", column = @Column(name = "userProfileId"))
public class UserProfile extends AuditableEntity {

	private static final long serialVersionUID = 4825351414603848635L;

	@NotNull
	@Column(name = "userId", nullable = false)
	private UUID userId;

	@Column(name = "displayName")
	private String displayName;

	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(name = "gender")
	private Gender gender = Gender.PREFER_NOT_SAY;

	@Column(name = "profilePictureId")
	private UUID profilePictureId;

	@Column(name = "dateOfBirth", length = 8)
	private String dateOfBirth;

	@Column(name = "blurb", length = 10485760)
	private String blurb;

	public UserProfile() {

	}

	public UserProfile(UUID userId) {
		super();
		this.userId = userId;
	}

	public UUID getUserId() {
		return userId;
	}

	public void setUserId(UUID userId) {
		this.userId = userId;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public UUID getProfilePictureId() {
		return profilePictureId;
	}

	public void setProfilePictureId(UUID profilePictureId) {
		this.profilePictureId = profilePictureId;
	}

	public String getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getBlurb() {
		return blurb;
	}

	public void setBlurb(String blurb) {
		this.blurb = blurb;
	}

	public UUID getUserProfileId() {
		return getId();
	}

	public void setUserProfileId(UUID id) {
		setId(id);
	}

}

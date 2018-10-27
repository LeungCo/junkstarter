package com.docker.junkstarter.model;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import org.joda.time.DateTime;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
@Entity
@Table(name = "user_pool", uniqueConstraints = { @UniqueConstraint(columnNames = "queueNumber"),
		@UniqueConstraint(columnNames = "groupId") }, indexes = {
				@Index(name = "i_fishingDate", columnList = "fishingDate"),
				@Index(name = "i_groupId", columnList = "groupId") })
public class UserPool implements Serializable {

	private static final long serialVersionUID = 8070525050156807218L;

	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
	private Long queueNumber;

	@NotNull
	@Column(name = "groupId", nullable = false)
	private UUID groupId;

	@NotNull
	@Column(name = "fishingDate", nullable = false)
	private Date fishingDate;

	@Column(name = "created_at", nullable = false, updatable = false)
	public Date createdAt;

	public UserPool() {

	}

	@PrePersist
	void createdAt() {
		DateTime dt = new DateTime();
		Date date = dt.toDate();
		this.createdAt = date;
	}

	public UserPool(UUID groupId) {
		super();
		this.groupId = groupId;
		this.fishingDate = new DateTime().toDate();
	}

	public Long getQueueNumber() {
		return queueNumber;
	}

	public void setQueueNumber(Long queueNumber) {
		this.queueNumber = queueNumber;
	}

	public UUID getGroupId() {
		return groupId;
	}

	public void setGroupId(UUID groupId) {
		this.groupId = groupId;
	}

	public Date getFishingDate() {
		return fishingDate;
	}

	public void setFishingDate(Date fishingDate) {
		this.fishingDate = fishingDate;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

}

package com.docker.junkstarter.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import org.joda.time.DateTime;

@SuppressWarnings("serial")
@MappedSuperclass
public abstract class AuditableEntity extends BaseEntity {
	@Column(name = "created_at", nullable = false, updatable=false)
	public Date createdAt;

	@Column(name = "modified_at", nullable = false)
	public Date modifiedAt;

	@PrePersist
	void createdAt() {
		DateTime dt = new DateTime();
		Date date = dt.toDate();
		this.createdAt = date;
		this.modifiedAt = date;
	}

	@PreUpdate
	void updatedAt() {
		DateTime dt = new DateTime();
		Date date = dt.toDate();
		this.modifiedAt = date;
	}

	public Date getModifiedAt() {
		return modifiedAt;
	}
	
	public Date getCreatedAt() {
		return createdAt;
	}
}

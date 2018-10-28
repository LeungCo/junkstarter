package com.docker.junkstarter.model;

import java.util.UUID;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
@Entity
//@Table(name = "user_pool_tag", uniqueConstraints = { @UniqueConstraint(columnNames = {"queueNumber, tagId"}),
//		@UniqueConstraint(columnNames = "userPoolTagId") }, indexes = {
//				@Index(name = "i_queueNumberTag", columnList = "queueNumber, tagId") })

@Table(name = "user_pool_tag", uniqueConstraints = {
		@UniqueConstraint(columnNames = "userPoolTagId") }, indexes = {
				@Index(name = "i_queueNumberTag", columnList = "queueNumber, tagId") })
@AttributeOverride(name = "id", column = @Column(name = "userPoolTagId"))
public class UserPoolTag extends AuditableEntity {

	private static final long serialVersionUID = -1881385439923714634L;

	@NotNull
	@Column(name = "queueNumber", nullable = false)
	private Long queueNumber;

	@NotNull
	@Column(name = "tagId", nullable = false)
	private UUID tagId;

	public UserPoolTag() {

	}

	public UserPoolTag(Long queueNumber, UUID tagId) {
		super();
		this.queueNumber = queueNumber;
		this.tagId = tagId;
	}

	public UUID getUserPoolTagId() {
		return getId();
	}

	public void setUserPoolTagId(UUID id) {
		this.setId(id);
	}

	public Long getQueueNumber() {
		return queueNumber;
	}

	public void setQueueNumber(Long queueNumber) {
		this.queueNumber = queueNumber;
	}

	public UUID getTagId() {
		return tagId;
	}

	public void setTagId(UUID tagId) {
		this.tagId = tagId;
	}

}

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
@Table(name = "event", uniqueConstraints = { @UniqueConstraint(columnNames = "eventid") })
@AttributeOverride(name = "id", column = @Column(name = "eventId"))
public class Event extends AuditableEntity {

	private static final long serialVersionUID = 2133036846121663021L;

	@NotEmpty
	@Column(name = "name", length = 255, nullable = false)
	private String name;

	@Column(name = "description", length = 10485760, nullable = false)
	private String description;

	public Event() {

	}

	public Event(String name, String description) {
		this.name = name;
		this.description = description;
	}

	public UUID getEventId() {
		return getId();
	}

	public void setEventId(UUID eventId) {
		this.setId(eventId);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return "Event [eventId=" + getEventId() + ", name=" + name + ", description=" + description + " created_at="
				+ getDateString(createdAt) + " modified_at=" + getDateString(modifiedAt) + "]";
	}

}

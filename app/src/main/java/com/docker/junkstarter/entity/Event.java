package com.docker.junkstarter.entity;

import java.util.UUID;

import com.docker.junkstarter.jooq.tables.records.EventRecord;

public class Event extends Entity {
	
	private UUID eventId;
	private String name;
	private String description;

	public Event(String name, String description) {
		this(UUID.randomUUID(), name, description);
	}
	
	public Event(EventRecord record) {
		this(record.getEventid(), record.getName(), record.getDescription());
	}

	public Event(UUID eventId, String name, String description) {
		super();
		this.eventId = eventId;
		this.name = name;
		this.description = description;
	}

	public UUID getEventId() {
		return eventId;
	}

	public void setEventId(UUID eventId) {
		this.eventId = eventId;
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
		return "Event [eventId=" + eventId + ", name=" + name + ", description=" + description + "]";
	}

}

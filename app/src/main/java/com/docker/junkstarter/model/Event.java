package com.docker.junkstarter.model;

import java.io.Serializable;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@Entity
@Table(name="event", uniqueConstraints = { @UniqueConstraint(columnNames = "eventid")})
@JsonInclude(Include.NON_NULL)
public class Event implements Serializable {
	 
	private static final long serialVersionUID = 2133036846121663021L;

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID eventId;
    
    @NotEmpty
    @Column(name = "name", length = 255, nullable = false)
    private String name;
    
    @Column(name = "description", length=10485760, nullable = false)
    private String description;
          
	public Event() {

	}
	
	public Event(String name, String description) {
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
		return "Event [eventId=" + eventId +
				         ", name=" + name +
				         ", description=" + description +
				         "]";
	}
	
}

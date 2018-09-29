package com.docker.atsea.model;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import java.io.Serializable;
import javax.persistence.*;

@Entity
@Table(name="event", uniqueConstraints = { @UniqueConstraint(columnNames = "eventid")})
@JsonInclude(Include.NON_NULL)
public class Event implements Serializable {
	 
	private static final long serialVersionUID = 2133036846121663021L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long eventId;
    
    @NotEmpty
    @Column(name = "name", length = 255, nullable = false)
    private String name;
    
    @Column(name = "description", length=10485760, nullable = false)
    private String description;
          
	public Event() {
		
	}
	
	public Event(Long eventId, String name, String description) {
		this.eventId = eventId;
		this.name = name;
		this.description = description;	
	}

    public long getEventId() {
    	return eventId;
    }
    
    public void setEventId(long eventId) {
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

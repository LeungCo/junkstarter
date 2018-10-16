package com.docker.junkstarter.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@Entity
@Table(name="event")
@JsonInclude(Include.NON_NULL)
public class Event extends BaseModel{
	 
	private static final long serialVersionUID = 2133036846121663021L;
    
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
		return "Event [uuid=" + getId()+
				         ", name=" + name +
				         ", description=" + description +
				         "]";
	}
	
}

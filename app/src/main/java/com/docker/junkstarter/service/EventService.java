package com.docker.junkstarter.service;

import java.util.List;

import com.docker.junkstarter.model.Event;

public interface EventService {
	
	Event findByName(String name);
		
	List<Event> findAllEvents();

	Event findById(Long eventId);
}

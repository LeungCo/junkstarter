package com.docker.junkstarter.service;

import java.util.List;
import java.util.UUID;

import com.docker.junkstarter.model.Event;

public interface EventService {
	
	Event findByName(String name);
		
	List<Event> findAllEvents();

	Event findById(UUID eventId);
}

package com.docker.atsea.service;

import java.util.List;

import com.docker.atsea.model.Event;

public interface EventService {
	
	Event findByName(String name);
		
	List<Event> findAllEvents();

	Event findById(Long eventId);
}

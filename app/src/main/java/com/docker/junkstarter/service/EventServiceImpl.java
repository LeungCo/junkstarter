package com.docker.junkstarter.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.docker.junkstarter.entity.Event;
import com.docker.junkstarter.repositories.EventRepository;

@Service("eventService")
@Transactional
public class EventServiceImpl implements EventService {

	@Autowired
	private EventRepository eventRepository;

	public List<Event> findAllEvents() {
		return eventRepository.findAll();
	}

	public Event findByName(String name) {
		return new Event("asdf","asdf");
	}

	public Event findById(UUID eventId) {
		return new Event("asdf","asdf");
	}
}

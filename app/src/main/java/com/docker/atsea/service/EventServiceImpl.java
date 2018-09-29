package com.docker.atsea.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.docker.atsea.model.Event;
import com.docker.atsea.repositories.EventRepository;

@Service("eventService")
@Transactional
public class EventServiceImpl implements EventService {

	@Autowired
	private EventRepository eventRepository;

	public List<Event> findAllEvents() {
		return eventRepository.findAll();
	}

	public Event findByName(String name) {
		return eventRepository.findByName(name);
	}

	public Event findById(Long eventId) {
		return eventRepository.findOne(eventId);
	}
}

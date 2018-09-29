package com.docker.junkstarter.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.docker.junkstarter.model.Event;
import com.docker.junkstarter.service.EventService;
import com.docker.junkstarter.util.CustomErrorType;

@RestController
@RequestMapping("/api")
public class EventController {
	public static final Logger logger = LoggerFactory.getLogger(EventController.class);
	
	@Autowired
	EventService eventService;
	
	// -------------------------------------------------------------------
	//                   Event methods
	//--------------------------------------------------------------------


	// -------------------Retrieve All Events---------------------------------------------

	@RequestMapping(value = "/event/", method = RequestMethod.GET)
	public ResponseEntity<List<Event>> listAllEvents() {
		List<Event> events = eventService.findAllEvents();
		if (events.isEmpty()) {
			return new ResponseEntity<List<Event>>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<Event>>(events, HttpStatus.OK);
	}

	// -------------------Retrieve Single Event By Id------------------------------------------

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/event/{eventId}", method = RequestMethod.GET)
	public ResponseEntity<?> getEvent(@PathVariable("eventId") long eventId) {
		logger.info("Fetching Event with id {}", eventId);
		Event event = eventService.findById(eventId);
		if (event == null) {
			logger.error("Event with id {} not found.", eventId);
			return new ResponseEntity(new CustomErrorType("Event with id " + eventId 
					+ " not found"), HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Event>(event, HttpStatus.OK);
	}
}

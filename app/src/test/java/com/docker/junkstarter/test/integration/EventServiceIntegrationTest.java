package com.docker.junkstarter.test.integration;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.docker.junkstarter.model.Event;
import com.docker.junkstarter.repositories.EventRepository;
import com.docker.junkstarter.service.EventService;

@SpringBootTest
@RunWith(SpringRunner.class)
public class EventServiceIntegrationTest {

	@Autowired
	private EventService service;
	
	@Autowired
	private EventRepository repository;

	private Event event;

	@Before
	public void setup() {
		event = new Event("name", "desc");
		repository.saveAndFlush(event);
	}

	@Test
	public void findByIdSucceeds() {
		Event found = service.findById(event.getEventId());
		assertThat(found.getName()).isEqualTo("name");
	}
	
	@Test
	public void findByNameSucceeds() {
		Event found = service.findByName(event.getName());
		assertThat(found.getName()).isEqualTo("name");
	}
	
	@Test
	public void findAllEventsSucceeds() {
		Event event2 = new Event("name", "desc");
		event2 = repository.saveAndFlush(event2);

		List<Event> results = service.findAllEvents();
		
		assertThat(results.get(0).getEventId()).isEqualTo(event.getEventId());
		assertThat(results.get(1).getEventId()).isEqualTo(event2.getEventId());
	}
	
	@After
	public void tearDown() {
		repository.deleteAll();
		repository.flush();
		assertThat(repository.count()).isEqualTo(0);
	}
}

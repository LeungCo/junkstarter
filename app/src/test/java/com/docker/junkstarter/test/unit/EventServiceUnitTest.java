package com.docker.junkstarter.test.unit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.internal.verification.VerificationModeFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import com.docker.junkstarter.model.Event;
import com.docker.junkstarter.repositories.EventRepository;
import com.docker.junkstarter.service.EventService;
import com.docker.junkstarter.service.EventServiceImpl;

@RunWith(SpringRunner.class)
public class EventServiceUnitTest {

	public static final String NAME_1 = "Name1";
	public static final String NAME_2 = "Name2";
	public static final String NAME_3 = "Name3";

	@TestConfiguration
	static class EventServiceImplTestContextConfiguration {
		@Bean
		public EventService eventService() {
			return new EventServiceImpl();
		}
	}

	@Autowired
	private EventService eventService;

	@MockBean
	private EventRepository eventRepository;

	@Before
	public void setUp() {
		Event event1 = new Event(NAME_1, "desc1");
		Event event2 = new Event(NAME_2, "desc2");
		Event event3 = new Event(NAME_3, "desc3");

		List<Event> allEvents = Arrays.asList(event1, event2, event3);

		Mockito.when(eventRepository.findByName(event1.getName())).thenReturn(event1);
		Mockito.when(eventRepository.findAll()).thenReturn(allEvents);
	}

	@Test
	public void whenValidName_thenEventShouldBeFound() {
		Event event = eventService.findByName(NAME_1);
		assertThat(event.getName(), is(NAME_1));
	}

	@Test
	public void given3Employees_whengetAll_thenReturn3Records() {
		List<Event> allEvents = eventService.findAllEvents();
		verifyFindAllEventsIsCalledOnce();
		assertThat(allEvents).hasSize(3).extracting(Event::getName).contains(NAME_1, NAME_2,NAME_3);
	}
	
	 private void verifyFindAllEventsIsCalledOnce() {
	        Mockito.verify(eventRepository, VerificationModeFactory.times(1)).findAll();
	        Mockito.reset(eventRepository);
	    }
}

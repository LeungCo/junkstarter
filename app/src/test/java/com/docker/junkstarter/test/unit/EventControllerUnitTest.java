package com.docker.junkstarter.test.unit;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import com.docker.junkstarter.controller.EventController;
import com.docker.junkstarter.entity.Event;
import com.docker.junkstarter.service.EventService;

import static org.hamcrest.Matchers.hasSize;

@SpringBootTest
@RunWith(SpringRunner.class)
public class EventControllerUnitTest {

	private static final String UUID_1_STRING = "6defa186-a475-429b-9534-55e0a8340d49";
	private static final String UUID_2_STRING = "7defa186-a475-429b-9534-55e0a8340d49";
	private static final UUID UUID_1 = UUID.fromString(UUID_1_STRING);
	private static final UUID UUID_2 = UUID.fromString(UUID_2_STRING);

	private MockMvc mockMvc;

	@Autowired
	protected WebApplicationContext wac;

	@Autowired
	private EventController eventController;

	@MockBean
	private EventService eventService;

	@Before
	public void setup() throws Exception {
		this.mockMvc = standaloneSetup(this.eventController).build();
	}

	@Test
	public void readSucceeds() throws Exception {
		Event event = new Event("name","desc");
		event.setEventId(UUID_1);
		when(eventService.findById(UUID_1)).thenReturn(event);
	
		mockMvc.perform(get("/api/event/" + UUID_1_STRING).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.eventId", is(UUID_1_STRING)))
				.andDo(print());
	}

	@Test
	public void searchSucceeds() throws Exception {
		Event event1 = new Event("name", "desc");
		event1.setEventId(UUID_1);
		Event event2 = new Event("name", "desc");
		event2.setEventId(UUID_2);
		List<Event>events = new ArrayList<>();
		events.add(event1);
		events.add(event2);
		
		when(eventService.findAllEvents()).thenReturn(events);
		
		mockMvc.perform(get("/api/event/").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(2)))
				.andExpect(jsonPath("$[0].eventId", is(UUID_1_STRING)))
				.andExpect(jsonPath("$[1].eventId", is(UUID_2_STRING)))
				.andDo(print());
	}

}

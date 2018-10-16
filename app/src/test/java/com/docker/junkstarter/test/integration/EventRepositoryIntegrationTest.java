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

@SpringBootTest
@RunWith(SpringRunner.class)
public class EventRepositoryIntegrationTest {

	@Autowired
	private EventRepository repository;

	private Event event;

	@Before
	public void setup() {
		event = new Event("name", "desc");
		repository.saveAndFlush(event);
	}

	@Test
	public void findByNameSucceeds() {
		Event found = repository.findByName("name");
		assertThat(found.getName()).isEqualTo("name");
	}

	@Test
	public void findOneSucceeds() {
		Event found = repository.findOne(event.getId());
		assertThat(found.getName()).isEqualTo("name");
		assertThat(repository.exists(event.getId())).isEqualTo(true);
	}

	@Test
	public void findAllSucceeds() {
		Event event2 = new Event("name", "desc");
		event2 = repository.saveAndFlush(event2);

		List<Event> results = repository.findAll();

		assertThat(results.size()).isEqualTo(2);

		Event result = results.get(0);
		assertThat(result.getId()).isEqualTo(event.getId());

		Event result2 = results.get(1);
		assertThat(result2.getId()).isEqualTo(event2.getId());
	}

	@Test
	public void createSucceeds() {
		Event event = new Event("name", "desc");
		event = repository.saveAndFlush(event);
		assertThat(repository.exists(event.getId())).isEqualTo(true);
	}

	@Test
	public void deleteSucceeds() {
		repository.delete(event);
		assertThat(repository.exists(event.getId())).isEqualTo(false);
	}

	@After
	public void tearDown() {
		repository.deleteAll();
		repository.flush();
		assertThat(repository.count()).isEqualTo(0);
	}
}

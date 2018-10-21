package com.docker.junkstarter.test.integration.event;

import static org.assertj.core.api.Assertions.assertThat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.joda.time.DateTimeUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import com.docker.junkstarter.model.Event;
import com.docker.junkstarter.repositories.EventRepository;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;

@SpringBootTest
@RunWith(SpringRunner.class)
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class, DbUnitTestExecutionListener.class })
@DatabaseSetup("events.xml")
public class EventRepositoryIntegrationTest {

	private static final UUID EVENT_ID1 = UUID.fromString("667fd724-2ac5-466b-ab85-948f42b0d372");
	private static final UUID EVENT_ID2 = UUID.fromString("6654b8df-7ad8-4732-8f8a-11d9870404e9");
	private static final UUID EVENT_ID3 = UUID.fromString("fa4bdfd1-6eec-428b-8117-e4a09a71c6c2");

	@Autowired
	private EventRepository repository;

	private Date today;

	@Before
	public void setup() throws Exception {
		String myDate = "2018/05/20";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		today = sdf.parse(myDate);
		long millis = today.getTime();
		DateTimeUtils.setCurrentMillisFixed(millis);
	}

	@Test
	public void findByNameSucceeds() {
		Event found = repository.findByName("event1");
		assertThat(found.getName()).isEqualTo("event1");
	}

	@Test
	public void findOneSucceeds() {
		Event found = repository.findOne(EVENT_ID1);
		assertThat(found.getName()).isEqualTo("event1");
		assertThat(repository.exists(EVENT_ID1)).isEqualTo(true);
	}

	@Test
	public void findAllSucceeds() {
		List<Event> results = repository.findAll(new Sort(Sort.Direction.ASC, "name"));
		;

		assertThat(results.size()).isEqualTo(3);

		Event result = results.get(0);
		assertThat(result.getEventId()).isEqualTo(EVENT_ID1);

		Event result2 = results.get(1);
		assertThat(result2.getEventId()).isEqualTo(EVENT_ID2);

		Event result3 = results.get(2);
		assertThat(result3.getEventId()).isEqualTo(EVENT_ID3);
	}

	@Test
	public void createSucceeds() {
		Event event = new Event("name", "desc");
		event = repository.saveAndFlush(event);
		assertThat(repository.exists(event.getEventId())).isEqualTo(true);

		Event found = repository.findOne(event.getEventId());
		assertThat(found.getName()).isEqualTo("name");
		assertThat(found.getDescription()).isEqualTo("desc");
		assertThat(found.getCreatedAt().getTime()).isEqualTo(today.getTime());
		assertThat(found.getModifiedAt().getTime()).isEqualTo(today.getTime());
	}
	
	@Test
	public void updateSucceeds() throws Exception {
		String createdDate = "2018/01/01";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		Date date = sdf.parse(createdDate);
		long millis = date.getTime();
		
		Event event = new Event("Changed", "Changed desc");
		event.setEventId(EVENT_ID1);
		event = repository.save(event);
		
		Event found = repository.findOne(event.getEventId());
		assertThat(found.getName()).isEqualTo("Changed");
		assertThat(found.getDescription()).isEqualTo("Changed desc");
		assertThat(found.getCreatedAt().getTime()).isEqualTo(millis);
		assertThat(found.getModifiedAt().getTime()).isEqualTo(today.getTime());
	}

	@Test
	public void deleteSucceeds() {
		repository.delete(EVENT_ID1);
		assertThat(repository.exists(EVENT_ID1)).isEqualTo(false);
	}
}

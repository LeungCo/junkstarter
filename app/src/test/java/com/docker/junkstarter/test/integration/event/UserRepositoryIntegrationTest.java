package com.docker.junkstarter.test.integration.event;

import static com.docker.junkstarter.util.DateUtility.getDateMillis;
import static org.assertj.core.api.Assertions.assertThat;

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

import com.docker.junkstarter.model.User;
import com.docker.junkstarter.repositories.UserRepository;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;

@SpringBootTest
@RunWith(SpringRunner.class)
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class, DbUnitTestExecutionListener.class })
@DatabaseSetup("users.xml")
public class UserRepositoryIntegrationTest {

	private static final UUID USER_ID1 = UUID.fromString("667fd724-2ac5-466b-ab85-948f42b0d372");
	private static final UUID USER_ID2 = UUID.fromString("6654b8df-7ad8-4732-8f8a-11d9870404e9");
	private static final UUID USER_ID3 = UUID.fromString("fa4bdfd1-6eec-428b-8117-e4a09a71c6c2");

	private static final String USER1_EMAIL = "user1@gmail.com";

	@Autowired
	private UserRepository repository;

	private static long TODAY_MILLIS = getDateMillis("2018-05-20");

	@Before
	public void setup() throws Exception {
		DateTimeUtils.setCurrentMillisFixed(TODAY_MILLIS);
	}

	@Test
	public void findByEmailSucceeds() {
		User found = repository.findByEmail(USER1_EMAIL);
		assertThat(found.getEmail()).isEqualTo(USER1_EMAIL);
	}

	@Test
	public void findOneSucceeds() {
		User found = repository.findOne(USER_ID1);
		assertThat(found.getEmail()).isEqualTo(USER1_EMAIL);
		assertThat(repository.exists(USER_ID1)).isEqualTo(true);
	}

	@Test
	public void findAllSucceeds() {
		List<User> results = repository.findAll(new Sort(Sort.Direction.ASC, "email"));
		;

		assertThat(results.size()).isEqualTo(3);

		User result = results.get(0);
		assertThat(result.getUserId()).isEqualTo(USER_ID1);

		User result2 = results.get(1);
		assertThat(result2.getUserId()).isEqualTo(USER_ID2);

		User result3 = results.get(2);
		assertThat(result3.getUserId()).isEqualTo(USER_ID3);
	}

	@Test
	public void createSucceeds() {
		User event = new User("userNew@gmail.com", "pw");
		event = repository.saveAndFlush(event);
		assertThat(repository.exists(event.getUserId())).isEqualTo(true);

		User found = repository.findOne(event.getUserId());
		assertThat(found.getEmail()).isEqualTo("userNew@gmail.com");
		assertThat(found.getPassword()).isEqualTo("pw");
		assertThat(found.getCreatedAt().getTime()).isEqualTo(TODAY_MILLIS);
		assertThat(found.getModifiedAt().getTime()).isEqualTo(TODAY_MILLIS);
	}

	@Test
	public void updateSucceeds() throws Exception {
		User event = new User("changed@gmail.com", "change");
		event.setUserId(USER_ID1);
		event = repository.save(event);

		User found = repository.findOne(event.getUserId());
		assertThat(found.getEmail()).isEqualTo("changed@gmail.com");
		assertThat(found.getPassword()).isEqualTo("change");
		assertThat(found.getCreatedAt().getTime()).isEqualTo(getDateMillis("2018-01-01"));
		assertThat(found.getModifiedAt().getTime()).isEqualTo(TODAY_MILLIS);
	}

	@Test
	public void deleteSucceeds() {
		repository.delete(USER_ID1);
		assertThat(repository.exists(USER_ID1)).isEqualTo(false);
	}

	@Test
	public void deleteByEmailSucceeds() {
		assertThat(repository.exists(USER_ID1)).isEqualTo(true);
		repository.deleteByEmail(USER1_EMAIL);
		assertThat(repository.exists(USER_ID1)).isEqualTo(false);
	}
}

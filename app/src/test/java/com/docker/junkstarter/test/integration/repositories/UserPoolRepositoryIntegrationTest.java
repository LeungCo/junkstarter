package com.docker.junkstarter.test.integration.repositories;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.UUID;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;

import com.docker.junkstarter.model.UserPool;
import com.docker.junkstarter.repositories.UserPoolRepository;
import com.docker.junkstarter.test.core.RepositoryTest;
import com.github.springtestdbunit.annotation.DatabaseSetup;

@DatabaseSetup("/userPools.xml")
public class UserPoolRepositoryIntegrationTest extends RepositoryTest  {

	private static final Long QUEUE_NO1 = 10L;
	private static final Long QUEUE_NO2 = 20L;
	private static final Long QUEUE_NO3 = 30L;

	private static final UUID GROUP_ID1 = UUID.fromString("8ed9e0ca-90a8-4818-bf68-c84bc0b44f10");

	private static final UUID CHANGED_ID = UUID.fromString("00e1a507-c9cf-429f-a06e-5dfb895abfbb");

	@Autowired
	private UserPoolRepository repository;

	@Test
	public void findByGroupIdSucceeds() {
		UserPool found = repository.findByGroupId(GROUP_ID1);
		assertThat(found.getQueueNumber()).isEqualTo(QUEUE_NO1);
	}

	@Test
	public void findOneSucceeds() {
		UserPool found = repository.findOne(QUEUE_NO1);
		assertThat(found.getGroupId()).isEqualTo(GROUP_ID1);
		assertThat(repository.exists(QUEUE_NO1)).isEqualTo(true);
	}

	@Test
	public void findAllSucceeds() {
		List<UserPool> results = repository.findAll(new Sort(Sort.Direction.ASC, "groupId"));

		assertThat(results.size()).isEqualTo(3);

		UserPool result = results.get(0);
		assertThat(result.getQueueNumber()).isEqualTo(QUEUE_NO1);

		UserPool result2 = results.get(1);
		assertThat(result2.getQueueNumber()).isEqualTo(QUEUE_NO2);

		UserPool result3 = results.get(2);
		assertThat(result3.getQueueNumber()).isEqualTo(QUEUE_NO3);
	}

	@Test
	public void createSucceeds() {
		UserPool userPool = new UserPool(CHANGED_ID);
		userPool.setQueueNumber(100L);
		userPool = repository.saveAndFlush(userPool);
		assertThat(repository.exists(userPool.getQueueNumber())).isEqualTo(true);

		UserPool found = repository.findOne(userPool.getQueueNumber());
		assertThat(found.getGroupId()).isEqualTo(CHANGED_ID);
		assertThat(found.getFishingDate().getTime()).isEqualTo(TODAY_MILLIS);
	}

	@Test
	public void updateSucceeds() throws Exception {
		UserPool userPool = new UserPool(CHANGED_ID);
		userPool.setQueueNumber(QUEUE_NO1);
		userPool = repository.save(userPool);

		UserPool found = repository.findOne(userPool.getQueueNumber());
		assertThat(found.getGroupId()).isEqualTo(CHANGED_ID);
	}

	@Test
	public void deleteSucceeds() {
		repository.delete(QUEUE_NO1);
		assertThat(repository.exists(QUEUE_NO1)).isEqualTo(false);
	}

	@Test
	public void deleteByGroupIdSucceeds() {
		assertThat(repository.count()).isEqualTo(3);
		int deleted = repository.deleteAllByGroupId(GROUP_ID1);
		assertThat(deleted).isEqualTo(1);
		assertThat(repository.count()).isEqualTo(2);
	}
}

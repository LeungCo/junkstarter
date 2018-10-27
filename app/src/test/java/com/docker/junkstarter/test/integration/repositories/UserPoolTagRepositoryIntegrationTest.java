package com.docker.junkstarter.test.integration.repositories;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.UUID;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;

import com.docker.junkstarter.model.UserPoolTag;
import com.docker.junkstarter.repositories.UserPoolTagRepository;
import com.docker.junkstarter.test.core.RepositoryTest;
import com.github.springtestdbunit.annotation.DatabaseSetup;

@DatabaseSetup("/userPoolTags.xml")
public class UserPoolTagRepositoryIntegrationTest extends RepositoryTest  {

	private static final Long QUEUE_NO1 = 1L;

	private static final UUID USER_POOL_TAG_ID1 = UUID.fromString("fb3033e1-c4b7-481e-b9b7-187e77a06ea9");

	private static final UUID TAG_ID1 = UUID.fromString("8ed9e0ca-90a8-4818-bf68-c84bc0b44f10");
	private static final UUID TAG_ID2 = UUID.fromString("cb60fe3a-40cf-4351-be8e-e063d5879367");
	private static final UUID TAG_ID3 = UUID.fromString("fffebf83-e6fa-4d0c-947d-c48c94774308");

	private static final UUID CHANGED_ID = UUID.fromString("00e1a507-c9cf-429f-a06e-5dfb895abfbb");

	@Autowired
	private UserPoolTagRepository repository;

	@Test
	public void findByGroupIdSucceeds() {
		List<UserPoolTag> results = repository.findByQueueNumber(QUEUE_NO1);
		assertThat(results.size()).isEqualTo(3);
	}

	@Test
	public void findOneSucceeds() {
		UserPoolTag found = repository.findOne(USER_POOL_TAG_ID1);
		assertThat(found.getTagId()).isEqualTo(TAG_ID1);
	}

	@Test
	public void findAllSucceeds() {
		List<UserPoolTag> results = repository.findAll(new Sort(Sort.Direction.ASC, "tagId"));

		assertThat(results.size()).isEqualTo(3);

		UserPoolTag result = results.get(0);
		assertThat(result.getTagId()).isEqualTo(TAG_ID1);

		UserPoolTag result2 = results.get(1);
		assertThat(result2.getTagId()).isEqualTo(TAG_ID2);

		UserPoolTag result3 = results.get(2);
		assertThat(result3.getTagId()).isEqualTo(TAG_ID3);
	}

	@Test
	public void createSucceeds() {
		UserPoolTag userPool = new UserPoolTag(2L, CHANGED_ID);
		userPool = repository.saveAndFlush(userPool);
		assertThat(repository.exists(userPool.getUserPoolTagId())).isEqualTo(true);

		UserPoolTag found = repository.findOne(userPool.getUserPoolTagId());
		assertThat(found.getQueueNumber()).isEqualTo(2L);
		assertThat(found.getTagId()).isEqualTo(CHANGED_ID);
	}

	@Test
	public void deleteSucceeds() {
		repository.delete(USER_POOL_TAG_ID1);
		assertThat(repository.exists(USER_POOL_TAG_ID1)).isEqualTo(false);
	}

	@Test
	public void deleteByQueueNumberSucceeds() {
		assertThat(repository.count()).isEqualTo(3);
		int deleted = repository.deleteAllByQueueNumber(1L);
		assertThat(deleted).isEqualTo(3);
		assertThat(repository.count()).isEqualTo(0);
	}
}

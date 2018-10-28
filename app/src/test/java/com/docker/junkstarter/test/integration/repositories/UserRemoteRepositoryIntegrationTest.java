package com.docker.junkstarter.test.integration.repositories;

import static com.docker.junkstarter.util.DateUtility.getDateMillis;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.UUID;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Sort;

import com.docker.junkstarter.model.UserRemote;
import com.docker.junkstarter.repositories.UserRemoteRepository;
import com.docker.junkstarter.test.core.RepositoryTest;
import com.github.springtestdbunit.annotation.DatabaseSetup;

@DatabaseSetup("/userRemotes.xml")
public class UserRemoteRepositoryIntegrationTest extends RepositoryTest  {

	private static final UUID ID_1 = UUID.fromString("667fd724-2ac5-466b-ab85-948f42b0d372");

	private static final UUID USER_ID1 = UUID.fromString("8ed9e0ca-90a8-4818-bf68-c84bc0b44f10");
	private static final UUID USER_ID2 = UUID.fromString("cb60fe3a-40cf-4351-be8e-e063d5879367");
	private static final UUID USER_ID3 = UUID.fromString("fffebf83-e6fa-4d0c-947d-c48c94774308");
	private static final UUID ID_CHANGED = UUID.fromString("07934535-93e5-4cbb-b2a2-b0d9eb6970d3");

	private static final UUID REMOTE_USER_ID1 = UUID.fromString("245365b2-8861-4d3c-941c-a2e55425d2b0");

	@Autowired
	private UserRemoteRepository repository;

	@Test
	public void findByUserIdSucceeds() {
		UserRemote found = repository.findByUserId(USER_ID1);
		assertThat(found.getRemoteId()).isEqualTo(REMOTE_USER_ID1);
	}

	@Test
	public void findByRemoteIdSucceeds() {
		UserRemote found = repository.findByRemoteId(REMOTE_USER_ID1);
		assertThat(repository.exists(found.getId())).isEqualTo(true);
		assertThat(found.getUserId()).isEqualTo(USER_ID1);
		assertThat(found.getId()).isEqualTo(ID_1);

	}

	@Test
	public void findByOneSucceeds() {
		UserRemote found = repository.findOne(ID_1);
		assertThat(repository.exists(found.getId())).isEqualTo(true);
		assertThat(found.getUserId()).isEqualTo(USER_ID1);
		assertThat(found.getRemoteId()).isEqualTo(REMOTE_USER_ID1);
	}

	@Test
	public void findAllSucceeds() {
		List<UserRemote> results = repository.findAll(new Sort(Sort.Direction.ASC, "userId"));

		assertThat(results.size()).isEqualTo(3);

		UserRemote result = results.get(0);
		assertThat(result.getUserId()).isEqualTo(USER_ID1);

		UserRemote result2 = results.get(1);
		assertThat(result2.getUserId()).isEqualTo(USER_ID2);

		UserRemote result3 = results.get(2);
		assertThat(result3.getUserId()).isEqualTo(USER_ID3);
	}

	@Test
	public void createSucceeds() {
		UserRemote userRemote = new UserRemote(REMOTE_USER_ID1, USER_ID1);
		userRemote = repository.saveAndFlush(userRemote);
		assertThat(repository.exists(userRemote.getId())).isEqualTo(true);

		UserRemote found = repository.findOne(userRemote.getId());
		assertThat(found.getUserId()).isEqualTo(REMOTE_USER_ID1);
		assertThat(found.getRemoteId()).isEqualTo(USER_ID1);
		assertThat(found.getCreatedAt().getTime()).isEqualTo(TODAY_MILLIS);
		assertThat(found.getModifiedAt().getTime()).isEqualTo(TODAY_MILLIS);
	}

	@Test(expected = DataIntegrityViolationException.class)
	public void userIdMustBeUnique() {
		UserRemote userRemote = new UserRemote(USER_ID1, REMOTE_USER_ID1);
		userRemote = repository.saveAndFlush(userRemote);
	}

	@Test(expected = DataIntegrityViolationException.class)
	public void remoteIdMustBeUnique() {
		UserRemote userRemote = new UserRemote(REMOTE_USER_ID1, REMOTE_USER_ID1);
		userRemote = repository.saveAndFlush(userRemote);
	}

	@Test
	public void updateSucceeds() throws Exception {
		UserRemote userRemote = new UserRemote(ID_CHANGED, ID_CHANGED);
		userRemote.setId(ID_1);
		userRemote = repository.save(userRemote);

		UserRemote found = repository.findOne(userRemote.getId());
		assertThat(found.getUserId()).isEqualTo(ID_CHANGED);
		assertThat(found.getRemoteId()).isEqualTo(ID_CHANGED);
		assertThat(found.getCreatedAt().getTime()).isEqualTo(getDateMillis("2018-01-01"));
		assertThat(found.getModifiedAt().getTime()).isEqualTo(TODAY_MILLIS);
	}

	@Test
	public void deleteSucceeds() {
		assertThat(repository.exists(ID_1)).isEqualTo(true);
		repository.delete(ID_1);
		assertThat(repository.exists(ID_1)).isEqualTo(false);
	}

	@Test
	public void deleteByUserIdSucceeds() {
		assertThat(repository.exists(ID_1)).isEqualTo(true);
		repository.deleteByUserId(USER_ID1);
		assertThat(repository.exists(ID_1)).isEqualTo(false);
	}

	@Test
	public void deleteByRemoteIdSucceeds() {
		assertThat(repository.exists(ID_1)).isEqualTo(true);
		repository.deleteByRemoteId(REMOTE_USER_ID1);
		assertThat(repository.exists(ID_1)).isEqualTo(false);
	}
}

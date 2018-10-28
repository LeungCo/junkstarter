package com.docker.junkstarter.test.integration.repositories;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.UUID;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;

import com.docker.junkstarter.enums.UserPoolHistoryType;
import com.docker.junkstarter.model.UserPoolHistory;
import com.docker.junkstarter.repositories.UserPoolHistoryRepository;
import com.docker.junkstarter.test.core.RepositoryTest;
import com.github.springtestdbunit.annotation.DatabaseSetup;

@DatabaseSetup("/userPoolHistory.xml")
public class UserPoolHistoryRepositoryIntegrationTest extends RepositoryTest  {

	private static final UUID USER_POOL_HISTORY_ID1 = UUID.fromString("72cdb42c-b4cf-4204-99b4-2e0f62b9e811");
	private static final UUID USER_POOL_HISTORY_ID2 = UUID.fromString("342bbc64-ece5-4fe5-a071-93a19af563ff");
	private static final UUID USER_POOL_HISTORY_ID3 = UUID.fromString("32288aa9-2794-4d10-ab2a-533399ae6031");

	private static final UUID USER_POOL_ID1 = UUID.fromString("8ed9e0ca-90a8-4818-bf68-c84bc0b44f10");

	private static final UUID CHANGED_ID = UUID.fromString("00e1a507-c9cf-429f-a06e-5dfb895abfbb");

	@Autowired
	private UserPoolHistoryRepository repository;

	@Test
	public void findAllByUserPoolHistoryIducceeds() {
		List<UserPoolHistory> results = repository.findAllByUserPoolId(USER_POOL_ID1);
		assertThat(results.size()).isEqualTo(3);
	}

	@Test
	public void findOneSucceeds() {
		UserPoolHistory found = repository.findOne(USER_POOL_HISTORY_ID1);
		assertThat(found.getUserPoolId()).isEqualTo(USER_POOL_ID1);
		assertThat(found.getType()).isEqualTo(UserPoolHistoryType.START);
		assertThat(repository.exists(USER_POOL_HISTORY_ID1)).isEqualTo(true);
	}

	@Test
	public void findAllSucceeds() {
		List<UserPoolHistory> results = repository.findAll(new Sort(Sort.Direction.ASC, "userPoolId"));

		assertThat(results.size()).isEqualTo(3);

		UserPoolHistory result = results.get(0);
		assertThat(result.getUserPoolHistoryId()).isEqualTo(USER_POOL_HISTORY_ID1);

		UserPoolHistory result2 = results.get(1);
		assertThat(result2.getUserPoolHistoryId()).isEqualTo(USER_POOL_HISTORY_ID2);

		UserPoolHistory result3 = results.get(2);
		assertThat(result3.getUserPoolHistoryId()).isEqualTo(USER_POOL_HISTORY_ID3);
	}

	@Test
	public void createSucceeds() {
		UserPoolHistory userPoolHistory = new UserPoolHistory(USER_POOL_ID1);

		userPoolHistory = repository.saveAndFlush(userPoolHistory);
		assertThat(repository.exists(userPoolHistory.getUserPoolHistoryId())).isEqualTo(true);

		UserPoolHistory found = repository.findOne(userPoolHistory.getUserPoolHistoryId());
		assertThat(found.getCount()).isEqualTo(1);
		assertThat(found.getType()).isEqualTo(UserPoolHistoryType.START);
		assertThat(found.getUserPoolId()).isEqualTo(USER_POOL_ID1);
		assertThat(found.getCreatedAt().getTime()).isEqualTo(TODAY_MILLIS);
		assertThat(found.getModifiedAt().getTime()).isEqualTo(TODAY_MILLIS);
	}

	@Test
	public void updateSucceeds() throws Exception {
		UserPoolHistory userPoolHistory = new UserPoolHistory(CHANGED_ID);
		userPoolHistory.setUserPoolHistoryId(USER_POOL_HISTORY_ID1);
		userPoolHistory.setCount(10);
		userPoolHistory = repository.save(userPoolHistory);

		UserPoolHistory found = repository.findOne(userPoolHistory.getUserPoolHistoryId());
		assertThat(found.getUserPoolId()).isEqualTo(CHANGED_ID);
		assertThat(found.getCount()).isEqualTo(10);
	}

	@Test
	public void deleteSucceeds() {
		repository.delete(USER_POOL_HISTORY_ID1);
		assertThat(repository.exists(USER_POOL_HISTORY_ID1)).isEqualTo(false);
	}

	@Test
	public void deleteAllByUserPoolIdSucceeds() {
		assertThat(repository.count()).isEqualTo(3);
		int deleted = repository.deleteAllByUserPoolId(USER_POOL_ID1);
		assertThat(deleted).isEqualTo(3);
		assertThat(repository.count()).isEqualTo(0);
	}
}

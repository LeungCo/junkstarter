package com.docker.junkstarter.test.integration.repositories;

import static com.docker.junkstarter.util.DateUtility.getDateMillis;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.UUID;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;

import com.docker.junkstarter.model.UserGroup;
import com.docker.junkstarter.repositories.UserGroupRepository;
import com.docker.junkstarter.test.core.RepositoryTest;
import com.github.springtestdbunit.annotation.DatabaseSetup;

@DatabaseSetup("/groups.xml")
public class UserGroupRepositoryIntegrationTest extends RepositoryTest {

	private static final UUID GROUP_ID1 = UUID.fromString("667fd724-2ac5-466b-ab85-948f42b0d372");
	private static final UUID GROUP_ID2 = UUID.fromString("46c394cf-55f1-4254-93c7-d054b12f522e");
	private static final UUID GROUP_ID3 = UUID.fromString("fa4bdfd1-6eec-428b-8117-e4a09a71c6c2");

	private static final UUID OWNER_ID1 = UUID.fromString("8ed9e0ca-90a8-4818-bf68-c84bc0b44f10");
	private static final UUID GROUP_PROFILE_ID1 = UUID.fromString("667fd724-2ac5-466b-ab85-948f42b0d372");

	private static final UUID CHANGED_ID = UUID.fromString("667fd724-2ac5-466b-ab85-948f42b0d372");

	@Autowired
	private UserGroupRepository repository;

	@Test
	public void findByOwnerIdSucceeds() {
		List<UserGroup> results = repository.findAllByOwnerId(OWNER_ID1);
		assertThat(results.size()).isEqualTo(3);
	}

	@Test
	public void findOneSucceeds() {
		UserGroup found = repository.findOne(GROUP_ID1);
		assertThat(found.getOwnerId()).isEqualTo(OWNER_ID1);
		assertThat(repository.exists(GROUP_ID1)).isEqualTo(true);
	}

	@Test
	public void findAllSucceeds() {
		List<UserGroup> results = repository.findAll(new Sort(Sort.Direction.ASC, "ownerId"));

		assertThat(results.size()).isEqualTo(3);

		UserGroup result = results.get(0);
		assertThat(result.getGroupId()).isEqualTo(GROUP_ID1);

		UserGroup result2 = results.get(1);
		assertThat(result2.getGroupId()).isEqualTo(GROUP_ID2);

		UserGroup result3 = results.get(2);
		assertThat(result3.getGroupId()).isEqualTo(GROUP_ID3);
	}

	@Test
	public void createSucceeds() {
		UserGroup group = new UserGroup(OWNER_ID1, GROUP_ID1);
		group = repository.saveAndFlush(group);
		assertThat(repository.exists(group.getGroupId())).isEqualTo(true);

		UserGroup found = repository.findOne(group.getGroupId());
		assertThat(found.getOwnerId()).isEqualTo(OWNER_ID1);
		assertThat(found.getGroupProfileId()).isEqualTo(GROUP_PROFILE_ID1);
		assertThat(found.getCreatedAt().getTime()).isEqualTo(TODAY_MILLIS);
		assertThat(found.getModifiedAt().getTime()).isEqualTo(TODAY_MILLIS);
	}

	@Test
	public void updateSucceeds() throws Exception {
		UserGroup group = new UserGroup(CHANGED_ID, CHANGED_ID);
		group.setGroupId(GROUP_ID1);
		group = repository.save(group);

		UserGroup found = repository.findOne(group.getGroupId());
		assertThat(found.getOwnerId()).isEqualTo(CHANGED_ID);
		assertThat(found.getGroupProfileId()).isEqualTo(CHANGED_ID);
		assertThat(found.getCreatedAt().getTime()).isEqualTo(getDateMillis("2018-01-01"));
		assertThat(found.getModifiedAt().getTime()).isEqualTo(TODAY_MILLIS);
	}

	@Test
	public void deleteSucceeds() {
		repository.delete(GROUP_ID1);
		assertThat(repository.exists(GROUP_ID1)).isEqualTo(false);
	}

	@Test
	public void deleteByEmailSucceeds() {
		assertThat(repository.count()).isEqualTo(3);
		int deleted = repository.deleteAllByOwnerId(OWNER_ID1);
		assertThat(deleted).isEqualTo(3);
		assertThat(repository.count()).isEqualTo(0);
	}
}

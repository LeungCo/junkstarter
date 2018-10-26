package com.docker.junkstarter.test.integration;

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

import com.docker.junkstarter.model.UserGroupMember;
import com.docker.junkstarter.repositories.UserGroupMemberRepository;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;

@SpringBootTest
@RunWith(SpringRunner.class)
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class, DbUnitTestExecutionListener.class })
@DatabaseSetup("/groupMembers.xml")
public class UserGroupMemberRepositoryIntegrationTest {

	private static final UUID GROUP_MEMBER_ID1 = UUID.fromString("667fd724-2ac5-466b-ab85-948f42b0d372");
	private static final UUID GROUP_MEMBER_ID2 = UUID.fromString("46c394cf-55f1-4254-93c7-d054b12f522e");
	private static final UUID GROUP_MEMBER_ID3 = UUID.fromString("fa4bdfd1-6eec-428b-8117-e4a09a71c6c2");

	private static final UUID GROUP_ID1 = UUID.fromString("8ed9e0ca-90a8-4818-bf68-c84bc0b44f10");
	private static final UUID USER_ID = UUID.fromString("667fd724-2ac5-466b-ab85-948f42b0d372");

	private static final UUID CHANGED_ID = UUID.fromString("667fd724-2ac5-466b-ab85-948f42b0d372");

	@Autowired
	private UserGroupMemberRepository repository;

	private static long TODAY_MILLIS = getDateMillis("2018-05-20");

	@Before
	public void setup() throws Exception {
		DateTimeUtils.setCurrentMillisFixed(TODAY_MILLIS);
	}

	@Test
	public void findByGroupIdSucceeds() {
		List<UserGroupMember> results = repository.findByGroupId(GROUP_ID1);
		assertThat(results.size()).isEqualTo(3);
	}

	@Test
	public void findOneSucceeds() {
		UserGroupMember found = repository.findOne(GROUP_MEMBER_ID1);
		assertThat(found.getGroupId()).isEqualTo(GROUP_ID1);
		assertThat(repository.exists(GROUP_MEMBER_ID1)).isEqualTo(true);
	}

	@Test
	public void findAllSucceeds() {
		List<UserGroupMember> results = repository.findAll(new Sort(Sort.Direction.ASC, "groupId"));

		assertThat(results.size()).isEqualTo(3);

		UserGroupMember result = results.get(0);
		assertThat(result.getGroupMemberId()).isEqualTo(GROUP_MEMBER_ID1);

		UserGroupMember result2 = results.get(1);
		assertThat(result2.getGroupMemberId()).isEqualTo(GROUP_MEMBER_ID2);

		UserGroupMember result3 = results.get(2);
		assertThat(result3.getGroupMemberId()).isEqualTo(GROUP_MEMBER_ID3);
	}

	@Test
	public void createSucceeds() {
		UserGroupMember group = new UserGroupMember(GROUP_ID1, GROUP_MEMBER_ID1);
		group = repository.saveAndFlush(group);
		assertThat(repository.exists(group.getGroupMemberId())).isEqualTo(true);

		UserGroupMember found = repository.findOne(group.getGroupMemberId());
		assertThat(found.getGroupId()).isEqualTo(GROUP_ID1);
		assertThat(found.getUserId()).isEqualTo(USER_ID);
		assertThat(found.getCreatedAt().getTime()).isEqualTo(TODAY_MILLIS);
		assertThat(found.getModifiedAt().getTime()).isEqualTo(TODAY_MILLIS);
	}

	@Test
	public void updateSucceeds() throws Exception {
		UserGroupMember group = new UserGroupMember(CHANGED_ID, CHANGED_ID);
		group.setGroupMemberId(GROUP_MEMBER_ID1);
		group = repository.save(group);

		UserGroupMember found = repository.findOne(group.getGroupId());
		assertThat(found.getGroupId()).isEqualTo(CHANGED_ID);
		assertThat(found.getUserId()).isEqualTo(CHANGED_ID);
		assertThat(found.getCreatedAt().getTime()).isEqualTo(getDateMillis("2018-01-01"));
		assertThat(found.getModifiedAt().getTime()).isEqualTo(TODAY_MILLIS);
	}

	@Test
	public void deleteSucceeds() {
		repository.delete(GROUP_MEMBER_ID1);
		assertThat(repository.exists(GROUP_MEMBER_ID1)).isEqualTo(false);
	}

	@Test
	public void deleteByEmailSucceeds() {
		assertThat(repository.count()).isEqualTo(3);
		int deleted = repository.deleteAllByGroupId(GROUP_ID1);
		assertThat(deleted).isEqualTo(3);
		assertThat(repository.count()).isEqualTo(0);
	}
}

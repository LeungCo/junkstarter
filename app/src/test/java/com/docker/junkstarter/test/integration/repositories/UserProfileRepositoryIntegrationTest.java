package com.docker.junkstarter.test.integration.repositories;

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

import com.docker.junkstarter.enums.UserGender;
import com.docker.junkstarter.model.UserProfile;
import com.docker.junkstarter.repositories.UserProfileRepository;
import com.docker.junkstarter.test.core.RepositoryTest;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;

@DatabaseSetup("/userProfiles.xml")
public class UserProfileRepositoryIntegrationTest extends RepositoryTest  {

	private static final UUID USER_PROFILE_ID1 = UUID.fromString("667fd724-2ac5-466b-ab85-948f42b0d372");
	private static final UUID USER_PROFILE_ID2 = UUID.fromString("6654b8df-7ad8-4732-8f8a-11d9870404e9");
	private static final UUID USER_PROFILE_ID3 = UUID.fromString("fa4bdfd1-6eec-428b-8117-e4a09a71c6c2");

	private static final UUID USER_ID1 = UUID.fromString("a0a33c87-ee45-4a37-90af-54106b509c28");

	@Autowired
	private UserProfileRepository repository;

	@Test
	public void findByEmailSucceeds() {
		UserProfile found = repository.findByUserId(USER_ID1);
		assertThat(found.getUserProfileId()).isEqualTo(USER_PROFILE_ID1);
	}

	@Test
	public void findOneSucceeds() {
		UserProfile found = repository.findOne(USER_PROFILE_ID1);
		assertThat(found.getUserId()).isEqualTo(USER_ID1);
		assertThat(repository.exists(USER_PROFILE_ID1)).isEqualTo(true);
	}

	@Test
	public void findAllSucceeds() {
		List<UserProfile> results = repository.findAll(new Sort(Sort.Direction.ASC, "userId"));
		assertThat(results.size()).isEqualTo(3);

		UserProfile result = results.get(0);
		assertThat(result.getUserProfileId()).isEqualTo(USER_PROFILE_ID1);

		UserProfile result2 = results.get(1);
		assertThat(result2.getUserProfileId()).isEqualTo(USER_PROFILE_ID3);

		UserProfile result3 = results.get(2);
		assertThat(result3.getUserProfileId()).isEqualTo(USER_PROFILE_ID2);
	}

	@Test
	public void createSucceeds() {
		UserProfile userProfile = new UserProfile(USER_PROFILE_ID1);
		userProfile = repository.saveAndFlush(userProfile);
		assertThat(repository.exists(userProfile.getUserProfileId())).isEqualTo(true);

		UserProfile found = repository.findOne(userProfile.getUserProfileId());
		assertThat(found.getUserId()).isEqualTo(USER_PROFILE_ID1);
		assertThat(found.getBlurb()).isNull();
		assertThat(found.getDateOfBirth()).isNull();
		assertThat(found.getGender()).isEqualTo(UserGender.PREFER_NOT_SAY);
		assertThat(found.getProfilePictureId()).isNull();
		assertThat(found.getCreatedAt().getTime()).isEqualTo(TODAY_MILLIS);
		assertThat(found.getModifiedAt().getTime()).isEqualTo(TODAY_MILLIS);
	}

	@Test
	public void updateSucceeds() throws Exception {
		UserProfile userProfile = new UserProfile(USER_PROFILE_ID1);
		userProfile.setUserProfileId(USER_PROFILE_ID1);
		userProfile.setDateOfBirth("20180607");
		userProfile.setBlurb("change");
		userProfile.setGender(UserGender.FEMALE);
		userProfile.setDisplayName("change");
		userProfile = repository.save(userProfile);

		UserProfile found = repository.findOne(userProfile.getUserProfileId());
		assertThat(found.getUserId()).isEqualTo(USER_PROFILE_ID1);
		assertThat(found.getDateOfBirth()).isEqualTo("20180607");
		assertThat(found.getBlurb()).isEqualTo("change");
		assertThat(found.getGender()).isEqualTo(UserGender.FEMALE);
		assertThat(found.getDisplayName()).isEqualTo("change");
		assertThat(found.getCreatedAt().getTime()).isEqualTo(getDateMillis("2018-01-01"));
		assertThat(found.getModifiedAt().getTime()).isEqualTo(TODAY_MILLIS);
	}

	@Test
	public void deleteSucceeds() {
		repository.delete(USER_PROFILE_ID1);
		assertThat(repository.exists(USER_PROFILE_ID1)).isEqualTo(false);
	}

	@Test
	public void deleteByUserIdSucceeds() {
		assertThat(repository.exists(USER_PROFILE_ID1)).isEqualTo(true);
		repository.deleteByUserId(USER_ID1);
		assertThat(repository.exists(USER_PROFILE_ID1)).isEqualTo(false);
	}
}

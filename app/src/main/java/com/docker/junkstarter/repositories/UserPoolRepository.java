package com.docker.junkstarter.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.docker.junkstarter.model.UserGroup;
import com.docker.junkstarter.model.UserPool;

@Repository
@Transactional
public interface UserPoolRepository extends JpaRepository<UserPool, Long> {

	@Query("SELECT e FROM UserPool e WHERE e.groupId = :groupId")
	UserPool findByGroupId(@Param("groupId") UUID groupId);

	@Modifying
	@Query("DELETE FROM UserPool e WHERE e.groupId =:#{#groupId}")
	int deleteAllByGroupId(@Param("groupId") UUID groupId);

	@Modifying
	@Query("DELETE FROM UserPool e WHERE e.fishingDate =:#{#fishingDate}")
	int deleteAllByFishingDate(@Param("fishingDate") UUID fishingDate);
}

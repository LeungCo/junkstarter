package com.docker.junkstarter.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.docker.junkstarter.model.User;
import com.docker.junkstarter.model.UserProfile;

@Repository
@Transactional
public interface UserProfileRepository extends JpaRepository<UserProfile, UUID> {

	@Query("SELECT e FROM UserProfile e WHERE e.userId = :userId")
	UserProfile findByUserId(@Param("userId") UUID userId);

	@Modifying
	@Query("DELETE FROM UserProfile e WHERE e.userId =:#{#userId}")
	int deleteByUserId(@Param("userId") UUID userId);
}

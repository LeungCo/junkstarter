package com.docker.junkstarter.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.docker.junkstarter.model.UserRemote;

@Repository
@Transactional
public interface UserRemoteRepository extends JpaRepository<UserRemote, UUID> {

	@Query("SELECT e FROM UserRemote e WHERE e.userId = :userId")
	UserRemote findByUserId(@Param("userId") UUID userId);

	@Query("SELECT e FROM UserRemote e WHERE e.remoteId = :remoteId")
	UserRemote findByRemoteId(@Param("remoteId") UUID remoteId);

	@Modifying
	@Query("DELETE FROM UserRemote e WHERE e.userId =:#{#userId}")
	int deleteByUserId(@Param("userId") UUID userId);

	@Modifying
	@Query("DELETE FROM UserRemote e WHERE e.remoteId =:#{#remoteId}")
	int deleteByRemoteId(@Param("remoteId") UUID remoteId);
}

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

@Repository
@Transactional
public interface UserGroupRepository extends JpaRepository<UserGroup, UUID> {

	@Query("SELECT e FROM UserGroup e WHERE e.ownerId = :ownerId")
	List<UserGroup> findAllByOwnerId(@Param("ownerId") UUID ownerId);

	@Modifying
	@Query("DELETE FROM UserGroup e WHERE e.ownerId =:#{#ownerId}")
	int deleteAllByOwnerId(@Param("ownerId") UUID ownerId);
}

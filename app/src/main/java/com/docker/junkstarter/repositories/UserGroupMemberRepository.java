package com.docker.junkstarter.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.docker.junkstarter.model.UserGroupMember;

@Repository
@Transactional
public interface UserGroupMemberRepository extends JpaRepository<UserGroupMember, UUID> {

	@Query("SELECT e FROM UserGroupMember e WHERE e.groupId = :groupId")
	List<UserGroupMember> findAllByGroupId(@Param("groupId") UUID groupId);

	@Query("SELECT e FROM UserGroupMember e WHERE e.userId = :userId")
	List<UserGroupMember> findAllByUserId(@Param("userId") UUID userId);

	@Modifying
	@Query("DELETE FROM UserGroupMember e WHERE e.groupId =:#{#groupId}")
	int deleteAllByGroupId(@Param("groupId") UUID groupId);

	@Modifying
	@Query("DELETE FROM UserGroupMember e WHERE e.userId =:#{#userId}")
	int deleteAllByUserId(@Param("userId") UUID userId);
}

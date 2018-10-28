package com.docker.junkstarter.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.docker.junkstarter.model.UserPoolHistory;

@Repository
@Transactional
public interface UserPoolHistoryRepository extends JpaRepository<UserPoolHistory, UUID> {

	@Query("SELECT e FROM UserPoolHistory e WHERE e.userPoolId = :userPoolId")
	List<UserPoolHistory> findAllByUserPoolId(@Param("userPoolId") UUID userPoolId);

	@Modifying
	@Query("DELETE FROM UserPoolHistory e WHERE e.userPoolId =:#{#userPoolId}")
	int deleteAllByUserPoolId(@Param("userPoolId") UUID userPoolId);
}

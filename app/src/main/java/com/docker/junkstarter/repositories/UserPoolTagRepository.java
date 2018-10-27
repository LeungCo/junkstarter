package com.docker.junkstarter.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.docker.junkstarter.model.UserPoolTag;

@Repository
@Transactional
public interface UserPoolTagRepository extends JpaRepository<UserPoolTag, UUID> {

	@Query("SELECT e FROM UserPoolTag e WHERE e.queueNumber = :queueNumber")
	List<UserPoolTag> findByQueueNumber(@Param("queueNumber") Long queueNumber);

	@Modifying
	@Query("DELETE FROM UserPoolTag e WHERE e.queueNumber =:#{#queueNumber}")
	int deleteAllByQueueNumber(@Param("queueNumber") Long queueNumber);
}

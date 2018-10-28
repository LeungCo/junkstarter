package com.docker.junkstarter.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.docker.junkstarter.model.User;

@Repository
@Transactional
public interface UserRepository extends JpaRepository<User, UUID> {
	@Query("SELECT e FROM User e WHERE e.email = :email")
	User findByEmail(@Param("email") String email);

	@Modifying
	@Query("DELETE FROM User e WHERE e.email =:#{#email}")
	int deleteByEmail(@Param("email") String email);
}

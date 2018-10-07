package com.docker.junkstarter.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.docker.junkstarter.model.Event;

@Repository
@Transactional
public interface EventRepository extends JpaRepository<Event, UUID> {
	
	Event findByName(String name);
}

package com.docker.junkstarter.repositories;

import java.util.ArrayList;
import java.util.List;

import org.jooq.Record;
import org.jooq.TableField;
import org.jooq.UpdatableRecord;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.docker.junkstarter.entity.Event;
import com.docker.junkstarter.jooq.tables.records.EventRecord;

@Repository
@Transactional
public class EventRepository extends AbstractJooqRepository {

	@Transactional(readOnly = true)
	public List<Event> findAll() {

		List<Event> events = new ArrayList<>();

		List<EventRecord> queryResults = dsl.selectFrom(com.docker.junkstarter.jooq.tables.Event.EVENT)
				.fetchInto(EventRecord.class);

		for (EventRecord queryResult : queryResults) {
			Event event = new Event(queryResult);
			events.add(event);
		}

		return events;
	}

	@Transactional
	public Event create(Event event) {
		this.create(event, com.docker.junkstarter.jooq.tables.Event.EVENT);
		return event;
	}

	@Transactional
	public int delete(Event event) {
		return dsl.fetchOne(com.docker.junkstarter.jooq.tables.Event.EVENT,
				com.docker.junkstarter.jooq.tables.Event.EVENT.EVENTID.eq(event.getEventId())).delete();
	}

	@Transactional
	public Event update(Event event) {
		final UpdatableRecord inputRec = (UpdatableRecord) dsl.fetchOne(com.docker.junkstarter.jooq.tables.Event.EVENT,
				((TableField<Record, Long>) com.docker.junkstarter.jooq.tables.Event.EVENT.field("id"))
						.eq(Long.parseLong(event.getEventId().toString())));
		inputRec.from(event);
		inputRec.store();
		return (Event) inputRec;
	}

}

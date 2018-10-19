package com.docker.junkstarter.repositories;

import java.util.HashMap;
import java.util.Map;

import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.TableField;
import org.jooq.impl.TableImpl;
import org.springframework.beans.factory.annotation.Autowired;

import com.docker.junkstarter.entity.Entity;

public abstract class AbstractJooqRepository {

	@Autowired
	protected DSLContext dsl;

	protected Map<String, Object> convertToMap(Record record, TableField... fields) {
		Map<String, Object> map = new HashMap<>();
		for (TableField f : fields) {
			map.put(f.getName(), record.getValue(f));
		}
		return map;
	}

	protected <T extends Record> T create(Entity entity, TableImpl<? extends Record> table) {
		final Record inputRec = dsl.newRecord(table);
		inputRec.from(entity);
		return (T) dsl.insertInto(table).set(inputRec).returning().fetchOne();
	}

//	protected abstract E convertQueryTOModelObject(R record);

}

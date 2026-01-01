package com.aji.agent.repository;

import com.aji.agent.entity.Timetable;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TimetableRepository
        extends MongoRepository<Timetable, String> {
}

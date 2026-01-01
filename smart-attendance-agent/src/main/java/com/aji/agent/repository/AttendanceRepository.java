package com.aji.agent.repository;

import com.aji.agent.entity.AttendanceRecord;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface AttendanceRepository
        extends MongoRepository<AttendanceRecord, String> {

    List<AttendanceRecord> findBySubjectName(String subjectName);
}

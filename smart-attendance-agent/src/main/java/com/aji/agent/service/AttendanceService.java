package com.aji.agent.service;

import com.aji.agent.entity.AttendanceRecord;
import com.aji.agent.repository.AttendanceRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AttendanceService {

    private final AttendanceRepository repo;

    public AttendanceService(AttendanceRepository repo) {
        this.repo = repo;
    }

    public double getAttendancePercent(String subject) {

        List<AttendanceRecord> records = repo.findBySubjectName(subject);

        if (records.isEmpty()) return 100;

        long total = records.size();
        long present = records.stream()
                .filter(AttendanceRecord::isPresent)
                .count();

        return (present * 100.0) / total;
    }
    public List<AttendanceRecord> getRecordsBySubject(String subject) {
        return repo.findBySubjectName(subject);
    }
}

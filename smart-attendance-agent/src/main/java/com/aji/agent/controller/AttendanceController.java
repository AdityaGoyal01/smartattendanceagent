package com.aji.agent.controller;

import com.aji.agent.entity.AttendanceRecord;
import com.aji.agent.repository.AttendanceRepository;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/attendance")
public class AttendanceController {

    private final AttendanceRepository repo;

    public AttendanceController(AttendanceRepository repo) {
        this.repo = repo;
    }

    @PostMapping
    public AttendanceRecord mark(@RequestBody AttendanceRecord record) {
        return repo.save(record);
    }
}

package com.aji.agent.controller;

import com.aji.agent.entity.Timetable;
import com.aji.agent.repository.TimetableRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/timetable")
public class TimetableController {

    private final TimetableRepository repo;

    public TimetableController(TimetableRepository repo) {
        this.repo = repo;
    }

    @PostMapping
    public Timetable add(@RequestBody Timetable t) {
        return repo.save(t);
    }

    @GetMapping
    public List<Timetable> all() {
        return repo.findAll();
    }
}

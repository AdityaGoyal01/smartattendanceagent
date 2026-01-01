package com.aji.agent.controller;

import com.aji.agent.service.AgentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/agent")
public class AgentController {

    private final AgentService agentService;

    public AgentController(AgentService agentService) {
        this.agentService = agentService;
    }

    @PostMapping("/recommend")
    public ResponseEntity<String> recommend(@RequestBody List<String> subjects) {
        String result = agentService.generateRecommendation(subjects);
        return ResponseEntity.ok(result);
    }
}

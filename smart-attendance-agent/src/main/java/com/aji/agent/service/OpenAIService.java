package com.aji.agent.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
public class OpenAIService {

    @Value("${openai.api-key}")
    private String apiKey;

    @Value("${openai.url}")
    private String apiUrl;

    private final RestTemplate restTemplate = new RestTemplate();

    public String getAIResponse(String prompt) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(apiKey);

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("model", "gpt-4o-mini");

        List<Map<String, String>> messages = new ArrayList<>();
        messages.add(Map.of(
                "role", "system",
                "content", "You are a smart attendance advisor."
        ));
        messages.add(Map.of(
                "role", "user",
                "content", prompt
        ));

        requestBody.put("messages", messages);

        HttpEntity<Map<String, Object>> request =
                new HttpEntity<>(requestBody, headers);

        ResponseEntity<Map> response =
                restTemplate.postForEntity(apiUrl, request, Map.class);

        Map<?, ?> choice =
                (Map<?, ?>) ((List<?>) response.getBody().get("choices")).get(0);

        Map<?, ?> message =
                (Map<?, ?>) choice.get("message");

        return message.get("content").toString();
    }
}


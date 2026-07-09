package com.financialos.controller;

import com.financialos.ai.AIService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ai")
public class AIController {

    private final AIService aiService;

    public AIController(AIService aiService) {
        this.aiService = aiService;
    }

    @PostMapping("/ask")
    public ResponseEntity<AIResponse> askQuestion(@RequestBody AskRequest request) {
        String answer = aiService.askQuestion(request.getQuestion());
        AIResponse response = new AIResponse(request.getQuestion(), answer);
        return ResponseEntity.ok(response);
    }

    public static class AskRequest {
        private String question;

        public String getQuestion() {
            return question;
        }

        public void setQuestion(String question) {
            this.question = question;
        }
    }

    public static class AIResponse {
        private String question;
        private String answer;

        public AIResponse(String question, String answer) {
            this.question = question;
            this.answer = answer;
        }

        public String getQuestion() {
            return question;
        }

        public String getAnswer() {
            return answer;
        }
    }
}


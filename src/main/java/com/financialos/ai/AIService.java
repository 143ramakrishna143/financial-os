package com.financialos.ai;

import com.financialos.config.OllamaConfig;
import com.financialos.repository.ExpenseRepository;
import com.financialos.repository.IncomeRepository;
import com.financialos.repository.StockRepository;
import com.financialos.repository.MutualFundRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Service
public class AIService {

    private final OllamaConfig ollamaConfig;
    private final RestTemplate restTemplate;
    private final ExpenseRepository expenseRepository;
    private final IncomeRepository incomeRepository;
    private final StockRepository stockRepository;
    private final MutualFundRepository mutualFundRepository;

    public AIService(OllamaConfig ollamaConfig, RestTemplate restTemplate,
                     ExpenseRepository expenseRepository, IncomeRepository incomeRepository,
                     StockRepository stockRepository, MutualFundRepository mutualFundRepository) {
        this.ollamaConfig = ollamaConfig;
        this.restTemplate = restTemplate;
        this.expenseRepository = expenseRepository;
        this.incomeRepository = incomeRepository;
        this.stockRepository = stockRepository;
        this.mutualFundRepository = mutualFundRepository;
    }

    /**
     * Query the AI model with a question and database context
     * Uses Ollama local LLM with Qwen 8B model
     */
    public String askQuestion(String question) {
        try {
            // Extract relevant data from database based on question
            String databaseContext = extractRelevantData(question);

            // Prepare prompt with context
            String systemPrompt = "You are a financial assistant with access to personal financial data. " +
                    "Provide concise and helpful answers based on the provided financial data.";
            
            String userPrompt = "Context: " + databaseContext + "\n\nQuestion: " + question;

            // Call Ollama API
            OllamaRequest request = new OllamaRequest(
                    ollamaConfig.getModel(),
                    systemPrompt,
                    userPrompt
            );

            try {
                OllamaResponse response = restTemplate.postForObject(
                        ollamaConfig.getBaseUrl() + "/api/generate",
                        request,
                        OllamaResponse.class
                );
                
                if (response != null && response.getResponse() != null) {
                    return response.getResponse().trim();
                }
            } catch (Exception e) {
                return "AI service is currently unavailable. Please check if Ollama is running at " + 
                       ollamaConfig.getBaseUrl();
            }

            return "No response from AI model";
        } catch (Exception e) {
            return "Error processing question: " + e.getMessage();
        }
    }

    private String extractRelevantData(String question) {
        StringBuilder context = new StringBuilder();
        question = question.toLowerCase();

        // Check for expense-related queries
        if (question.contains("expense") || question.contains("spend") || question.contains("spent")) {
            double totalExpense = expenseRepository.findAll().stream()
                    .mapToDouble(e -> e.getAmount() != null ? e.getAmount() : 0)
                    .sum();
            context.append("Total Expenses: ").append(totalExpense).append("\n");
        }

        // Check for income-related queries
        if (question.contains("income") || question.contains("salary") || question.contains("earned")) {
            double totalIncome = incomeRepository.findAll().stream()
                    .mapToDouble(i -> i.getAmount() != null ? i.getAmount() : 0)
                    .sum();
            context.append("Total Income: ").append(totalIncome).append("\n");
        }

        // Check for stock-related queries
        if (question.contains("stock") || question.contains("share") || question.contains("equity")) {
            double totalStockValue = stockRepository.findAll().stream()
                    .mapToDouble(s -> s.getCurrentValue() != null ? s.getCurrentValue() : 0)
                    .sum();
            context.append("Total Stock Value: ").append(totalStockValue).append("\n");
        }

        // Check for mutual fund queries
        if (question.contains("mutual fund") || question.contains("mf") || question.contains("fund")) {
            double totalMFValue = mutualFundRepository.findAll().stream()
                    .mapToDouble(mf -> mf.getCurrentValue() != null ? mf.getCurrentValue() : 0)
                    .sum();
            context.append("Total Mutual Fund Value: ").append(totalMFValue).append("\n");
        }

        // Check for budget/surplus queries
        if (question.contains("budget") || question.contains("surplus") || question.contains("balance")) {
            double totalIncome = incomeRepository.findAll().stream()
                    .mapToDouble(i -> i.getAmount() != null ? i.getAmount() : 0)
                    .sum();
            double totalExpense = expenseRepository.findAll().stream()
                    .mapToDouble(e -> e.getAmount() != null ? e.getAmount() : 0)
                    .sum();
            context.append("Surplus/Balance: ").append(totalIncome - totalExpense).append("\n");
        }

        // If nothing specific matched, provide general overview
        if (context.length() == 0) {
            double totalIncome = incomeRepository.findAll().stream()
                    .mapToDouble(i -> i.getAmount() != null ? i.getAmount() : 0)
                    .sum();
            double totalExpense = expenseRepository.findAll().stream()
                    .mapToDouble(e -> e.getAmount() != null ? e.getAmount() : 0)
                    .sum();
            context.append("Total Income: ").append(totalIncome).append("\n")
                   .append("Total Expenses: ").append(totalExpense).append("\n");
        }

        return context.toString();
    }

    public static class OllamaRequest {
        private String model;
        private String prompt;
        private boolean stream = false;
        private Map<String, Object> options;

        public OllamaRequest(String model, String systemPrompt, String userPrompt) {
            this.model = model;
            this.prompt = systemPrompt + "\n\n" + userPrompt;
            this.options = new HashMap<>();
            this.options.put("temperature", 0.7);
        }

        public String getModel() { return model; }
        public String getPrompt() { return prompt; }
        public boolean isStream() { return stream; }
        public Map<String, Object> getOptions() { return options; }
    }

    public static class OllamaResponse {
        private String response;
        private String model;
        private long created_at;
        private boolean done;

        public String getResponse() { return response; }
        public void setResponse(String response) { this.response = response; }
        public String getModel() { return model; }
        public void setModel(String model) { this.model = model; }
        public long getCreated_at() { return created_at; }
        public void setCreated_at(long created_at) { this.created_at = created_at; }
        public boolean isDone() { return done; }
        public void setDone(boolean done) { this.done = done; }
    }
}


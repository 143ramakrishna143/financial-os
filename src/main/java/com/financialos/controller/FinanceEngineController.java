package com.financialos.controller;

import com.financialos.service.FinanceEngineService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Map;

@RestController
@RequestMapping("/api/finance")
public class FinanceEngineController {

    private final FinanceEngineService financeEngineService;

    public FinanceEngineController(FinanceEngineService financeEngineService) {
        this.financeEngineService = financeEngineService;
    }

    @GetMapping("/net-worth")
    public ResponseEntity<Map<String, Object>> netWorth() {
        return ResponseEntity.ok(financeEngineService.calculateNetWorth());
    }

    @GetMapping("/cash-flow")
    public ResponseEntity<Map<String, Object>> cashFlow(@RequestParam String start, @RequestParam String end) {
        LocalDateTime s = LocalDateTime.parse(start);
        LocalDateTime e = LocalDateTime.parse(end);
        return ResponseEntity.ok(financeEngineService.cashFlowForPeriod(s, e));
    }
}


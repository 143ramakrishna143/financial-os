package com.financialos.controller.v2;

import com.financialos.service.FinanceFacade;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Map;

@RestController
@RequestMapping("/api/v2/finance")
public class FinanceControllerV2 {

    private final FinanceFacade financeFacade;

    public FinanceControllerV2(FinanceFacade financeFacade) {
        this.financeFacade = financeFacade;
    }

    @GetMapping("/net-worth")
    public ResponseEntity<Map<String, Object>> netWorth() {
        return ResponseEntity.ok(financeFacade.getNetWorth());
    }

    @GetMapping("/cash-flow")
    public ResponseEntity<Map<String, Object>> cashFlow(@RequestParam String start, @RequestParam String end) {
        LocalDateTime s = LocalDateTime.parse(start);
        LocalDateTime e = LocalDateTime.parse(end);
        return ResponseEntity.ok(financeFacade.getCashFlow(s, e));
    }

    @GetMapping("/overview")
    public ResponseEntity<Map<String, Object>> overview(@RequestParam String start, @RequestParam String end) {
        LocalDateTime s = LocalDateTime.parse(start);
        LocalDateTime e = LocalDateTime.parse(end);
        return ResponseEntity.ok(financeFacade.getDashboardOverview(s, e));
    }
}


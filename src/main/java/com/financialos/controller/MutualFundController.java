package com.financialos.controller;

import com.financialos.model.MutualFund;
import com.financialos.service.MutualFundService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/mutual-funds")
public class MutualFundController {

    private final MutualFundService mutualFundService;

    public MutualFundController(MutualFundService mutualFundService) {
        this.mutualFundService = mutualFundService;
    }

    @GetMapping
    public ResponseEntity<List<MutualFund>> getAllMutualFunds() {
        return ResponseEntity.ok(mutualFundService.getAllMutualFunds());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MutualFund> getMutualFundById(@PathVariable Long id) {
        Optional<MutualFund> mutualFund = mutualFundService.getMutualFundById(id);
        return mutualFund.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<MutualFund> createMutualFund(@RequestBody MutualFund mutualFund) {
        MutualFund created = mutualFundService.saveMutualFund(mutualFund);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MutualFund> updateMutualFund(@PathVariable Long id, @RequestBody MutualFund mutualFund) {
        Optional<MutualFund> existing = mutualFundService.getMutualFundById(id);
        if (existing.isPresent()) {
            mutualFund.setId(id);
            MutualFund updated = mutualFundService.saveMutualFund(mutualFund);
            return ResponseEntity.ok(updated);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMutualFund(@PathVariable Long id) {
        mutualFundService.deleteMutualFund(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/summary")
    public ResponseEntity<MutualFundSummary> getMutualFundSummary() {
        MutualFundSummary summary = new MutualFundSummary(
                mutualFundService.getTotalMutualFundInvested(),
                mutualFundService.getTotalMutualFundCurrentValue(),
                mutualFundService.getTotalMutualFundProfit()
        );
        return ResponseEntity.ok(summary);
    }

    public static class MutualFundSummary {
        public Double totalInvested;
        public Double totalCurrentValue;
        public Double totalProfit;

        public MutualFundSummary(Double totalInvested, Double totalCurrentValue, Double totalProfit) {
            this.totalInvested = totalInvested;
            this.totalCurrentValue = totalCurrentValue;
            this.totalProfit = totalProfit;
        }
    }
}


package com.financialos.controller;

import com.financialos.model.Insurance;
import com.financialos.service.InsuranceService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/insurance")
public class InsuranceController {

    private final InsuranceService insuranceService;

    public InsuranceController(InsuranceService insuranceService) {
        this.insuranceService = insuranceService;
    }

    @GetMapping
    public ResponseEntity<List<Insurance>> getAllInsurances() {
        return ResponseEntity.ok(insuranceService.getAllInsurances());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Insurance> getInsuranceById(@PathVariable Long id) {
        Optional<Insurance> insurance = insuranceService.getInsuranceById(id);
        return insurance.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Insurance> createInsurance(@RequestBody Insurance insurance) {
        Insurance created = insuranceService.saveInsurance(insurance);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Insurance> updateInsurance(@PathVariable Long id, @RequestBody Insurance insurance) {
        Optional<Insurance> existing = insuranceService.getInsuranceById(id);
        if (existing.isPresent()) {
            insurance.setId(id);
            Insurance updated = insuranceService.saveInsurance(insurance);
            return ResponseEntity.ok(updated);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInsurance(@PathVariable Long id) {
        insuranceService.deleteInsurance(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/type/{type}")
    public ResponseEntity<List<Insurance>> getInsuranceByType(@PathVariable String type) {
        return ResponseEntity.ok(insuranceService.getInsuranceByType(type));
    }

    @GetMapping("/active")
    public ResponseEntity<List<Insurance>> getActiveInsurances() {
        return ResponseEntity.ok(insuranceService.getActiveInsurances());
    }

    @GetMapping("/summary")
    public ResponseEntity<InsuranceSummary> getInsuranceSummary() {
        InsuranceSummary summary = new InsuranceSummary(
                insuranceService.getTotalAnnualPremium(),
                insuranceService.getActiveInsurances().size()
        );
        return ResponseEntity.ok(summary);
    }

    public static class InsuranceSummary {
        public Double totalAnnualPremium;
        public Integer activeCount;

        public InsuranceSummary(Double totalAnnualPremium, Integer activeCount) {
            this.totalAnnualPremium = totalAnnualPremium;
            this.activeCount = activeCount;
        }
    }
}


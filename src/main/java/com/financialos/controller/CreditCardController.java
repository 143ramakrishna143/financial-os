package com.financialos.controller;

import com.financialos.model.CreditCard;
import com.financialos.service.CreditCardService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/credit-cards")
public class CreditCardController {

    private final CreditCardService creditCardService;

    public CreditCardController(CreditCardService creditCardService) {
        this.creditCardService = creditCardService;
    }

    @GetMapping
    public ResponseEntity<List<CreditCard>> getAllCreditCards() {
        return ResponseEntity.ok(creditCardService.getAllCreditCards());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CreditCard> getCreditCardById(@PathVariable Long id) {
        Optional<CreditCard> creditCard = creditCardService.getCreditCardById(id);
        return creditCard.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<CreditCard> createCreditCard(@RequestBody CreditCard creditCard) {
        CreditCard created = creditCardService.saveCreditCard(creditCard);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CreditCard> updateCreditCard(@PathVariable Long id, @RequestBody CreditCard creditCard) {
        Optional<CreditCard> existing = creditCardService.getCreditCardById(id);
        if (existing.isPresent()) {
            creditCard.setId(id);
            CreditCard updated = creditCardService.saveCreditCard(creditCard);
            return ResponseEntity.ok(updated);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCreditCard(@PathVariable Long id) {
        creditCardService.deleteCreditCard(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/active")
    public ResponseEntity<List<CreditCard>> getActiveCreditCards() {
        return ResponseEntity.ok(creditCardService.getActiveCreditCards());
    }

    @GetMapping("/issuer/{issuer}")
    public ResponseEntity<List<CreditCard>> getCreditCardsByIssuer(@PathVariable String issuer) {
        return ResponseEntity.ok(creditCardService.getCreditCardsByIssuer(issuer));
    }

    @GetMapping("/summary")
    public ResponseEntity<CreditCardSummary> getCreditCardSummary() {
        CreditCardSummary summary = new CreditCardSummary(
                creditCardService.getTotalCreditLimit(),
                creditCardService.getTotalCreditCardBalance(),
                creditCardService.getTotalAvailableCredit()
        );
        return ResponseEntity.ok(summary);
    }

    public static class CreditCardSummary {
        public Double totalCreditLimit;
        public Double totalBalance;
        public Double totalAvailableCredit;

        public CreditCardSummary(Double totalCreditLimit, Double totalBalance, Double totalAvailableCredit) {
            this.totalCreditLimit = totalCreditLimit;
            this.totalBalance = totalBalance;
            this.totalAvailableCredit = totalAvailableCredit;
        }
    }
}


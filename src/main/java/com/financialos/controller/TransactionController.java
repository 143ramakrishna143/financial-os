package com.financialos.controller;

import com.financialos.model.Transaction;
import com.financialos.service.TransactionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping
    public ResponseEntity<List<Transaction>> getAll() {
        return ResponseEntity.ok(transactionService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Transaction> getOne(@PathVariable Long id) {
        return ResponseEntity.ok(transactionService.getById(id));
    }

    @PostMapping
    public ResponseEntity<Transaction> create(@RequestBody Transaction t) {
        Transaction created = transactionService.create(t);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping("/between")
    public ResponseEntity<List<Transaction>> between(@RequestParam String start, @RequestParam String end) {
        LocalDateTime s = LocalDateTime.parse(start);
        LocalDateTime e = LocalDateTime.parse(end);
        return ResponseEntity.ok(transactionService.findBetween(s, e));
    }
}


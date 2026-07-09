package com.financialos.controller.v2;

import com.financialos.dto.TransactionRequest;
import com.financialos.mapper.TransactionMapper;
import com.financialos.model.Account;
import com.financialos.model.Transaction;
import com.financialos.service.AccountService;
import com.financialos.service.TransactionService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/v2/transactions")
public class TransactionControllerV2 {

    private final TransactionService transactionService;
    private final AccountService accountService;

    public TransactionControllerV2(TransactionService transactionService, AccountService accountService) {
        this.transactionService = transactionService;
        this.accountService = accountService;
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
    public ResponseEntity<Transaction> create(@Valid @RequestBody TransactionRequest req) {
        Account from = null;
        Account to = null;
        if (req.getFromAccountId() != null) from = accountService.getById(req.getFromAccountId());
        if (req.getToAccountId() != null) to = accountService.getById(req.getToAccountId());

        Transaction t = TransactionMapper.toEntity(req, from, to);
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


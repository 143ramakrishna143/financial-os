package com.financialos.mapper;

import com.financialos.dto.TransactionRequest;
import com.financialos.model.Account;
import com.financialos.model.Transaction;

import java.time.LocalDateTime;

public class TransactionMapper {

    public static Transaction toEntity(TransactionRequest req, Account from, Account to) {
        Transaction t = new Transaction();
        t.setType(req.getType());
        t.setCategory(req.getCategory());
        t.setFromAccount(from);
        t.setToAccount(to);
        t.setAmount(req.getAmount());
        t.setOccurredAt(req.getOccurredAt() != null ? req.getOccurredAt() : LocalDateTime.now());
        t.setNotes(req.getNotes());
        return t;
    }
}


package com.financialos.service.widget;

import com.financialos.dto.widget.RecentTransactionDTO;
import com.financialos.model.Transaction;
import com.financialos.service.FinanceFacade;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RecentTransactionsWidgetService {

    private final FinanceFacade financeFacade;

    public RecentTransactionsWidgetService(FinanceFacade financeFacade) {
        this.financeFacade = financeFacade;
    }

    public List<RecentTransactionDTO> getRecent(int limit) {
        return financeFacade.getRecentTransactions(limit).stream().map(this::toDto).collect(Collectors.toList());
    }

    private RecentTransactionDTO toDto(Transaction t) {
        RecentTransactionDTO dto = new RecentTransactionDTO();
        dto.setId(t.getId());
        dto.setTransactionReference(t.getTransactionReference());
        dto.setCategory(t.getCategory());
        dto.setAmount(t.getAmount());
        dto.setOccurredAt(t.getOccurredAt());
        return dto;
    }
}


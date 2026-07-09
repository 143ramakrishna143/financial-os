package com.financialos.controller.v2;

import com.financialos.dto.DashboardOverviewDTO;
import com.financialos.dto.widget.*;
import com.financialos.service.widget.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/v2/dashboard")
public class DashboardControllerV2 {

    private final NetWorthWidgetService netWorthWidgetService;
    private final CashFlowWidgetService cashFlowWidgetService;
    private final InvestmentWidgetService investmentWidgetService;
    private final GoalWidgetService goalWidgetService;
    private final RecentTransactionsWidgetService recentTransactionsWidgetService;
    private final FinancialHealthWidgetService financialHealthWidgetService;

    public DashboardControllerV2(NetWorthWidgetService netWorthWidgetService,
                                 CashFlowWidgetService cashFlowWidgetService,
                                 InvestmentWidgetService investmentWidgetService,
                                 GoalWidgetService goalWidgetService,
                                 RecentTransactionsWidgetService recentTransactionsWidgetService,
                                 FinancialHealthWidgetService financialHealthWidgetService) {
        this.netWorthWidgetService = netWorthWidgetService;
        this.cashFlowWidgetService = cashFlowWidgetService;
        this.investmentWidgetService = investmentWidgetService;
        this.goalWidgetService = goalWidgetService;
        this.recentTransactionsWidgetService = recentTransactionsWidgetService;
        this.financialHealthWidgetService = financialHealthWidgetService;
    }

    @GetMapping
    public ResponseEntity<DashboardOverviewDTO> getOverview(@RequestParam(value = "start", required = false) String start,
                                                             @RequestParam(value = "end", required = false) String end) {
        LocalDateTime s = start != null ? LocalDateTime.parse(start) : LocalDateTime.now().minusDays(30);
        LocalDateTime e = end != null ? LocalDateTime.parse(end) : LocalDateTime.now();

        DashboardOverviewDTO dto = new DashboardOverviewDTO();
        NetWorthWidgetDTO nw = netWorthWidgetService.get();
        dto.setNetWorth(nw);
        dto.setCashFlow(cashFlowWidgetService.getForPeriod(s, e));
        dto.setInvestments(investmentWidgetService.get());
        dto.setGoals(goalWidgetService.getAll());
        dto.setRecentTransactions(recentTransactionsWidgetService.getRecent(10));
        dto.setHealth(financialHealthWidgetService.get());
        return ResponseEntity.ok(dto);
    }
}


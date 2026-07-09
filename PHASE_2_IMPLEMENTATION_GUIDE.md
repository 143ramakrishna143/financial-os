# Phase 2 Implementation Guide - Accounts & Dashboard

**Version:** v0.2 Accounts & Dashboard  
**Target Duration:** 4 weeks (20 developer days)  
**Priority:** Accounts table + Professional Dashboard

---

## 🎯 Phase 2 Overview

Phase 2 transforms Financial OS from a **data collector** (v0.1) into a **financial management tool** (v0.2).

**Key Achievement:** Every transaction linked to an account.

---

## 📋 Sprint Breakdown

### 🏗️ Sprint 1: Accounts Foundation (Days 1-5)

#### Day 1-2: Create Account Entity

**File:** `src/main/java/com/financialos/model/Account.java`

```java
@Entity
@Table(name = "accounts")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;  // "SBI Savings", "ICICI Demat", "Groww", "Cash", "HDFC Card"

    @Column(nullable = false)
    private String type;  // BANK, INVESTMENT, WALLET, CASH, CREDIT_CARD

    private Double balance;  // Current balance

    private String currency;  // INR, USD, etc.

    private Boolean active;  // Active or archived

    private String notes;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    // Getters, setters, constructors
}
```

**File:** `src/main/java/com/financialos/repository/AccountRepository.java`

```java
@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    List<Account> findByType(String type);
    List<Account> findByActive(Boolean active);
    Optional<Account> findByName(String name);
}
```

#### Day 2-3: Create Account Service

**File:** `src/main/java/com/financialos/service/AccountService.java`

```java
@Service
public class AccountService {

    private final AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    public List<Account> getActiveAccounts() {
        return accountRepository.findByActive(true);
    }

    public Double getTotalBalance() {
        return accountRepository.findByActive(true).stream()
                .mapToDouble(Account::getBalance)
                .sum();
    }

    public Double getBalanceByType(String type) {
        return accountRepository.findByType(type).stream()
                .mapToDouble(Account::getBalance)
                .sum();
    }

    public Account createAccount(Account account) {
        account.setCreatedAt(LocalDateTime.now());
        account.setUpdatedAt(LocalDateTime.now());
        return accountRepository.save(account);
    }

    public Account updateBalance(Long accountId, Double newBalance) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new RuntimeException("Account not found"));
        account.setBalance(newBalance);
        account.setUpdatedAt(LocalDateTime.now());
        return accountRepository.save(account);
    }
}
```

#### Day 3-4: Create Account Controller

**File:** `src/main/java/com/financialos/controller/AccountController.java`

```java
@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping
    public ResponseEntity<List<Account>> getAllAccounts() {
        return ResponseEntity.ok(accountService.getAllAccounts());
    }

    @GetMapping("/active")
    public ResponseEntity<List<Account>> getActiveAccounts() {
        return ResponseEntity.ok(accountService.getActiveAccounts());
    }

    @GetMapping("/total-balance")
    public ResponseEntity<Double> getTotalBalance() {
        return ResponseEntity.ok(accountService.getTotalBalance());
    }

    @GetMapping("/balance-by-type/{type}")
    public ResponseEntity<Double> getBalanceByType(@PathVariable String type) {
        return ResponseEntity.ok(accountService.getBalanceByType(type));
    }

    @PostMapping
    public ResponseEntity<Account> createAccount(@RequestBody Account account) {
        Account created = accountService.createAccount(account);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Account> updateAccount(@PathVariable Long id, @RequestBody Account account) {
        account.setId(id);
        return ResponseEntity.ok(accountService.updateBalance(id, account.getBalance()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAccount(@PathVariable Long id) {
        // Soft delete: set active = false
        Account account = accountService.findById(id);
        account.setActive(false);
        accountService.save(account);
        return ResponseEntity.noContent().build();
    }

    public static class AccountsSummary {
        public Double totalBalance;
        public Integer accountCount;
        public Map<String, Double> balanceByType;

        public AccountsSummary(Double totalBalance, Integer accountCount, Map<String, Double> balanceByType) {
            this.totalBalance = totalBalance;
            this.accountCount = accountCount;
            this.balanceByType = balanceByType;
        }
    }
}
```

#### Day 4-5: Update Existing Entities

**Modify:** `src/main/java/com/financialos/model/Income.java`

```java
@Entity
@Table(name = "income")
public class Income {
    // ...existing fields...
    
    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;  // ADD THIS
    
    // getter, setter
}
```

**Repeat for:**
- `Expense.java` - Add `@ManyToOne Account account`
- `Stock.java` - Add `@ManyToOne Account account` (demat account)
- `MutualFund.java` - Add `@ManyToOne Account account`
- `CreditCard.java` - Add `@ManyToOne Account account`

**Update Repositories:**
```java
// IncomeRepository.java
List<Income> findByAccount(Account account);

// ExpenseRepository.java
List<Expense> findByAccount(Account account);
```

---

### 📊 Sprint 2: Professional Dashboard (Days 6-10)

#### Day 6: Create Dashboard V2 DTOs

**File:** `src/main/java/com/financialos/dto/DashboardV2.java`

```java
@Data
public class DashboardV2 {
    // Assets Section
    private Double totalAssets;
    private Double cashBalance;
    private Double investmentValue;
    private Double mfValue;
    private Double stockValue;

    // Liabilities Section
    private Double totalLiabilities;
    private Double totalLoanDebt;
    private Double totalCreditCardDebt;

    // Net Worth
    private Double netWorth;

    // Cash Flow
    private Double monthlyIncome;
    private Double monthlyExpense;
    private Double monthlySurplus;
    private Double savingsRate;

    // Goals
    private List<GoalSnapshot> activeGoals;
    private Double totalGoalProgress;

    // Account Summary
    private List<AccountSummary> accountsByType;
}

@Data
public class AccountSummary {
    private String type;
    private Double balance;
    private Integer count;
    private List<String> accountNames;
}

@Data
public class GoalSnapshot {
    private String goalName;
    private Double progress;
    private Double remaining;
    private String status;
}
```

#### Day 6-7: Create Dashboard V2 Service

**File:** `src/main/java/com/financialos/service/DashboardServiceV2.java`

```java
@Service
public class DashboardServiceV2 {

    private final AccountService accountService;
    private final BudgetService budgetService;
    private final MutualFundService mutualFundService;
    private final StockService stockService;
    private final LoanService loanService;
    private final CreditCardService creditCardService;
    private final GoalService goalService;

    public DashboardServiceV2(AccountService accountService, BudgetService budgetService,
                              MutualFundService mutualFundService, StockService stockService,
                              LoanService loanService, CreditCardService creditCardService,
                              GoalService goalService) {
        this.accountService = accountService;
        this.budgetService = budgetService;
        this.mutualFundService = mutualFundService;
        this.stockService = stockService;
        this.loanService = loanService;
        this.creditCardService = creditCardService;
        this.goalService = goalService;
    }

    public DashboardV2 getDashboard() {
        DashboardV2 dashboard = new DashboardV2();

        // Assets
        dashboard.setTotalAssets(accountService.getTotalBalance());
        dashboard.setCashBalance(accountService.getBalanceByType("BANK") + accountService.getBalanceByType("CASH"));
        dashboard.setMfValue(mutualFundService.getTotalMutualFundCurrentValue());
        dashboard.setStockValue(stockService.getTotalStockCurrentValue());
        dashboard.setInvestmentValue(dashboard.getMfValue() + dashboard.getStockValue());

        // Liabilities
        dashboard.setTotalLoanDebt(loanService.getTotalRemainingLoanAmount());
        dashboard.setTotalCreditCardDebt(creditCardService.getTotalCreditCardBalance());
        dashboard.setTotalLiabilities(dashboard.getTotalLoanDebt() + dashboard.getTotalCreditCardDebt());

        // Net Worth
        dashboard.setNetWorth(dashboard.getTotalAssets() + dashboard.getInvestmentValue() - dashboard.getTotalLiabilities());

        // Cash Flow
        dashboard.setMonthlyIncome(budgetService.getTotalIncome());
        dashboard.setMonthlyExpense(budgetService.getTotalExpense());
        dashboard.setMonthlySurplus(budgetService.getSurplus());
        dashboard.setSavingsRate(calculateSavingsRate(dashboard.getMonthlySurplus(), dashboard.getMonthlyIncome()));

        // Goals
        dashboard.setActiveGoals(getActiveGoalsSnapshot());
        dashboard.setTotalGoalProgress(goalService.getTotalGoalCurrentAmount());

        // Accounts by Type
        dashboard.setAccountsByType(getAccountsByType());

        return dashboard;
    }

    private Double calculateSavingsRate(Double surplus, Double income) {
        if (income == null || income == 0) return 0.0;
        return (surplus / income) * 100;
    }

    private List<GoalSnapshot> getActiveGoalsSnapshot() {
        return goalService.getGoalsByStatus("In Progress").stream()
                .map(goal -> new GoalSnapshot(
                        goal.getGoalName(),
                        goal.getProgressPercentage(),
                        goal.getRemainingAmount(),
                        goal.getStatus()
                ))
                .collect(Collectors.toList());
    }

    private List<AccountSummary> getAccountsByType() {
        // Group accounts by type and calculate totals
        // Implementation details...
        return new ArrayList<>();
    }
}
```

#### Day 7-8: Create Dashboard V2 Controller

**File:** `src/main/java/com/financialos/controller/DashboardControllerV2.java`

```java
@RestController
@RequestMapping("/api/v2/dashboard")
public class DashboardControllerV2 {

    private final DashboardServiceV2 dashboardService;

    public DashboardControllerV2(DashboardServiceV2 dashboardService) {
        this.dashboardService = dashboardService;
    }

    @GetMapping
    public ResponseEntity<DashboardV2> getDashboard() {
        return ResponseEntity.ok(dashboardService.getDashboard());
    }

    @GetMapping("/assets")
    public ResponseEntity<AssetBreakdown> getAssets() {
        // Return assets breakdown
        return ResponseEntity.ok(dashboardService.getAssetBreakdown());
    }

    @GetMapping("/liabilities")
    public ResponseEntity<LiabilityBreakdown> getLiabilities() {
        // Return liabilities breakdown
        return ResponseEntity.ok(dashboardService.getLiabilityBreakdown());
    }

    @GetMapping("/cash-flow")
    public ResponseEntity<CashFlowData> getCashFlow() {
        // Return monthly cash flow
        return ResponseEntity.ok(dashboardService.getCashFlow());
    }
}
```

---

### 💼 Sprint 3: Portfolio Manager (Days 11-15)

#### Day 11: Create Price History Entity

**File:** `src/main/java/com/financialos/model/PriceHistory.java`

```java
@Entity
@Table(name = "price_history")
public class PriceHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String entityType;  // STOCK, MUTUAL_FUND

    @Column(nullable = false)
    private Long entityId;      // Stock ID or MF ID

    @Column(nullable = false)
    private Double price;       // Current price or NAV

    @Column(nullable = false)
    private LocalDate date;

    private String source;      // MANUAL, API, UPLOADED

    // Getters, setters
}
```

#### Day 12-13: Update Stock & MF Models

**Modify:** `Stock.java` - Remove currentPrice, add method

```java
public Double getCurrentPrice() {
    // Get latest price from PriceHistory
    // Implementation: query PriceHistory for this stock, order by date DESC, limit 1
}
```

**Same for:** `MutualFund.java`

#### Day 13-14: Create Portfolio DTOs

**File:** `src/main/java/com/financialos/dto/PortfolioSummary.java`

```java
@Data
public class PortfolioSummary {
    // Stocks
    private Double totalStocksInvested;
    private Double totalStocksValue;
    private Double totalStocksGain;
    private List<StockHolding> stockHoldings;

    // Mutual Funds
    private Double totalMFInvested;
    private Double totalMFValue;
    private Double totalMFGain;
    private List<MFHolding> mfHoldings;

    // Allocation
    private List<AllocationBreakdown> allocation;
}

@Data
public class StockHolding {
    private String ticker;
    private String company;
    private Integer qty;
    private Double avgPrice;
    private Double currentPrice;
    private Double currentValue;
    private Double gain;
    private Double gainPercent;
    private String sector;
}

@Data
public class MFHolding {
    private String fundName;
    private String fundCode;
    private Double amountInvested;
    private Double currentValue;
    private Double gain;
    private Double gainPercent;
    private Double xirr;
}
```

#### Day 14-15: Create Portfolio Controller

**File:** `src/main/java/com/financialos/controller/PortfolioController.java`

```java
@RestController
@RequestMapping("/api/v2/portfolio")
public class PortfolioController {

    private final StockService stockService;
    private final MutualFundService mutualFundService;

    @GetMapping("/summary")
    public ResponseEntity<PortfolioSummary> getPortfolioSummary() {
        // Return complete portfolio summary
        return ResponseEntity.ok(buildPortfolioSummary());
    }

    @GetMapping("/stocks")
    public ResponseEntity<List<StockHolding>> getStockHoldings() {
        // Return detailed stock holdings with current prices
        return ResponseEntity.ok(getStockHoldingsList());
    }

    @GetMapping("/mutual-funds")
    public ResponseEntity<List<MFHolding>> getMFHoldings() {
        // Return detailed MF holdings
        return ResponseEntity.ok(getMFHoldingsList());
    }

    @GetMapping("/allocation")
    public ResponseEntity<AllocationBreakdown> getAllocation() {
        // Return portfolio allocation (stocks vs MF vs cash)
        return ResponseEntity.ok(getAllocationBreakdown());
    }
}
```

---

### 📈 Sprint 4: Reports & Goal Engine (Days 16-20)

#### Day 16: Create Report DTOs

**File:** `src/main/java/com/financialos/dto/MonthlyReport.java`

```java
@Data
public class MonthlyReport {
    private String month;           // "2026-07"
    private Double income;
    private Double expense;
    private Double surplus;
    private Double savingsRate;
    private Double investmentReturn;
    private Double netWorthChange;
    private Map<String, Double> expenseByCategory;
    private List<Transaction> topExpenses;  // Top 5 expenses
}
```

#### Day 16-17: Create Report Service

**File:** `src/main/java/com/financialos/service/ReportService.java`

```java
@Service
public class ReportService {

    private final IncomeRepository incomeRepository;
    private final ExpenseRepository expenseRepository;
    private final DashboardServiceV2 dashboardService;

    public MonthlyReport getMonthlyReport(String month) {
        // month = "2026-07"
        LocalDate start = LocalDate.parse(month + "-01");
        LocalDate end = start.plusMonths(1).minusDays(1);

        MonthlyReport report = new MonthlyReport();
        report.setMonth(month);

        // Calculate income/expense for month
        List<Income> monthlyIncome = incomeRepository.findByDateBetween(start, end);
        List<Expense> monthlyExpense = expenseRepository.findByDateBetween(start, end);

        Double income = monthlyIncome.stream().mapToDouble(Income::getAmount).sum();
        Double expense = monthlyExpense.stream().mapToDouble(Expense::getAmount).sum();

        report.setIncome(income);
        report.setExpense(expense);
        report.setSurplus(income - expense);
        report.setSavingsRate((income > 0) ? (report.getSurplus() / income) * 100 : 0);

        // Expense breakdown
        report.setExpenseByCategory(getExpenseByCategory(monthlyExpense));

        // Top 5 expenses
        report.setTopExpenses(getTopExpenses(monthlyExpense, 5));

        return report;
    }

    public AnnualReport getAnnualReport(Integer year) {
        // Aggregate monthly reports for the year
        // Implementation...
    }

    private Map<String, Double> getExpenseByCategory(List<Expense> expenses) {
        return expenses.stream()
                .collect(Collectors.groupingBy(
                        Expense::getCategory,
                        Collectors.summingDouble(Expense::getAmount)
                ));
    }
}
```

#### Day 17-18: Enhance Goal Engine

**Modify:** `src/main/java/com/financialos/service/GoalService.java`

```java
@Service
public class GoalService {
    // ...existing methods...

    public GoalProgress getGoalProgress(Long goalId) {
        Goal goal = goalRepository.findById(goalId)
                .orElseThrow(() -> new RuntimeException("Goal not found"));

        double progressPercent = goal.getProgressPercentage();
        double remaining = goal.getRemainingAmount();

        // Calculate months needed (assuming consistent monthly savings)
        double monthlyNeeded = calculateMonthlyNeeded(goal);

        return new GoalProgress(
                goal.getGoalName(),
                progressPercent,
                remaining,
                monthlyNeeded,
                calculateMonthsRemaining(monthlyNeeded, remaining)
        );
    }

    private Double calculateMonthlyNeeded(Goal goal) {
        if (goal.getTargetDate() == null) return 0.0;
        long monthsUntilTarget = ChronoUnit.MONTHS.between(LocalDate.now(), goal.getTargetDate());
        if (monthsUntilTarget <= 0) return 0.0;
        return goal.getRemainingAmount() / monthsUntilTarget;
    }

    private Integer calculateMonthsRemaining(Double monthlyNeeded, Double remaining) {
        if (monthlyNeeded == null || monthlyNeeded == 0) return 0;
        return (int) Math.ceil(remaining / monthlyNeeded);
    }
}
```

#### Day 18-19: Create Report Controller

**File:** `src/main/java/com/financialos/controller/ReportController.java`

```java
@RestController
@RequestMapping("/api/v2/reports")
public class ReportController {

    private final ReportService reportService;

    @GetMapping("/monthly/{month}")
    public ResponseEntity<MonthlyReport> getMonthlyReport(@PathVariable String month) {
        return ResponseEntity.ok(reportService.getMonthlyReport(month));
    }

    @GetMapping("/annual/{year}")
    public ResponseEntity<AnnualReport> getAnnualReport(@PathVariable Integer year) {
        return ResponseEntity.ok(reportService.getAnnualReport(year));
    }

    @PostMapping("/export/pdf/{month}")
    public ResponseEntity<byte[]> exportPDF(@PathVariable String month) {
        // Generate and return PDF
        // Implementation requires iText or similar library
        return ResponseEntity.ok()
                .header("Content-Disposition", "attachment; filename=report_" + month + ".pdf")
                .body(new byte[]{});
    }
}
```

#### Day 19-20: Create Goal Controller V2

**File:** `src/main/java/com/financialos/controller/GoalControllerV2.java`

```java
@RestController
@RequestMapping("/api/v2/goals")
public class GoalControllerV2 {

    private final GoalService goalService;

    @GetMapping("/{id}/progress")
    public ResponseEntity<GoalProgress> getGoalProgress(@PathVariable Long id) {
        return ResponseEntity.ok(goalService.getGoalProgress(id));
    }

    @GetMapping("/all-progress")
    public ResponseEntity<List<GoalProgress>> getAllGoalsProgress() {
        return ResponseEntity.ok(goalService.getAllGoalsProgress());
    }

    @GetMapping("/summary")
    public ResponseEntity<GoalsSummary> getGoalsSummary() {
        // Return aggregate goal data
        return ResponseEntity.ok(goalService.getGoalsSummary());
    }
}
```

---

## 🧪 Testing Checklist for Phase 2

### Unit Tests
- [ ] Account creation and retrieval
- [ ] Dashboard calculations (net worth, cash flow)
- [ ] Portfolio aggregations
- [ ] Goal progress calculations
- [ ] Report generation

### Integration Tests
- [ ] Account-Income relationship
- [ ] Account-Expense relationship
- [ ] Multi-account balance tracking
- [ ] Dashboard end-to-end

### API Tests
- [ ] GET /api/accounts
- [ ] GET /api/v2/dashboard
- [ ] GET /api/v2/portfolio/summary
- [ ] GET /api/v2/reports/monthly/{month}

### Database Tests
- [ ] Foreign key constraints working
- [ ] Data integrity with account_id

---

## 📝 Migration Strategy (v0.1 → v0.2)

### Step 1: Backup Database
```bash
# Before starting v0.2 development
cp financial.db financial.db.v0.1.backup
```

### Step 2: Create Migration SQL
```sql
-- Add account_id columns to existing tables
ALTER TABLE income ADD COLUMN account_id INTEGER;
ALTER TABLE expense ADD COLUMN account_id INTEGER;
ALTER TABLE stock ADD COLUMN account_id INTEGER;
ALTER TABLE mutual_funds ADD COLUMN account_id INTEGER;
ALTER TABLE credit_cards ADD COLUMN account_id INTEGER;

-- Create accounts table
CREATE TABLE accounts (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    name VARCHAR(255) NOT NULL,
    type VARCHAR(50) NOT NULL,
    balance DOUBLE,
    currency VARCHAR(3),
    active BOOLEAN DEFAULT 1,
    notes TEXT,
    created_at TIMESTAMP,
    updated_at TIMESTAMP
);
```

### Step 3: Seed Default Accounts
```sql
-- For v0.1 data, create a "Default Account"
INSERT INTO accounts (name, type, currency, active, created_at, updated_at)
VALUES ('Default Account', 'BANK', 'INR', 1, NOW(), NOW());

-- Link existing transactions
UPDATE income SET account_id = 1 WHERE account_id IS NULL;
UPDATE expense SET account_id = 1 WHERE account_id IS NULL;
```

---

## 🚀 Deployment Checklist for v0.2

- [ ] All v0.2 entities created
- [ ] All v0.2 controllers tested
- [ ] Accounts migration completed
- [ ] Dashboard V2 working correctly
- [ ] All v0.1 endpoints still functional (backward compatible)
- [ ] Documentation updated
- [ ] Database backup created
- [ ] Performance tested (no N+1 queries)
- [ ] Security review completed
- [ ] Team sign-off

---

## 📚 Documentation Updates Required

- [ ] README.md - Add v0.2 features
- [ ] API_DOCUMENTATION.md - Add /api/v2/ endpoints
- [ ] DATABASE_SCHEMA.md - Add Accounts table and PriceHistory
- [ ] Create "v0.2 User Guide"
- [ ] Create "Migration Guide (v0.1 to v0.2)"

---

## 🎓 Key Learnings for v0.2

**From User Feedback:**
1. **Accounts are foundational** - Every transaction needs a home
2. **Dashboard should be professional** - Not just JSON APIs
3. **Portfolio is about performance** - Not just CRUD
4. **Reports enable insights** - Monthly/Quarterly/Annual trends
5. **Separate concerns** - AI explains, Java calculates

**Technical Decisions:**
1. Use `/api/v2/` for new endpoints (keep v0.1 stable)
2. Store price/NAV separately from investments
3. Immutable buy data (never modify quantity, price, date)
4. Account-centric architecture

---

## 🔗 Dependencies to Add (if needed)

```xml
<!-- For PDF export (optional for Phase 2) -->
<!-- <dependency>
    <groupId>com.itextpdf</groupId>
    <artifactId>itextpdf</artifactId>
    <version>5.5.13.3</version>
</dependency> -->

<!-- Already have everything else -->
```

---

**Ready to Start Phase 2?** Begin with Sprint 1: Accounts Foundation!


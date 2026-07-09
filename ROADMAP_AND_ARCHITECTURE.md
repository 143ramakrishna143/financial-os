# Financial OS - Product Roadmap & Architecture

**Current Version:** v0.1 (Foundation) - Complete ✅  
**Last Updated:** July 9, 2026

---

## 📊 Version Roadmap

```
v0.1 Foundation        ✅ COMPLETE
├─ Core entities (Income, Expense, Investments, Goals)
├─ REST APIs (50+ endpoints)
├─ SQLite database
├─ Dashboard (basic)
└─ Offline AI (Ollama integration)

v0.2 Accounts & Dashboard    ⬜ NEXT
├─ Accounts table (bank accounts, wallets)
├─ Professional dashboard with charts
├─ Portfolio view (stocks/MF with performance)
├─ Goal tracking engine
└─ Monthly reports (PDF export)

v0.3 Portfolio Manager       ⬜ PLANNED
├─ Enhanced stock/MF tracking
├─ Performance analytics (XIRR, returns)
├─ Asset allocation view
├─ Rebalancing suggestions
└─ Sector-wise breakdown

v0.4 Reports & Analytics     ⬜ PLANNED
├─ Monthly/Quarterly/Annual reports
├─ Expense analysis by category
├─ Income vs. Expense trends
├─ Cash flow projections
└─ Tax planning insights

v0.5 Automation             ⬜ PLANNED
├─ Groww API integration
├─ Bank statement import
├─ Credit card CSV import
├─ Receipt OCR (expense recognition)
└─ Automatic transaction categorization

v0.6 AI Advisor             ⬜ PLANNED
├─ Intent-based AI queries
├─ "Can I afford X?" predictions
├─ Smart financial recommendations
├─ RAG (Retrieval-Augmented Generation)
├─ Voice interface
└─ Personalized insights

v1.0 Financial OS (Final)    ⬜ RELEASE
└─ Production-ready with all features
```

---

## 🏗️ Architectural Improvements (Priority Order)

### Phase 2 Priority: ADD ACCOUNTS TABLE

**Why:** Every transaction must belong to an account.

**Current Problem:**
```
Income → No account specified
Expense → Where did money come from?
Stock → Which account holds shares?
```

**Solution:**
```sql
CREATE TABLE accounts (
    id INTEGER PRIMARY KEY,
    name VARCHAR(255),              -- "SBI Savings", "ICICI Demat", "Groww", "Cash"
    type VARCHAR(50),               -- BANK, WALLET, INVESTMENT, CASH
    balance DOUBLE,                 -- Current balance
    currency VARCHAR(3),            -- INR, USD, etc.
    created_at TIMESTAMP
);
```

**Update Existing Tables:**
```
Income              → Add account_id
Expense             → Add account_id (from which account)
Stock               → Add account_id (demat account)
MutualFund          → Add account_id (fund house/app)
CreditCard          → Link to account_id
```

**Benefits:**
- Real multi-account tracking
- Cash flow by account
- Account-wise net worth
- Easier reconciliation

---

### Phase 3 Priority: INTRODUCE TRANSACTIONS TABLE

**Why:** Replace separate Income/Expense tables with unified Transaction model.

**Current Problem:**
```
Income table
Expense table
→ Duplicate logic for reporting
→ Hard to track transfers
→ Can't record dividends, refunds, EMI as separate types
```

**Solution (Phase 3):**
```sql
CREATE TABLE transactions (
    id INTEGER PRIMARY KEY,
    type VARCHAR(50),               -- INCOME, EXPENSE, TRANSFER, INVESTMENT, DIVIDEND, EMI, REFUND
    category VARCHAR(100),          -- "Salary", "Food", "Stock Buy", etc.
    from_account_id INTEGER,        -- Source account
    to_account_id INTEGER,          -- Destination account (for transfers)
    amount DOUBLE,
    date TIMESTAMP,
    notes TEXT,
    FOREIGN KEY (from_account_id) REFERENCES accounts(id),
    FOREIGN KEY (to_account_id) REFERENCES accounts(id)
);
```

**Transaction Types:**
- `INCOME` - Salary, Bonus, Interest, Dividend
- `EXPENSE` - Regular spending
- `TRANSFER` - Between accounts
- `INVESTMENT` - Stock/MF purchases
- `EMI` - Loan payments
- `REFUND` - Returns, refunds
- `ADJUSTMENT` - Manual corrections

**Why Phase 3?** Let Phase 2 establish accounts first; transactions are easier to introduce once accounts are solid.

---

### Immediate Fix: PORTFOLIO PRICING

**Current Problem:**
```java
Stock {
    currentPrice: 4200  // Changes every second!
}
```

**Solution - Separate Data from Volatility:**

```sql
-- Keep what doesn't change
Stock {
    ticker_symbol: "HAL",
    quantity: 100,
    buy_price: 3500,
    buy_date: "2026-01-15"
    // Remove currentPrice!
}

-- Track prices separately
CREATE TABLE price_history (
    id INTEGER PRIMARY KEY,
    stock_id INTEGER,
    price DOUBLE,
    date TIMESTAMP,
    source VARCHAR(50)  -- "MANUAL", "API", "UPLOADED"
);
```

**Benefits:**
- Price history automatically preserved
- Can calculate returns for any date
- Ready for future market API integration
- Immutable buy data

**Same for Mutual Funds:**
```sql
MutualFund {
    fund_code: "SBIN1ABC",
    quantity: 50,
    buy_price: 100,      -- NAV at purchase
    buy_date: "2026-01-01"
    // Remove currentValue!
}

CREATE TABLE fund_nav_history (
    id INTEGER PRIMARY KEY,
    fund_id INTEGER,
    nav DOUBLE,
    date TIMESTAMP
);
```

---

### AI SERVICE ARCHITECTURE

**Current (v0.1):**
```
User Question
    ↓
Prompt Engineering
    ↓
Ollama (LLM)
    ↓
Answer
```

**Improved (v0.2+):**
```
User Question
    ↓
Intent Detection (ML/Regex)
    ├─ "How much did I spend?" → Query type: EXPENSE_SUMMARY
    ├─ "Can I afford PS5?" → Query type: BUDGET_CHECK
    ├─ "What's my net worth?" → Query type: NET_WORTH
    └─ etc.
    ↓
Database Query (Java)
    ├─ Execute deterministic calculations
    ├─ Get exact numbers (not AI estimates!)
    └─ Extract context
    ↓
Format Context for LLM
    (Let Java do the math, AI does explanation)
    ↓
Ollama (LLM)
    (Generate human-friendly response)
    ↓
Answer with Context
```

**Implementation:**
```java
@Service
public class ImprovedAIService {
    
    private enum QueryIntent {
        EXPENSE_SUMMARY,
        INCOME_SUMMARY,
        BUDGET_CHECK,
        NET_WORTH,
        GOAL_PROGRESS,
        INVESTMENT_PERFORMANCE,
        RECOMMENDATION
    }
    
    public AIResponse askQuestion(String question) {
        // 1. Detect intent
        QueryIntent intent = detectIntent(question);
        
        // 2. Execute deterministic query
        FinancialData data = executeQuery(intent);
        
        // 3. Let LLM only explain
        String explanation = ollamaService.explain(intent, data);
        
        return new AIResponse(question, explanation, data);
    }
    
    private QueryIntent detectIntent(String q) {
        String lower = q.toLowerCase();
        if (lower.contains("spend")) return QueryIntent.EXPENSE_SUMMARY;
        if (lower.contains("earn") || lower.contains("income")) return QueryIntent.INCOME_SUMMARY;
        if (lower.contains("afford")) return QueryIntent.BUDGET_CHECK;
        if (lower.contains("worth")) return QueryIntent.NET_WORTH;
        // ... etc
        return QueryIntent.RECOMMENDATION;  // Default: ask LLM
    }
    
    private FinancialData executeQuery(QueryIntent intent) {
        // Java calculates, never LLM
        switch(intent) {
            case EXPENSE_SUMMARY:
                return expenseService.getMonthlyBreakdown();
            case NET_WORTH:
                return dashboardService.calculateNetWorth();
            // ... etc
        }
    }
}
```

**Benefits:**
- Accurate calculations (Java, not AI hallucinations)
- Consistent results
- Easier to test
- AI only does what it's good at (explanation)
- Can swap LLMs without changing logic

---

### USER PROFILE / FINANCIAL ASSUMPTIONS

**Current Problem:**
```
No place to store financial assumptions
→ AI can't make recommendations
→ Goals hard to track
```

**Solution:**
```java
@Entity
public class UserProfile {
    @Id
    private Long userId;
    
    private Double monthlySalary;           // For budget calculations
    private Double annualBonus;             // For income projections
    private Integer bonusMonths;            // 1-12
    
    // Financial targets
    private Double emergencyFundTarget;     // 6 months expenses
    private Double retirementAge;           // For goal planning
    private Double expectedRetirementReturn; // 7%, 8%, 9%?
    
    // Preferences
    private String riskProfile;             // CONSERVATIVE, MODERATE, AGGRESSIVE
    private Double preferredAllocation;     // MF:Stock:Cash ratio
    
    private LocalDateTime updatedAt;
}
```

**Usage:**
```java
// AI can now answer smarter questions
"Can I invest in this fund?"
→ Check against risk profile
→ Check allocation limits
→ Suggest rebalancing if needed

"When can I retire?"
→ Check current assets
→ Check retirement target
→ Calculate years needed
```

---

## 📋 v0.2 Implementation Plan

### Sprint 1: Accounts Foundation (1 week)

**Day 1-2: Create Account Entity & Repository**
```java
// New: Account.java
@Entity
public class Account {
    Long id;
    String name;        // "SBI Savings", "ICICI Demat", "Groww", "Cash"
    String type;        // BANK, WALLET, INVESTMENT, CASH
    Double balance;
    String currency;
    LocalDateTime createdAt;
}

// AccountRepository with custom queries
// AccountService for balance tracking
```

**Day 3: Update Existing Entities**
- Add `accountId` foreign key to:
  - Income
  - Expense
  - Stock
  - MutualFund
  - CreditCard

**Day 4-5: Create Account Controllers & Tests**
```
GET    /api/accounts              - List all accounts
POST   /api/accounts              - Create account
PUT    /api/accounts/{id}         - Update account
DELETE /api/accounts/{id}         - Delete account
GET    /api/accounts/{id}/balance - Get balance
```

---

### Sprint 2: Professional Dashboard (1 week)

**Dashboard Data Structure:**
```java
@Data
public class DashboardV2 {
    // Assets
    Double totalAssets;
    Double cashBalance;
    Double investmentValue;
    
    // Liabilities
    Double totalLiabilities;
    Double creditCardDebt;
    Double loanBalance;
    
    // Net Worth
    Double netWorth;
    
    // Cash Flow
    Double monthlyIncome;
    Double monthlyExpense;
    Double monthlySurplus;
    
    // Charts
    List<CategoryBreakdown> expenseByCategory;
    List<MonthlyTrend> cashFlowTrend;
    List<AssetAllocation> investmentAllocation;
    
    // Goals
    List<GoalProgress> activeGoals;
}
```

**Dashboard Endpoints:**
```
GET /api/dashboard/v2                  - Main dashboard
GET /api/dashboard/v2/cash-flow        - Monthly trends
GET /api/dashboard/v2/allocation       - Asset allocation
GET /api/dashboard/v2/goals           - Goal progress
```

---

### Sprint 3: Portfolio Manager (1 week)

**Real Portfolio View (not CRUD):**
```java
@Data
public class PortfolioSummary {
    // Stocks
    Double totalStocksInvested;
    Double totalStocksValue;
    Double totalStocksGain;
    List<StockHolding> holdings;
    
    // Mutual Funds
    Double totalMFInvested;
    Double totalMFValue;
    Double totalMFGain;
    List<MFHolding> mfHoldings;
    
    // Sector-wise (stocks)
    List<SectorBreakdown> sectors;
}

@Data
public class StockHolding {
    String ticker;
    String company;
    Integer qty;
    Double avgPrice;        // Calculated from buy_price & qty
    Double currentValue;    // From latest price_history
    Double gain;
    Double gainPercent;
}
```

**Endpoints:**
```
GET /api/portfolio/summary          - Overall portfolio
GET /api/portfolio/stocks           - Stock holdings with performance
GET /api/portfolio/mutual-funds     - MF holdings with performance
GET /api/portfolio/allocation       - Sector/category breakdown
GET /api/portfolio/{id}/history     - Price history chart
```

---

### Sprint 4: Reports & Goal Engine (1 week)

**Reports:**
```java
@Data
public class MonthlyReport {
    String month;
    Double income;
    Double expenses;
    Double surplus;
    Double investmentReturn;
    Double netWorthChange;
    Map<String, Double> expenseByCategory;
}

@Data
public class GoalProgress {
    String goalName;
    Double target;
    Double current;
    Double percentage;
    Integer monthsRemaining;
    Double monthlyNeeded;
}
```

**Endpoints:**
```
GET /api/reports/monthly/{month}    - Monthly report
GET /api/reports/quarterly/{year}/{q}
GET /api/reports/annual/{year}
GET /api/goals/progress             - All goals with progress
POST /api/reports/export/pdf        - Export PDF
```

---

## 🎯 Phase 2 Success Criteria

- [ ] Accounts table created and integrated
- [ ] Multi-account tracking working
- [ ] Professional dashboard deployed
- [ ] Portfolio view showing real performance
- [ ] Monthly reports generating correctly
- [ ] Goal engine calculating progress accurately
- [ ] All v0.1 functionality still working
- [ ] Tests passing (aim for 80% coverage)
- [ ] Documentation updated

---

## 🔧 Technical Debt to Address in v0.2

| Item | Impact | Action |
|------|--------|--------|
| No database transactions | High | Add `@Transactional` to critical operations |
| No input validation | Medium | Add `@Valid` + `@NotNull` annotations |
| Limited error handling | Medium | Create GlobalExceptionHandler |
| No API versioning | Low | Plan /api/v2/ for future breaking changes |
| No pagination | Medium | Add Pageable to list endpoints |
| No logging | Medium | Add SLF4J logging throughout |

---

## 📚 Documentation Updates for v0.2

- [ ] Update README with v0.2 features
- [ ] Create Account management guide
- [ ] Create Dashboard user guide
- [ ] Update API_DOCUMENTATION for new endpoints
- [ ] Create migration guide (v0.1 → v0.2)
- [ ] Add architecture decisions document

---

## 🧪 Testing Strategy for v0.2

### Unit Tests
- AccountService calculations
- Dashboard aggregations
- Goal progress calculations

### Integration Tests
- Account-Transaction relationships
- Multi-account balance tracking
- Report generation

### API Tests
- All new endpoints (v0.2)
- Backward compatibility (v0.1)
- Error handling

---

## 🚀 Deployment Checklist for v0.2

- [ ] All tests passing
- [ ] Code reviewed
- [ ] Documentation complete
- [ ] Database migration tested
- [ ] Performance benchmarked
- [ ] Backup created
- [ ] Rollback plan ready

---

## 💡 Design Principles for v0.2+

1. **Java Calculates, AI Explains**
   - Let Java handle all financial calculations
   - AI only generates human-friendly explanations

2. **Immutable Historical Data**
   - Buy prices, quantities, dates: never change
   - Prices/valuations: stored in separate history tables

3. **Account-Centric**
   - Every transaction linked to an account
   - Makes multi-account tracking natural

4. **Modular Architecture**
   - Separate AI module (can swap LLMs)
   - Separate reporting module (can add new reports easily)
   - Separate import module (future: Groww, banks, etc.)

5. **API Stability**
   - v0.1 endpoints stay stable
   - v0.2+ use separate namespaces if needed
   - Clear versioning strategy

---

## 📊 Estimated Effort

| Phase | Duration | Dev Days | Complexity |
|-------|----------|----------|-----------|
| v0.1 (Foundation) | ✅ Complete | 8 | Medium |
| v0.2 (Accounts & Dashboard) | 4 weeks | 20 | Medium-High |
| v0.3 (Portfolio Manager) | 3 weeks | 15 | Medium |
| v0.4 (Reports & Analytics) | 3 weeks | 15 | Low-Medium |
| v0.5 (Automation) | 4 weeks | 20 | High |
| v0.6 (AI Advisor) | 3 weeks | 15 | High |
| **Total to v1.0** | **20 weeks** | **100** | **Medium** |

---

## ✅ Final Notes

**What worked in v0.1:**
- Clean architecture (Controller → Service → Repository → Entity)
- Comprehensive REST API
- Good documentation
- Offline AI integration

**What to improve in v0.2:**
- Real multi-account support (Accounts table)
- Professional UI/UX dashboard
- Better portfolio tracking (separate price history)
- Smarter AI (Java calculates, AI explains)

**The big picture:**
v0.1 was about **building the foundation**.  
v0.2 is about **making it genuinely useful**.  
v0.3-v1.0 is about **automation and intelligence**.

---

**Next Action:** Start v0.2 sprint planning with the Accounts table as the foundation.


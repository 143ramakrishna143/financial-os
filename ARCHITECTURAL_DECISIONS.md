# Architectural Decisions - Financial OS

**Document Type:** Architecture Decision Record (ADR)  
**Date:** July 9, 2026  
**Status:** Approved (Based on Senior Review)

---

## 🎯 Key Architectural Principles

These principles guide all decisions in Financial OS development.

### 1. **Layered Architecture**

```
┌─────────────────────────────┐
│   REST Controllers          │ (HTTP Interface)
├─────────────────────────────┤
│   Services                  │ (Business Logic)
├─────────────────────────────┤
│   Repositories              │ (Data Access)
├─────────────────────────────┤
│   Entities                  │ (Domain Models)
├─────────────────────────────┤
│   Database (SQLite)         │ (Persistence)
└─────────────────────────────┘
```

**Rationale:**
- Clear separation of concerns
- Easy to test each layer independently
- Each layer has single responsibility
- Easy to modify logic without touching data access

**Decision:** APPROVED ✅

---

### 2. **Java Calculates, AI Explains**

**CRITICAL PRINCIPLE**

```
Financial Calculations (Deterministic)
    ↓
    ALL DONE BY JAVA
    ↓
    Results (exact, testable)

LLM Interaction (Creative)
    ↓
    ONLY EXPLAINS JAVA RESULTS
    ↓
    Response (natural language)
```

**What This Means:**

❌ **WRONG:**
```
User: "How much did I spend?"
    ↓
Ollama: "Let me calculate... 
        I think it's around 45,000?"
```

✅ **RIGHT:**
```
User: "How much did I spend?"
    ↓
Java: Queries database → 45,000 (exact)
    ↓
Ollama: "Your total spending is ₹45,000. 
        That's mainly on food (25%) and travel (30%)."
```

**Benefits:**
- Accurate calculations (no hallucinations)
- Testable results
- Fast execution
- Can swap LLMs without changing logic
- Users trust the numbers

**Decision:** APPROVED ✅

---

### 3. **Account-Centric Architecture**

**Every transaction belongs to an account.**

```
Accounts (physical locations of money)
    ├─ SBI Savings
    ├─ ICICI Demat
    ├─ Groww App
    ├─ Cash Wallet
    ├─ HDFC Credit Card
    └─ Liquid Fund Account

Transactions (flow of money)
    ├─ Income → SBI
    ├─ Expense ← SBI
    ├─ Transfer: SBI → Groww
    ├─ Stock Buy: Groww → Demat
    └─ EMI: SBI ← Loan
```

**Why:**
- Real-world modeling (people think in terms of accounts)
- Multi-account tracking natural
- Account balances automatically tracked
- Easier to reconcile
- Future: bank statement import becomes simple

**Decision:** APPROVED ✅ (Starting Phase 2)

---

### 4. **Immutable Historical Data**

**Buy data is historical fact. Never modify it.**

```
Purchase Event (Immutable):
├─ Ticker: HAL
├─ Quantity: 100
├─ Buy Price: 3,500
├─ Buy Date: 2026-01-15
└─ Account: HDFC Demat

Price Timeline (Variable):
├─ 2026-01-15: 3,500 (buy date)
├─ 2026-02-15: 3,650
├─ 2026-03-15: 4,000
├─ 2026-07-09: 4,200 (today)
└─ ...future prices...
```

**Never Do This:**
```java
stock.setCurrentPrice(4200);  // ❌ WRONG
stock.save();                  // ❌ Loss of history
```

**Do This Instead:**
```java
priceHistory.add(new PriceSnapshot(
    stockId, 
    4200, 
    LocalDate.now(),
    "MANUAL"
));

// Later: get current price
Double currentPrice = priceHistoryRepo
    .findLatestPrice(stockId);
```

**Benefits:**
- Price history preserved
- Can calculate returns for any date
- Auditable
- Ready for API integration (add past prices easily)

**Decision:** APPROVED ✅ (Implement in v0.2)

---

### 5. **Modular AI Integration**

**AI is a separate, swappable module.**

```
Finance Engine (Core)
    ├─ Calculate net worth
    ├─ Aggregate expenses
    ├─ Track goals
    └─ Generate reports
    
AI Module (Pluggable)
    ├─ Detect intent
    ├─ Format context
    ├─ Call LLM
    └─ Generate response
```

**Current:** Ollama + Qwen 8B

**Future Options:**
- Switch to Llama 2
- Use local BERT for intent detection
- Add RAG (Retrieval-Augmented Generation)
- Add voice interface
- Swap multiple LLMs

**Why:**
- Finance engine works without AI
- Can test AI independently
- Easy to experiment with new models
- No lock-in to specific LLM

**Decision:** APPROVED ✅

---

### 6. **API Versioning Strategy**

```
v0.1 API (Stable)
├─ /api/income
├─ /api/expense
├─ /api/dashboard
└─ /api/ai/ask

v0.2 API (New Features)
├─ /api/v2/dashboard      (enhanced)
├─ /api/v2/portfolio      (new)
├─ /api/accounts          (new)
└─ /api/v2/reports        (new)

v0.3 API (Future)
└─ /api/v3/...
```

**Why:**
- v0.1 endpoints stay stable
- New features don't break existing code
- Easy to deprecate gradually
- Clients can upgrade at their own pace

**Decision:** APPROVED ✅

---

### 7. **Transaction Types (Future)**

**For Phase 3: Replace separate Income/Expense with unified Transaction**

```java
enum TransactionType {
    INCOME,       // Salary, bonus, interest
    EXPENSE,      // Regular spending
    TRANSFER,     // Between accounts
    INVESTMENT,   // Stock/MF purchase
    DIVIDEND,     // Stock dividend
    EMI,          // Loan payment
    REFUND,       // Money back
    ADJUSTMENT    // Manual correction
}
```

**Why:**
- Real-world transactions are complex
- Single table for all movements
- Easier reporting logic
- Prepares for automation (import)

**Decision:** DEFERRED to Phase 3 (Keep Income/Expense separate in v0.1-v0.2)

---

### 8. **Database Design: SQLite**

**Why SQLite?**
- Single file (portable, easy backup)
- No server needed (embedded)
- Good for personal finance apps
- Future: can migrate to PostgreSQL if needed

**Future Migration Path:**
```
Phase 1-2: SQLite (local development)
    ↓
Phase 3+: PostgreSQL (if adding features requiring it)
    ↓
Hibernate handles both (just change driver)
```

**Decision:** APPROVED ✅ (Stick with SQLite until Phase 3)

---

## 📋 Design Patterns Used

### 1. **Service Layer Pattern**

All business logic lives in `Service` classes.

```java
@Service
public class ExpenseService {
    // Java calculates here
    public Double getTotalExpense() { }
    public Double getExpenseByCategory(String category) { }
}
```

**Not Here:**
```java
@RestController
public class ExpenseController {
    // DON'T put calculations here
    // Just delegate to service
}
```

---

### 2. **Repository Pattern**

Data access through repositories.

```java
@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {
    List<Expense> findByCategory(String category);
}
```

**Benefits:**
- Easy to swap data sources later
- Can add caching
- Testable with mock repositories

---

### 3. **Dependency Injection**

All dependencies injected via constructor.

```java
@Service
public class MyService {
    private final Repository repo;
    private final OtherService other;
    
    public MyService(Repository repo, OtherService other) {
        this.repo = repo;
        this.other = other;
    }
}
```

**Not Autowired into fields:**
```java
@Autowired  // ❌ Avoid this
private Repository repo;
```

**Why:**
- Testable (easy to mock)
- Immutable
- Clear dependencies
- Follows SOLID principles

---

### 4. **DTO Pattern**

Data Transfer Objects for responses.

```java
@Data
public class DashboardSummary {
    Double netWorth;
    Double surplus;
    List<GoalProgress> goals;
}
```

**Benefits:**
- Don't expose database entities
- Can change database without affecting API
- Clear contract for clients
- Easier to version APIs

---

## 🚨 Anti-Patterns to Avoid

### ❌ 1. Business Logic in Controllers

**WRONG:**
```java
@PostMapping
public Expense create(@RequestBody Expense e) {
    e.setAmount(e.getAmount() * 1.18);  // ❌ Tax calc in controller!
    return repo.save(e);
}
```

**RIGHT:**
```java
@PostMapping
public Expense create(@RequestBody Expense e) {
    return service.saveExpense(e);  // ✅ Delegate to service
}
```

---

### ❌ 2. Queries in Controllers

**WRONG:**
```java
@GetMapping
public List<Expense> getByCategory(@RequestParam String cat) {
    return repo.findByCategory(cat);  // ❌ Direct query!
}
```

**RIGHT:**
```java
@GetMapping
public List<Expense> getByCategory(@RequestParam String cat) {
    return service.getExpenseByCategory(cat);  // ✅ Through service
}
```

---

### ❌ 3. AI for Calculations

**WRONG:**
```java
aiService.askQuestion("Calculate my net worth");
// Returns: "I think you have ~50 lakhs"  ❌ Guessing!
```

**RIGHT:**
```java
Double netWorth = dashboardService.calculateNetWorth();  // ✅ Exact
String explanation = aiService.explain("net_worth", netWorth);
// Returns: "Your net worth is ₹50,00,000..."  ✅ Explained
```

---

### ❌ 4. Storing Volatile Data

**WRONG:**
```java
stock.currentPrice = 4200;
stock.save();  // ❌ Lost tomorrow's price!
```

**RIGHT:**
```java
priceHistory.add(new Price(stockId, 4200, today));
// Latest price = priceHistory.getLatest(stockId)
```

---

## 🔄 Technology Stack Decisions

### Why Spring Boot?

- ✅ Industry standard
- ✅ Excellent for REST APIs
- ✅ Built-in dependency injection
- ✅ Easy to scale

### Why SQLite (v0.1-v0.2)?

- ✅ Single file (portable)
- ✅ No server setup
- ✅ Perfect for personal apps
- ✅ Easy local development

### Why Hibernate/JPA?

- ✅ Database agnostic
- ✅ Easy migration path (PostgreSQL later)
- ✅ Built-in relationship management
- ✅ Supports all major databases

### Why Ollama + Qwen?

- ✅ Local/offline
- ✅ Open source
- ✅ No API costs
- ✅ No data leaves machine

---

## 📊 API Evolution Path

```
v0.1 API (Foundation)
    ├─ Income CRUD
    ├─ Expense CRUD
    ├─ Dashboard (basic)
    └─ AI (basic)

v0.2 API (Enhancement)
    ├─ Accounts (new)
    ├─ Dashboard V2 (enhanced)
    ├─ Portfolio (new)
    ├─ Reports (new)
    └─ AI (improved intent detection)

v0.3 API (Structure Change)
    ├─ Transaction (replaces Income/Expense)
    ├─ Automation (import)
    └─ Advanced analytics

v1.0 API (Release)
    └─ Stable, production-ready
```

---

## ✅ Architectural Review Scorecard

| Aspect | Score | Status |
|--------|-------|--------|
| Layered Architecture | 9.5/10 | ✅ Approved |
| Separation of Concerns | 9/10 | ✅ Approved |
| Scalability | 9/10 | ✅ Approved |
| Testability | 9/10 | ✅ Approved |
| Maintainability | 9/10 | ✅ Approved |
| API Design | 9/10 | ✅ Approved |
| Database Design | 8.5/10 | ✅ Approved (with v0.2 improvements) |
| AI Integration | 8/10 | ✅ Approved (needs v0.2 refinement) |
| **Overall** | **9.1/10** | ✅ **Approved** |

---

## 🚀 Recommended Next Actions

### Immediate (Before v0.2)

1. ✅ Implement Accounts table (foundational)
2. ✅ Add account_id to existing entities
3. ✅ Create Account service & controller
4. ✅ Update Dashboard to account-aware

### Short-term (v0.2)

5. ✅ Create Professional Dashboard V2
6. ✅ Build Portfolio manager
7. ✅ Add Monthly reports
8. ✅ Improve AI intent detection

### Medium-term (v0.3)

9. ⬜ Introduce Transaction table
10. ⬜ Add price history tracking
11. ⬜ Implement Groww/Bank import
12. ⬜ Advanced analytics

### Long-term (v1.0+)

13. ⬜ Mobile app
14. ⬜ Cloud sync
15. ⬜ Advanced AI recommendations
16. ⬜ Tax planning features

---

## 📚 Related Documents

- `ROADMAP_AND_ARCHITECTURE.md` - Version roadmap
- `PHASE_2_IMPLEMENTATION_GUIDE.md` - v0.2 sprint details
- `DEVELOPER_REFERENCE.md` - Coding patterns
- `DATABASE_SCHEMA.md` - Data model

---

## 🔐 Architecture Sign-Off

**Reviewed By:** Senior Engineer (Code Review)  
**Date:** July 9, 2026  
**Status:** ✅ APPROVED

**Recommendations:**
1. Implement Phase 2 with Accounts as foundation
2. Maintain clean separation (Java calculates, AI explains)
3. Preserve v0.1 API stability
4. Plan Phase 3 Transaction refactoring

---

**This architecture is solid for v0.1-v1.0. Ready to execute!** 🚀



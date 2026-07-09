# Financial OS — v0.1 (Foundation)

A comprehensive Spring Boot personal finance management system with SQLite database and local AI integration.

**Current Version:** v0.1 Foundation ✅ Complete  
**Next Version:** v0.2 Accounts & Dashboard  
**Target Release:** v1.0 Financial OS

## 📊 Version Strategy

```
v0.1 Foundation        ✅ CURRENT
v0.2 Accounts & Dashboard  (Next)
v0.3 Portfolio Manager
v0.4 Reports & Analytics
v0.5 Automation
v0.6 AI Advisor
v1.0 Financial OS (Release)
```

See **ROADMAP_AND_ARCHITECTURE.md** for complete roadmap.

## ✅ What's Complete (v0.1 Foundation)

**Status:** v0.1 Approved by Senior Review ✅  
**Ready For:** Phase 2 Development  
**Rating:** 9.2/10 - Foundation-Ready

### v0.1 Achievements

### Core Features
- ✅ **Income & Expense Tracking** - Full CRUD REST APIs
- ✅ **Dashboard** - Net worth, surplus, and financial overview
- ✅ **Mutual Funds** - Track SIP investments and current value
- ✅ **Stocks** - Monitor stock portfolio
- ✅ **Financial Goals** - Set and track goals (Farm, House, Marriage, Emergency Fund, Retirement)
- ✅ **Insurance Management** - Track policies and premiums
- ✅ **Loan Management** - EMI tracking and loan details
- ✅ **Credit Cards** - Balance and limit tracking
- ✅ **AI Assistant** - Local LLM with Ollama + Qwen 8B (offline)
- ✅ **SQLite Database** - Auto-created portable database

## Tech Stack

| Component | Technology |
|-----------|-----------|
| Language | Java 21 |
| Framework | Spring Boot 3.3.4 |
| Database | SQLite |
| ORM | Spring Data JPA + Hibernate |
| Build Tool | Maven |
| AI Engine | Ollama (Qwen 8B Model) |

## Project Structure

```
financial-os/
├── src/main/java/com/financialos/
│   ├── controller/              # REST API Endpoints
│   ├── service/                 # Business Logic
│   ├── model/                   # JPA Entities (10 tables)
│   ├── repository/              # Data Access
│   ├── ai/                      # AI Service (Ollama Integration)
│   ├── config/                  # Spring Configuration
│   └── dto/                     # Data Transfer Objects
├── src/main/resources/
│   └── application.properties   # Configuration
└── pom.xml
```

## How to Run (Windows + IntelliJ)

### 1. Open Project
- File → Open → Select `financial-os` folder

### 2. Wait for Maven Download
- IntelliJ will auto-load Maven dependencies (~1-2 minutes first time)
- If stuck: Right-click `pom.xml` → Maven → Reload Project

### 3. Run Application
- Open `FinancialOsApplication.java`
- Click green ▶ Run button or right-click → Run

### 4. Console Output
```
=================================================
 Financial OS is running!
 Try: http://localhost:8080/api/dashboard
=================================================
```

## API Endpoints (Phase 1)

### Dashboard & Budget
```
GET  /api/dashboard              # Financial overview
GET  /api/budget/summary         # Budget summary
GET  /api/budget/expenses/category/{category}
GET  /api/budget/income/source/{source}
```

### Income & Expenses
```
GET    /api/income               # List all income
POST   /api/income               # Add income
PUT    /api/income/{id}          # Update income
DELETE /api/income/{id}          # Delete income

GET    /api/expense              # List all expenses
POST   /api/expense              # Add expense
PUT    /api/expense/{id}         # Update expense
DELETE /api/expense/{id}         # Delete expense
```

### Investments
```
GET    /api/mutual-funds         # List funds
POST   /api/mutual-funds         # Add fund
GET    /api/mutual-funds/summary # Fund portfolio summary

GET    /api/stocks               # List stocks
POST   /api/stocks               # Add stock
GET    /api/stocks/summary       # Stock summary
```

### Financial Management
```
GET    /api/goals                # List goals
POST   /api/goals                # Create goal
PUT    /api/goals/{id}           # Update goal
GET    /api/goals/summary        # Goals progress

GET    /api/insurance            # List policies
GET    /api/insurance/active     # Active only
GET    /api/insurance/summary    # Premium overview

GET    /api/loans                # List loans
GET    /api/loans/active         # Active only
GET    /api/loans/summary        # Loan overview

GET    /api/credit-cards         # List cards
GET    /api/credit-cards/active  # Active only
GET    /api/credit-cards/summary # Credit summary
```

### AI Assistant (Ollama Integration)
```
POST /api/ai/ask
Body: {"question":"How much did I spend?"}
```

## Testing with curl (Windows PowerShell)

**Add Income:**
```powershell
curl -X POST http://localhost:8080/api/income `
  -H "Content-Type: application/json" `
  -d '{\"source\":\"Salary\",\"amount\":70000,\"date\":\"2026-07-01\",\"notes\":\"July salary\"}'
```

**Add Expense:**
```powershell
curl -X POST http://localhost:8080/api/expense `
  -H "Content-Type: application/json" `
  -d '{\"category\":\"Food\",\"amount\":5000,\"date\":\"2026-07-02\",\"notes\":\"Groceries\"}'
```

**View Dashboard:**
```
http://localhost:8080/api/dashboard
```

**Ask AI Assistant:**
```powershell
curl -X POST http://localhost:8080/api/ai/ask `
  -H "Content-Type: application/json" `
  -d '{\"question\":\"How much did I spend this month?\"}'
```

## Setting Up AI Assistant (Optional)

### Install Ollama
1. Download from: https://ollama.ai
2. Install and run: `ollama serve`
3. Pull Qwen model (in another terminal):
   ```bash
   ollama pull qwen:8b
   ```

### Configuration
Edit `application.properties`:
```properties
ollama.baseUrl=http://localhost:11434
ollama.model=qwen:8b
ollama.timeout=300
```

## Database

- **File**: `financial.db` (created automatically in project root)
- **Type**: SQLite (portable, single file)
- **Tables**: 10 entities (Income, Expense, MutualFund, Stock, Goal, Insurance, Loan, CreditCard, User, Settings)
- **Init**: Auto-created on first run via Hibernate

## Troubleshooting

| Issue | Solution |
|-------|----------|
| "Cannot resolve symbol" in IDE | Right-click `pom.xml` → Maven → Reload Project |
| Port 8080 already in use | Change `server.port` in `application.properties` |
| Maven download fails | First run needs internet; cached locally after that |
| AI features not working | Ensure Ollama is running: `ollama serve` |
| Ollama not found | Install from https://ollama.ai and run `ollama serve` |

## Phase 2 Roadmap

- [ ] JavaFX Desktop UI
- [ ] Advanced Financial Reports
- [ ] Tax Planning Tools
- [ ] Investment Analysis & Recommendations
- [ ] Budget Alerts & Notifications
- [ ] Data Export (PDF/CSV)
- [ ] Multi-user Support

## Project Entities (10 Tables)

1. **User** - Account information
2. **Income** - Income sources and amounts
3. **Expense** - Expense tracking with categories
4. **MutualFund** - Fund investments + SIP tracking
5. **Stock** - Stock holdings and valuations
6. **Goal** - Financial goals and progress
7. **Insurance** - Insurance policies
8. **Loan** - Loan details and EMI
9. **CreditCard** - Credit card information
10. **Settings** - Application configuration

## Notes

- **Offline**: Everything runs locally. No cloud, no API keys, no data sent anywhere
- **Portable**: Single `financial.db` file contains entire database
- **Local AI**: Uses Ollama for offline LLM (Qwen 8B) — no internet required after setup
- **No UI Yet**: Phase 1 is API-only; JavaFX UI comes in Phase 2

---

## 🔄 What's Next? Phase 2 (v0.2 Accounts & Dashboard)

✅ **v0.1 is Foundation-Ready** (Architecture: 9.5/10)

**Phase 2 will focus on:**
- ✅ **Accounts Table** (Most Critical) - Link every transaction to an account
- ✅ **Professional Dashboard** - Net worth, cash flow, asset allocation
- ✅ **Portfolio Manager** - Real performance tracking
- ✅ **Monthly Reports** - PDF export
- ✅ **Goal Engine** - Months to target calculations

**Timeline:** 4 weeks (20 dev days)

### Phase 2 Recommendations

Based on senior code review:

1. **Add Accounts (Critical)**
   - Every transaction needs to know which account it belongs to
   - Enables real multi-account tracking
   - Future: Makes bank import simple

2. **Separate Java & AI** (Critical)
   - Let Java calculate (deterministic, testable)
   - AI only explains results (creative, natural language)
   - This prevents hallucinations and errors

3. **Fix Portfolio Pricing** (Important)
   - Don't store `currentPrice` in holdings
   - Keep historical price data separately
   - Query latest price on demand

### Where to Go From Here

1. **Review the feedback:** `SENIOR_REVIEW_FEEDBACK.md`
2. **Understand the roadmap:** `ROADMAP_AND_ARCHITECTURE.md`
3. **See architectural decisions:** `ARCHITECTURAL_DECISIONS.md`
4. **Start Phase 2:** `PHASE_2_IMPLEMENTATION_GUIDE.md`
5. **Execute:** `NEXT_STEPS.md`

---

## 📊 Version Timeline

```
v0.1 Foundation        ✅ COMPLETE
├─ Core entities
├─ REST APIs (50+)
├─ SQLite database
└─ Offline AI

v0.2 Accounts & Dashboard  (Next - 4 weeks)
├─ Accounts table
├─ Professional dashboard
├─ Portfolio manager
└─ Reports

v0.3 Portfolio Manager     (Planned)
├─ Advanced analytics
├─ XIRR calculations
└─ Rebalancing advice

v0.4 Reports & Analytics   (Planned)
├─ Monthly/Annual reports
├─ Expense analysis
└─ Tax planning

v0.5 Automation           (Planned)
├─ Groww import
├─ Bank import
└─ Receipt OCR

v0.6 AI Advisor           (Planned)
├─ Smart recommendations
├─ Voice interface
└─ Advanced insights

v1.0 Financial OS         (Release)
```

---

Ready to build your personal financial OS! 🚀

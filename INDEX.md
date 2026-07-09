# Financial OS - Quick Index

Quick reference to all project files and their locations.

## 📑 Documentation Files (Root Directory)

### Getting Started (Read First)
| File | Purpose | Priority |
|------|---------|----------|
| **README.md** | Project overview & v0.1 features | ✅ START HERE |
| **SETUP_GUIDE.md** | Step-by-step setup instructions | ✅ First run |
| **INDEX.md** | This quick reference | Bookmark it |

### Understanding the System
| File | Purpose | For Whom |
|------|---------|----------|
| **API_DOCUMENTATION.md** | Complete 50+ endpoint reference | API users |
| **DATABASE_SCHEMA.md** | 10 tables with SQL examples | DB developers |
| **DEVELOPER_REFERENCE.md** | Code patterns & architecture | Developers |

### Planning Phase 2+
| File | Purpose | When |
|------|---------|------|
| **ROADMAP_AND_ARCHITECTURE.md** | v0.1→v1.0 version strategy | After v0.1 ✅ |
| **ARCHITECTURAL_DECISIONS.md** | Why key decisions were made | Before coding |
| **PHASE_2_IMPLEMENTATION_GUIDE.md** | Sprint-by-sprint Phase 2 plan | Starting Phase 2 |
| **SENIOR_REVIEW_FEEDBACK.md** | Code review + recommendations | Strategic overview |

### Project Info
| File | Purpose |
|------|---------|
| **IMPLEMENTATION_SUMMARY.md** | What was built in v0.1 |
| **pom.xml** | Maven dependencies |

---

## 🏗️ Source Code Structure

### Models (Entities) - `src/main/java/com/financialos/model/`

```
User.java                 - User account information
Income.java              - Income tracking entity
Expense.java             - Expense tracking entity
MutualFund.java          - Mutual fund investments
Stock.java               - Stock portfolio entity
Goal.java                - Financial goals entity
Insurance.java           - Insurance policies entity
Loan.java                - Loan tracking entity
CreditCard.java          - Credit card entity
Settings.java            - Application settings
```

### Repositories - `src/main/java/com/financialos/repository/`

```
UserRepository.java
IncomeRepository.java
ExpenseRepository.java
MutualFundRepository.java
StockRepository.java
GoalRepository.java
InsuranceRepository.java
LoanRepository.java
CreditCardRepository.java
SettingsRepository.java
```

### Services - `src/main/java/com/financialos/service/`

```
BudgetService.java          - Income/Expense business logic
MutualFundService.java      - MF calculations & queries
StockService.java           - Stock portfolio logic
GoalService.java            - Goal tracking logic
InsuranceService.java       - Insurance management
LoanService.java            - Loan EMI calculations
CreditCardService.java      - Credit card logic
AIService.java              - Ollama LLM integration
```

### Controllers - `src/main/java/com/financialos/controller/`

```
DashboardController.java    - GET /api/dashboard
ExpenseController.java      - CRUD /api/expense
IncomeController.java       - CRUD /api/income
BudgetController.java       - GET /api/budget/*
MutualFundController.java   - CRUD /api/mutual-funds
StockController.java        - CRUD /api/stocks
GoalController.java         - CRUD /api/goals
InsuranceController.java    - CRUD /api/insurance
LoanController.java         - CRUD /api/loans
CreditCardController.java   - CRUD /api/credit-cards
AIController.java           - POST /api/ai/ask
```

### Configuration - `src/main/java/com/financialos/config/`

```
OllamaConfig.java           - Ollama AI configuration
RestConfig.java             - RestTemplate bean
FinancialOsApplication.java - Main entry point
```

### DTOs - `src/main/java/com/financialos/dto/`

```
DashboardSummary.java       - Dashboard data transfer object
```

### AI - `src/main/java/com/financialos/ai/`

```
AIService.java              - Ollama integration & LLM logic
```

### Configuration File - `src/main/resources/`

```
application.properties      - Spring Boot configuration
```

---

## 📊 Database Tables

| Table | Purpose | Key Fields |
|-------|---------|-----------|
| users | User accounts | id, username, email |
| income | Income records | id, source, amount, date |
| expense | Expense records | id, category, amount, date |
| mutual_funds | MF investments | id, fund_name, amount_invested, current_value |
| stocks | Stock holdings | id, ticker_symbol, quantity, current_price |
| goals | Financial goals | id, goal_name, target_amount, current_amount |
| insurance | Insurance policies | id, policy_name, premium_amount |
| loans | Loan details | id, loan_name, principal_amount, monthly_emi |
| credit_cards | Credit cards | id, card_name, credit_limit, current_balance |
| settings | App settings | id, setting_key, setting_value |

---

## 🔗 API Endpoint Categories

### Dashboard
```
GET /api/dashboard
```

### Income & Expenses
```
GET    /api/income
POST   /api/income
PUT    /api/income/{id}
DELETE /api/income/{id}

GET    /api/expense
POST   /api/expense
PUT    /api/expense/{id}
DELETE /api/expense/{id}
```

### Budget
```
GET /api/budget/summary
GET /api/budget/expenses/category/{category}
GET /api/budget/income/source/{source}
```

### Investments
```
GET    /api/mutual-funds
POST   /api/mutual-funds
GET    /api/mutual-funds/summary
PUT    /api/mutual-funds/{id}
DELETE /api/mutual-funds/{id}

GET    /api/stocks
POST   /api/stocks
GET    /api/stocks/summary
PUT    /api/stocks/{id}
DELETE /api/stocks/{id}
```

### Goals
```
GET    /api/goals
POST   /api/goals
GET    /api/goals/{id}
GET    /api/goals/status/{status}
GET    /api/goals/type/{type}
GET    /api/goals/summary
PUT    /api/goals/{id}
DELETE /api/goals/{id}
```

### Insurance
```
GET    /api/insurance
POST   /api/insurance
GET    /api/insurance/{id}
GET    /api/insurance/type/{type}
GET    /api/insurance/active
GET    /api/insurance/summary
PUT    /api/insurance/{id}
DELETE /api/insurance/{id}
```

### Loans
```
GET    /api/loans
POST   /api/loans
GET    /api/loans/{id}
GET    /api/loans/type/{type}
GET    /api/loans/active
GET    /api/loans/summary
PUT    /api/loans/{id}
DELETE /api/loans/{id}
```

### Credit Cards
```
GET    /api/credit-cards
POST   /api/credit-cards
GET    /api/credit-cards/{id}
GET    /api/credit-cards/active
GET    /api/credit-cards/issuer/{issuer}
GET    /api/credit-cards/summary
PUT    /api/credit-cards/{id}
DELETE /api/credit-cards/{id}
```

### AI Assistant
```
POST /api/ai/ask
```

---

## 🚀 Quick Start Guide

### 1. Setup Environment
   - Install Java 21
   - Install IntelliJ IDEA
   - (Optional) Install Ollama for AI

### 2. Open Project
   - File → Open → select financial-os folder

### 3. Run Application
   - Click green ▶ Run button
   - Wait for console: "Financial OS is running!"

### 4. Test API
   ```
   http://localhost:8080/api/dashboard
   ```

### 5. Read Documentation
   - Start with README.md
   - Follow SETUP_GUIDE.md
   - Reference API_DOCUMENTATION.md

---

## 🔍 File Statistics

| Category | Count |
|----------|-------|
| Controllers | 11 |
| Services | 8 |
| Repositories | 10 |
| Models/Entities | 10 |
| Configuration Files | 2 |
| DTOs | 1 |
| Documentation Files | 7 |
| **Total** | **49+** |

---

## 📝 Documentation Reference

### For Users/Managers
1. README.md - Overview & getting started
2. SETUP_GUIDE.md - Installation steps
3. API_DOCUMENTATION.md - API examples

### For Developers
1. DEVELOPER_REFERENCE.md - Code patterns & architecture
2. DATABASE_SCHEMA.md - Database design
3. IMPLEMENTATION_SUMMARY.md - What was built
4. This file (INDEX.md) - Quick navigation

---

## 🎯 Key Concepts

### Layered Architecture
```
Controllers (REST API)
    ↓
Services (Business Logic)
    ↓
Repositories (Data Access)
    ↓
Entities (Database)
    ↓
SQLite Database
```

### Request Flow
```
Client HTTP Request
    ↓
Controller (Handle request)
    ↓
Service (Process business logic)
    ↓
Repository (Database query)
    ↓
Entity (Database row)
    ↓
Response (JSON)
```

---

## 💾 Database File

- **Location:** Project root `/financial.db`
- **Type:** SQLite
- **Tables:** 10
- **Auto-created:** On first API request
- **Portable:** Single file, can be backed up easily

---

## 🔐 Environment Configuration

**File:** `src/main/resources/application.properties`

```properties
# Server
server.port=8080

# Database
spring.datasource.url=jdbc:sqlite:financial.db

# Ollama AI
ollama.baseUrl=http://localhost:11434
ollama.model=qwen:8b
ollama.timeout=300
```

---

## 🧪 Testing

### Using curl (PowerShell)

Get dashboard:
```powershell
curl http://localhost:8080/api/dashboard
```

Add income:
```powershell
curl -X POST http://localhost:8080/api/income `
  -H "Content-Type: application/json" `
  -d '{"source":"Salary","amount":50000,"date":"2026-07-09"}'
```

Ask AI:
```powershell
curl -X POST http://localhost:8080/api/ai/ask `
  -H "Content-Type: application/json" `
  -d '{"question":"How much did I spend?"}'
```

### Using Postman
1. Import endpoints from API_DOCUMENTATION.md
2. Set base URL: `http://localhost:8080`
3. Test each endpoint

---

## 🔄 Development Workflow

### Adding New Feature

1. **Create Entity** → `model/FeatureName.java`
2. **Create Repository** → `repository/FeatureNameRepository.java`
3. **Create Service** → `service/FeatureNameService.java`
4. **Create Controller** → `controller/FeatureNameController.java`
5. **Update Documentation** → Add to API_DOCUMENTATION.md
6. **Test** → Use curl or Postman

Refer to DEVELOPER_REFERENCE.md for detailed examples.

---

## 📦 Dependencies

Main dependencies in `pom.xml`:

```xml
<!-- Spring Boot -->
spring-boot-starter-web
spring-boot-starter-data-jpa

<!-- Database -->
sqlite-jdbc
hibernate-community-dialects

<!-- Dev Tools -->
spring-boot-devtools
spring-boot-starter-test
```

---

## 🎓 Learning Path

1. **New to project?**
   - Start: README.md
   - Then: SETUP_GUIDE.md
   - Next: Try first API call

2. **Want to understand architecture?**
   - Read: DEVELOPER_REFERENCE.md
   - Then: DATABASE_SCHEMA.md
   - Explore: Source code files

3. **Need to add feature?**
   - Review: DEVELOPER_REFERENCE.md (Adding New Feature section)
   - Copy: Existing controller pattern
   - Test: Use curl examples

4. **API integration?**
   - Read: API_DOCUMENTATION.md
   - Copy: Example requests
   - Modify: For your use case

---

## ✅ Checklist

- [ ] Java 21 installed
- [ ] IntelliJ IDEA installed
- [ ] Project opened
- [ ] Maven dependencies loaded
- [ ] Application running (green ▶ button)
- [ ] Dashboard API working (`/api/dashboard`)
- [ ] Sample data added (POST /api/income)
- [ ] Ollama installed (optional, for AI)
- [ ] Tests passing

---

## 🔗 Important Locations

```
Project Root:
├── README.md                    ← Start here
├── SETUP_GUIDE.md              ← Setup instructions
├── API_DOCUMENTATION.md        ← API reference
├── DATABASE_SCHEMA.md          ← Database structure
├── DEVELOPER_REFERENCE.md      ← Code patterns
├── IMPLEMENTATION_SUMMARY.md   ← What was built
├── INDEX.md                    ← This file
├── pom.xml                     ← Maven config
├── financial.db                ← Database (auto-created)
└── src/main/java/com/financialos/
    ├── controller/             ← REST endpoints
    ├── service/                ← Business logic
    ├── repository/             ← Data access
    ├── model/                  ← Entities
    ├── ai/                     ← AI integration
    └── config/                 ← Configuration
```

---

## 🚨 Troubleshooting Quick Links

| Issue | Reference |
|-------|-----------|
| Setup problems | SETUP_GUIDE.md → Troubleshooting |
| API not working | API_DOCUMENTATION.md → Error Responses |
| Database issues | DATABASE_SCHEMA.md → Database Maintenance |
| Coding help | DEVELOPER_REFERENCE.md → Common Patterns |
| Architecture | DEVELOPER_REFERENCE.md → Project Architecture |

---

## 🎉 You're All Set!

**Next Steps:**
1. Follow SETUP_GUIDE.md to get running
2. Test endpoints from API_DOCUMENTATION.md
3. Explore the code and database
4. Start Phase 2 (frontend) when ready

**Questions?**
- README.md - Overview questions
- SETUP_GUIDE.md - Installation questions
- API_DOCUMENTATION.md - API questions
- DEVELOPER_REFERENCE.md - Code questions
- DATABASE_SCHEMA.md - Database questions

---

**Last Updated:** July 9, 2026  
**Version:** 1.0.0  
**Status:** Phase 1 Complete ✅



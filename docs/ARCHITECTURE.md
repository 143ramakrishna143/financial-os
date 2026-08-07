# Architecture Guide

## Overview

Financial OS is built with a **layered architecture** with domain-driven design principles. The system is organized to support scalability and maintainability as new features are added.

---

## Current Architecture (v0.2)

```
┌─────────────────────────────────────────┐
│         API Layer (Controllers)         │
│  (DashboardController, AccountController)
└────────────┬────────────────────────────┘
             │
┌────────────▼────────────────────────────┐
│      Application Facade Layer           │
│        (FinanceFacade)                  │
└────────────┬────────────────────────────┘
             │
┌────────────▼────────────────────────────┐
│      Service Layer                      │
│  (Business Logic & Orchestration)       │
│  ├─ Finance Services                    │
│  ├─ Widget Services                     │
│  ├─ Event Services                      │
│  └─ AI Services                         │
└────────────┬────────────────────────────┘
             │
┌────────────▼────────────────────────────┐
│      Repository Layer (Data Access)     │
│  (JPA Repositories)                     │
└────────────┬────────────────────────────┘
             │
┌────────────▼────────────────────────────┐
│         Database (H2/PostgreSQL)        │
└─────────────────────────────────────────┘
```

---

## Package Structure

```
src/main/java/com/financialos/
│
├── config/                 # Spring configuration beans
│   └── DatabaseConfig, JpaConfig
│
├── controller/             # REST API endpoints
│   ├── DashboardController
│   ├── AccountController
│   ├── TransactionController
│   └── v2/                 # API versioning
│
├── dto/                    # Data Transfer Objects
│   ├── request/
│   ├── response/
│   └── widget/
│
├── mapper/                 # Entity ↔ DTO converters
│   └── (EntityMappers)
│
├── model/                  # JPA entities
│   ├── Account
│   ├── Transaction
│   ├── Budget
│   ├── Goal
│   ├── Portfolio
│   └── User
│
├── repository/             # Data access layer
│   └── (JPARepositories)
│
├── service/                # Business logic
│   ├── FinanceFacade       # Primary entry point
│   ├── finance/
│   │   ├── AccountService
│   │   ├── TransactionService
│   │   ├── BudgetService
│   │   ├── GoalService
│   │   └── FinanceEngineService (Ledger)
│   ├── widget/             # Dashboard widgets
│   │   ├── NetWorthWidgetService
│   │   ├── CashFlowWidgetService
│   │   ├── GoalWidgetService
│   │   └── InvestmentWidgetService
│   ├── event/              # Event handling
│   │   └── FinanceEventPublisher
│   ├── ai/                 # AI/ML services
│   │   └── AIService
│   └── migration/          # Data migration
│
└── util/                   # Utilities
    ├── CurrencyConverter
    ├── DateUtils
    └── ValidationUtils
```

---

## Design Patterns Used

### 1. **Facade Pattern** (FinanceFacade)
- Single entry point for all business operations
- Hides complexity of underlying services
- Ensures consistent transaction management

### 2. **Service Layer Pattern**
- Separates business logic from web layer
- Reusable across multiple controllers/clients
- Transaction boundaries managed here

### 3. **Repository Pattern**
- Abstracts data access logic
- Allows easy testing with mock repositories
- Leverages Spring Data JPA

### 4. **DTO Pattern**
- Controllers never expose entities directly
- Decouples API contracts from database schema
- Enables different representations for different clients

### 5. **Event-Driven Architecture**
- Loose coupling between services
- Asynchronous workflows (e.g., budget alerts)
- Extensible without modifying core services

---

## Domain Model

### Core Entities

**Account** (Foundation)
- Parent-child relationships for account hierarchies
- Multiple account types: asset, liability, income, expense, equity
- Tracks balances via ledger entries

**Transaction** (Core Operation)
- Double-entry accounting: each transaction affects two accounts
- Comprehensive types: income, expense, transfer, investment, dividend
- Immutable after creation (audit trail)

**Ledger Entry** (Accounting Core)
- Atomic unit of double-entry bookkeeping
- Links transactions to accounts with debit/credit amounts

**Budget** (Financial Planning)
- Recurring budgets with expense tracking
- Rollover functionality for unused budget
- Alerts when exceeded

**Goal** (Financial Goals)
- Long-term financial objectives
- Milestone tracking
- Progress visualization

**Portfolio** (Investments)
- Holdings of stocks and mutual funds
- Current value calculation
- Performance tracking

---

## Key Decisions

### 1. Why Double-Entry Ledger?
- **Correctness**: Guarantees balanced books (Assets = Liabilities + Equity)
- **Auditability**: Complete transaction history
- **Flexibility**: Supports complex financial scenarios
- **Standards**: Follows accounting principles

### 2. Why FinanceFacade?
- **Consistency**: All operations flow through facade
- **Transaction Management**: Ensures ACID properties
- **Service Orchestration**: Coordinates multiple services
- **Extensibility**: Easy to add new workflows

### 3. Why Widget Services?
- **Separation of Concerns**: Dashboard logic separate from transaction logic
- **Reusability**: Widgets can be combined in different dashboards
- **Performance**: Optimized queries per widget
- **Testing**: Easy to test widget calculations independently

### 4. Why BigDecimal?
- **Precision**: No floating-point errors in financial calculations
- **Rounding**: Explicit control over rounding behavior
- **Standards**: Industry standard for financial systems

---

## Future Architecture (Planned)

### Phase 1: Vertical Slicing (v0.4+)

Transition from horizontal layers to feature-based modules:

```
finance/
  ├── controller/
  ├── service/
  ├── repository/
  ├── dto/
  └── model/

portfolio/
  ├── controller/
  ├── service/
  ├── repository/
  ├── dto/
  └── model/

dashboard/
  ├── controller/
  ├── service/
  ├── widget/
  └── dto/

ai/
  ├── service/
  ├── prompt/
  └── dto/
```

### Phase 2: Event Sourcing (v1.0+)
- Replace direct database updates with event streams
- Enable temporal queries (point-in-time views)
- Improve auditability

### Phase 3: CQRS (v1.0+)
- Separate read and write models
- Optimize queries for reporting
- Improve performance at scale

---

## Development Guidelines

1. **Always use FinanceFacade** for business operations
2. **Keep Services focused** - each service has one primary responsibility
3. **Use DTOs** - never expose entities through API
4. **Publish events** - other services listen and react
5. **Test at service layer** - easier to test than controllers
6. **Use BigDecimal** - no double/float for financial calculations
7. **Leverage transactions** - use @Transactional appropriately

---

## Performance Considerations

- **Ledger Queries**: Indexed on account_id and transaction_date
- **Widget Calculations**: Use JPA projections for aggregations
- **Event Processing**: Asynchronous where possible
- **Cache Strategy**: Consider caching widget results (cache invalidation on transaction)

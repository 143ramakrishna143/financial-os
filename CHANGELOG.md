# Changelog

All notable changes to the Financial OS project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

---

## [0.2.0] - 2025-07-10

### Added
- **Finance Engine Core**
  - Double-entry ledger system with account-based transactions
  - Comprehensive transaction types (income, expense, transfer, investment, dividend)
  - Budget management with rollover and expense tracking
  - Goal tracking with milestone and progress calculation
  - Portfolio management for stocks and mutual funds
  - Loan and insurance tracking with amortization
  - Multi-currency support with exchange rates
  - Settings management with personal financial preferences

- **Widget Services**
  - NetWorthWidgetService for net worth calculations across all assets
  - CashFlowWidgetService for income/expense analysis
  - GoalWidgetService for progress tracking
  - InvestmentWidgetService for portfolio analysis
  - RecentTransactionsWidgetService for transaction history

- **Event System**
  - Event-driven architecture with FinanceEventPublisher
  - Event types: TransactionCreated, BudgetExceeded, GoalMilestoneReached, PortfolioUpdated
  - Event listeners for complex workflows

- **API Design**
  - RESTful endpoints for all major entities
  - DTO layer for API responses
  - Standardized error handling
  - API versioning support (v1, v2)

- **AI Integration**
  - Basic AIService for financial insights
  - Integration with business logic without coupling

- **Documentation**
  - Comprehensive README.md
  - API_DOCUMENTATION.md with endpoint details
  - ARCHITECTURAL_DECISIONS.md
  - DATABASE_SCHEMA.md
  - DEVELOPER_REFERENCE.md
  - IMPLEMENTATION_SUMMARY.md

### Changed
- Improved account hierarchy with parent-child relationships
- Enhanced transaction validation and error handling
- Optimized ledger queries with JPA projections

### Fixed
- Decimal precision handling with BigDecimal
- Cascading deletes for account hierarchies
- Currency conversion edge cases

---

## [0.1.0] - 2025-06-15

### Added
- Initial project setup with Spring Boot 3.x
- JPA entity modeling for core finance domain
- Basic repository layer
- Controller layer with REST endpoints
- Service layer with business logic
- Maven build configuration
- Java 21 compatibility

---

## Roadmap

### [0.3.0] - Dashboard OS (Q3 2025)
- Advanced dashboard layouts
- Custom widget configuration
- Real-time data refresh
- Export functionality (PDF, CSV)

### [0.4.0] - Portfolio Intelligence (Q4 2025)
- Advanced portfolio analytics
- Asset allocation recommendations
- Risk profiling
- Performance benchmarking

### [0.5.0] - Automation (Q1 2026)
- Rule-based transaction automation
- Recurring transaction templates
- Budget alerts and notifications
- Tax loss harvesting recommendations

### [1.0.0] - AI Advisor (Q2 2026)
- Machine learning-based recommendations
- Natural language financial queries
- Predictive financial planning
- Integration with external APIs

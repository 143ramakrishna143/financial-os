# Roadmap

## Vision
Financial OS is a **domain-driven platform** for personal finance management, built with the latest architectural patterns and designed for scale. It evolves through focused phases, each adding value without compromising existing functionality.

---

## Release Roadmap

### ✅ v0.1 - Foundation (Completed - June 2025)
**Goal**: Establish Spring Boot project with JPA entities and basic REST API

- [x] Project setup with Spring Boot 3.x
- [x] JPA entity modeling
- [x] Repository layer
- [x] Basic REST controllers
- [x] Maven build configuration

**Status**: Core platform established

---

### ✅ v0.2 - Money Engine (Completed - July 2025)
**Goal**: Implement double-entry ledger and comprehensive financial domain

**Finance Engine**
- [x] Double-entry ledger with account-based transactions
- [x] Transaction types: income, expense, transfer, investment, dividend
- [x] Budget management with rollover tracking
- [x] Goal tracking with progress calculation
- [x] Portfolio management (stocks, mutual funds)
- [x] Loan and insurance tracking
- [x] Multi-currency support

**Application Layer**
- [x] FinanceFacade as primary entry point
- [x] Comprehensive service layer
- [x] DTO layer for API responses
- [x] Event-driven architecture

**Dashboard Infrastructure**
- [x] Widget services pattern
- [x] NetWorthWidgetService
- [x] CashFlowWidgetService
- [x] GoalWidgetService
- [x] InvestmentWidgetService

**Platform Quality**
- [x] Documentation (README, API docs, architecture)
- [x] Error handling and validation
- [x] API versioning support

**Status**: Money Engine complete. Ready for Dashboard OS phase.

---

### 🎯 v0.3 - Dashboard OS (Q3 2025)
**Goal**: Build comprehensive dashboard UI with widgets and analytics

**Dashboard Engine**
- [ ] Advanced dashboard layouts
- [ ] Responsive grid system
- [ ] Widget configuration and personalization
- [ ] Real-time data refresh (WebSocket support)
- [ ] Dashboard templates (Minimalist, Detailed, Analytics)

**Analytics & Insights**
- [ ] Net worth trending (monthly, yearly)
- [ ] Cash flow analysis (by category, by time period)
- [ ] Budget performance metrics
- [ ] Goal progress visualization
- [ ] Investment performance tracking

**Export & Reports**
- [ ] PDF export for dashboards
- [ ] CSV export for transactions
- [ ] Monthly financial reports
- [ ] Year-end summaries

**Mobile Responsiveness**
- [ ] Tablet-optimized layouts
- [ ] Mobile-first design (future mobile app foundation)

**Status**: TBD

---

### 🎯 v0.4 - Portfolio Intelligence (Q4 2025)
**Goal**: Advanced investment analytics and recommendations

**Portfolio Analysis**
- [ ] Asset allocation breakdown (stocks vs bonds vs crypto)
- [ ] Sector exposure analysis
- [ ] Geographic concentration
- [ ] Performance benchmarking vs indices

**Risk Assessment**
- [ ] Risk profiling questionnaire
- [ ] Portfolio risk score
- [ ] Volatility analysis
- [ ] Correlation analysis

**Recommendations**
- [ ] Rebalancing suggestions
- [ ] Diversification opportunities
- [ ] Tax-efficient investing tips
- [ ] Cost optimization (fees and commissions)

**Integration**
- [ ] Market data API integration (live prices)
- [ ] Historical performance data
- [ ] Index benchmarks

**Status**: TBD

---

### 🎯 v0.5 - Automation (Q1 2026)
**Goal**: Rule-based automation for financial workflows

**Recurring Transactions**
- [ ] Templates for recurring transactions
- [ ] Automatic transaction matching
- [ ] Duplicate detection

**Rules Engine**
- [ ] Categorization rules (auto-categorize transactions)
- [ ] Notification rules (alerts and triggers)
- [ ] Action rules (auto-transfer, auto-pay)

**Alerts & Notifications**
- [ ] Budget alerts
- [ ] Goal milestone notifications
- [ ] Large transaction alerts
- [ ] Investment performance alerts

**Advanced Features**
- [ ] Tax loss harvesting recommendations
- [ ] Contribution room calculations (RRSP, TFSA)
- [ ] Debt payoff optimization
- [ ] Savings rate tracking

**Status**: TBD

---

### 🎯 v1.0 - AI Advisor (Q2 2026)
**Goal**: ML-powered personalized financial guidance

**Intelligent Recommendations**
- [ ] Machine learning model training on user data
- [ ] Personalized financial advice
- [ ] Spending pattern analysis
- [ ] Savings opportunity detection

**Natural Language Interface**
- [ ] Chat-based financial queries
- [ ] Voice input for transactions
- [ ] Context-aware responses

**Predictive Analytics**
- [ ] Future income/expense projections
- [ ] Retirement readiness analysis
- [ ] Cashflow forecasting
- [ ] Emergency fund adequacy

**External Integrations**
- [ ] Bank account auto-sync
- [ ] Investment account aggregation
- [ ] Crypto exchange integration
- [ ] Real estate valuation APIs

**Status**: TBD

---

## Concurrent Initiatives

### Code Quality & Infrastructure

#### Sprint 2.4 - Cleanup & CI (July 2025)
- [x] Add `.gitignore`
- [x] Add MIT `LICENSE`
- [x] Add `CHANGELOG.md`
- [x] Create documentation structure
- [x] Add unit tests for core services
- [ ] GitHub Actions CI workflow
- [ ] Code coverage reporting

#### Future Initiatives
- [ ] SonarQube integration
- [ ] Performance testing
- [ ] Security scanning
- [ ] Load testing

---

### Architecture Evolution

#### Planned Transitions

**Phase A: Vertical Slicing (v0.4+)**
- Transition from horizontal layers to feature-based modules
- Improves modularity and team parallelization
- Each feature module owns its own service/repo/dto

**Phase B: Event Sourcing (v1.0+)**
- Replace direct database updates with event streams
- Enable point-in-time queries
- Improve auditability and debugging

**Phase C: CQRS (v1.0+)**
- Separate read and write models
- Optimize queries for reporting
- Improve performance at scale

---

## Key Milestones

| Milestone | Target | Key Deliverable |
|-----------|--------|-----------------|
| v0.2 Complete | ✅ July 2025 | Money Engine Stable |
| Sprint 2.4 Done | July 2025 | CI/CD Ready |
| v0.3 Release | Q3 2025 | Dashboard MVP |
| v0.4 Release | Q4 2025 | Portfolio Analytics |
| v0.5 Release | Q1 2026 | Rule Automation |
| v1.0 Release | Q2 2026 | AI Advisor Beta |

---

## Success Criteria

### For Each Release
- ✅ All tests passing
- ✅ CI/CD green
- ✅ Documentation up-to-date
- ✅ No technical debt introduced
- ✅ Backward compatibility maintained (if applicable)

### Platform-Wide
- Clean code (SonarQube A grade)
- >80% test coverage for critical paths
- <100ms response time for dashboard queries
- Zero data integrity issues
- Full audit trail of transactions

---

## Decision Log

### Why Focus on Dashboard Next?
- Money Engine is complete and stable
- Dashboard directly demonstrates value to users
- Widget services already in place
- Foundation for future AI features

### Why Not Start AI Immediately?
- Money Engine needs time to stabilize in production
- Dashboard provides better training data
- AI quality improves with real financial data
- Foundation work (NLP, ML pipelines) takes time

### Why Vertical Slicing Later?
- Current project size is manageable with horizontal layers
- Vertical slicing optimal when 10+ domain modules exist
- Easier transition after stable v1.0

---

## Backlog

### High Priority
- [ ] Performance optimization for large ledgers (10k+ transactions)
- [ ] Batch transaction import
- [ ] Multi-user support with role-based access
- [ ] API rate limiting

### Medium Priority
- [ ] Dark mode UI
- [ ] Internationalization (i18n)
- [ ] Accessibility improvements
- [ ] Mobile app (React Native)

### Lower Priority
- [ ] Blockchain integration (future)
- [ ] Crypto wallet sync
- [ ] Real estate tracking enhancements
- [ ] Insurance policy management

---

## Contact & Questions

For questions about roadmap priorities or architectural decisions, consult:
- `ARCHITECTURAL_DECISIONS.md` - Design decisions
- `IMPLEMENTATION_SUMMARY.md` - Current state
- `docs/ARCHITECTURE.md` - Technical architecture

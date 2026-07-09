# Financial OS - Database Schema

Complete database schema documentation for Financial OS Phase 1.

## Database Type: SQLite

**File:** `financial.db` (auto-created in project root)

---

## 📋 Tables Overview

| Table | Purpose | Records |
|-------|---------|---------|
| `users` | User accounts | 1+ |
| `income` | Income sources and amounts | Many |
| `expense` | Expense tracking by category | Many |
| `mutual_funds` | Mutual fund investments | Many |
| `stocks` | Stock portfolio | Many |
| `goals` | Financial goals | Many |
| `insurance` | Insurance policies | Many |
| `loans` | Loan details | Many |
| `credit_cards` | Credit card information | Many |
| `settings` | Application settings | Many |

---

## 1. Users Table

Stores user account information.

```sql
CREATE TABLE users (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    username VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL
);
```

### Fields

| Field | Type | Description |
|-------|------|-------------|
| `id` | INTEGER | Primary Key (auto-increment) |
| `username` | VARCHAR(255) | Unique username |
| `email` | VARCHAR(255) | User email address |
| `password` | VARCHAR(255) | Encrypted password (Phase 2) |
| `created_at` | TIMESTAMP | Account creation time |
| `updated_at` | TIMESTAMP | Last update time |

### Example Data
```json
{
  "id": 1,
  "username": "john_doe",
  "email": "john@example.com",
  "password": "encrypted...",
  "created_at": "2026-07-09T10:00:00",
  "updated_at": "2026-07-09T10:00:00"
}
```

---

## 2. Income Table

Tracks all income sources and amounts.

```sql
CREATE TABLE income (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    source VARCHAR(255) NOT NULL,
    amount DOUBLE NOT NULL,
    date DATE NOT NULL,
    notes TEXT
);
```

### Fields

| Field | Type | Description |
|-------|------|-------------|
| `id` | INTEGER | Primary Key |
| `source` | VARCHAR(255) | Income source (e.g., Salary, Bonus) |
| `amount` | DOUBLE | Income amount in ₹ |
| `date` | DATE | Date of income |
| `notes` | TEXT | Optional notes |

### Common Income Sources
- Salary
- Bonus
- Interest
- Dividend
- Freelance Income
- Business Income
- Rental Income
- Gift
- Refund
- Other

### Example Data
```json
{
  "id": 1,
  "source": "Salary",
  "amount": 70000,
  "date": "2026-07-01",
  "notes": "July monthly salary"
}
```

### Queries

```sql
-- Total income in a month
SELECT SUM(amount) FROM income WHERE strftime('%Y-%m', date) = '2026-07';

-- Income by source
SELECT source, SUM(amount) FROM income GROUP BY source;

-- Recent income
SELECT * FROM income ORDER BY date DESC LIMIT 10;
```

---

## 3. Expense Table

Tracks all expenses by category.

```sql
CREATE TABLE expense (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    category VARCHAR(255) NOT NULL,
    amount DOUBLE NOT NULL,
    date DATE NOT NULL,
    notes TEXT
);
```

### Fields

| Field | Type | Description |
|-------|------|-------------|
| `id` | INTEGER | Primary Key |
| `category` | VARCHAR(255) | Expense category |
| `amount` | DOUBLE | Expense amount in ₹ |
| `date` | DATE | Date of expense |
| `notes` | TEXT | Optional notes |

### Common Expense Categories
- Food
- Groceries
- Petrol
- Bills
- Insurance
- Shopping
- Medical
- Entertainment
- Transport
- Utilities
- Subscriptions
- Dining
- Travel

### Example Data
```json
{
  "id": 1,
  "category": "Food",
  "amount": 500,
  "date": "2026-07-09",
  "notes": "Groceries"
}
```

### Queries

```sql
-- Total expenses in a month
SELECT SUM(amount) FROM expense WHERE strftime('%Y-%m', date) = '2026-07';

-- Expenses by category
SELECT category, SUM(amount) FROM expense GROUP BY category;

-- Monthly breakdown
SELECT strftime('%Y-%m', date) as month, SUM(amount) FROM expense GROUP BY month;
```

---

## 4. Mutual Funds Table

Tracks mutual fund investments and SIPs.

```sql
CREATE TABLE mutual_funds (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    fund_name VARCHAR(255) NOT NULL,
    fund_code VARCHAR(50) NOT NULL,
    amount_invested DOUBLE NOT NULL,
    current_value DOUBLE NOT NULL,
    sip_amount DOUBLE,
    sip_start_date DATE,
    investment_date DATE NOT NULL,
    notes TEXT
);
```

### Fields

| Field | Type | Description |
|-------|------|-------------|
| `id` | INTEGER | Primary Key |
| `fund_name` | VARCHAR(255) | Name of mutual fund |
| `fund_code` | VARCHAR(50) | Fund code/ISIN |
| `amount_invested` | DOUBLE | Total amount invested in ₹ |
| `current_value` | DOUBLE | Current portfolio value in ₹ |
| `sip_amount` | DOUBLE | Monthly SIP amount (optional) |
| `sip_start_date` | DATE | When SIP started |
| `investment_date` | DATE | Initial investment date |
| `notes` | TEXT | Notes |

### Calculated Fields
- **Profit**: `current_value - amount_invested`
- **Return %**: `(Profit / amount_invested) * 100`

### Example Data
```json
{
  "id": 1,
  "fundName": "SBI Bluechip Fund",
  "fundCode": "SBICF001",
  "amountInvested": 100000,
  "currentValue": 125000,
  "sipAmount": 5000,
  "sipStartDate": "2026-01-01",
  "investmentDate": "2026-01-01",
  "notes": "Long-term equity fund"
}
```

### Queries

```sql
-- Total MF value
SELECT SUM(current_value) FROM mutual_funds;

-- Total profit/loss
SELECT SUM(current_value - amount_invested) FROM mutual_funds;

-- Active SIPs
SELECT * FROM mutual_funds WHERE sip_amount IS NOT NULL;
```

---

## 5. Stocks Table

Tracks stock holdings and valuations.

```sql
CREATE TABLE stocks (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    ticker_symbol VARCHAR(20) NOT NULL,
    company_name VARCHAR(255) NOT NULL,
    quantity INTEGER NOT NULL,
    buy_price DOUBLE NOT NULL,
    current_price DOUBLE NOT NULL,
    total_invested DOUBLE NOT NULL,
    purchase_date DATE NOT NULL,
    notes TEXT
);
```

### Fields

| Field | Type | Description |
|-------|------|-------------|
| `id` | INTEGER | Primary Key |
| `ticker_symbol` | VARCHAR(20) | Stock ticker (e.g., HAL, RELIANCE) |
| `company_name` | VARCHAR(255) | Company name |
| `quantity` | INTEGER | Number of shares |
| `buy_price` | DOUBLE | Price per share bought at |
| `current_price` | DOUBLE | Current price per share |
| `total_invested` | DOUBLE | `quantity * buy_price` |
| `purchase_date` | DATE | Purchase date |
| `notes` | TEXT | Notes |

### Calculated Fields
- **Current Value**: `quantity * current_price`
- **Profit/Loss**: `current_value - total_invested`
- **Return %**: `(Profit / total_invested) * 100`

### Example Data
```json
{
  "id": 1,
  "tickerSymbol": "HAL",
  "companyName": "Hindustan Aeronautics",
  "quantity": 100,
  "buyPrice": 3500,
  "currentPrice": 4200,
  "totalInvested": 350000,
  "purchaseDate": "2026-01-15",
  "notes": "Aerospace stock"
}
```

### Popular Indian Stocks (Examples)
- HAL (Hindustan Aeronautics Limited)
- BEL (Bharat Electronics Limited)
- KPIT (KPIT Technologies)
- L&T (Larsen & Toubro)
- Waaree (Waaree Energies)

### Queries

```sql
-- Total stock value
SELECT SUM(quantity * current_price) FROM stocks;

-- Top gainers
SELECT ticker_symbol, (current_price - buy_price) as gain 
FROM stocks ORDER BY gain DESC LIMIT 5;

-- Highest invested stock
SELECT * FROM stocks ORDER BY total_invested DESC LIMIT 1;
```

---

## 6. Goals Table

Tracks financial goals and progress.

```sql
CREATE TABLE goals (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    goal_name VARCHAR(255) NOT NULL,
    goal_type VARCHAR(100) NOT NULL,
    target_amount DOUBLE NOT NULL,
    current_amount DOUBLE NOT NULL,
    target_date DATE NOT NULL,
    status VARCHAR(50) NOT NULL,
    notes TEXT
);
```

### Fields

| Field | Type | Description |
|-------|------|-------------|
| `id` | INTEGER | Primary Key |
| `goal_name` | VARCHAR(255) | Goal name |
| `goal_type` | VARCHAR(100) | Type (Farm, House, etc.) |
| `target_amount` | DOUBLE | Target amount in ₹ |
| `current_amount` | DOUBLE | Amount saved so far |
| `target_date` | DATE | Target completion date |
| `status` | VARCHAR(50) | In Progress / Completed / On Hold |
| `notes` | TEXT | Notes |

### Goal Types
- Farm
- House
- Marriage
- Emergency Fund
- Retirement
- Car
- Education
- Vacation

### Status Values
- `In Progress`
- `Completed`
- `On Hold`

### Calculated Fields
- **Remaining**: `target_amount - current_amount`
- **Progress %**: `(current_amount / target_amount) * 100`

### Example Data
```json
{
  "id": 1,
  "goalName": "Emergency Fund",
  "goalType": "Emergency Fund",
  "targetAmount": 500000,
  "currentAmount": 150000,
  "targetDate": "2027-12-31",
  "status": "In Progress",
  "notes": "6 months of expenses"
}
```

### Queries

```sql
-- Goals progress
SELECT goal_name, current_amount, target_amount, 
       ROUND((current_amount * 100.0 / target_amount), 2) as progress_percent
FROM goals;

-- Completed goals
SELECT * FROM goals WHERE status = 'Completed';
```

---

## 7. Insurance Table

Tracks insurance policies.

```sql
CREATE TABLE insurance (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    policy_name VARCHAR(255) NOT NULL,
    insurance_type VARCHAR(100) NOT NULL,
    policy_number VARCHAR(100) NOT NULL,
    premium_amount DOUBLE NOT NULL,
    premium_due_date DATE,
    policy_start_date DATE NOT NULL,
    policy_end_date DATE NOT NULL,
    provider VARCHAR(255),
    status VARCHAR(50) NOT NULL,
    notes TEXT
);
```

### Fields

| Field | Type | Description |
|-------|------|-------------|
| `id` | INTEGER | Primary Key |
| `policy_name` | VARCHAR(255) | Policy name |
| `insurance_type` | VARCHAR(100) | Type (Health, Life, etc.) |
| `policy_number` | VARCHAR(100) | Policy number |
| `premium_amount` | DOUBLE | Annual/Monthly premium |
| `premium_due_date` | DATE | Next premium due date |
| `policy_start_date` | DATE | When policy started |
| `policy_end_date` | DATE | When policy ends |
| `provider` | VARCHAR(255) | Insurance company |
| `status` | VARCHAR(50) | Active / Inactive / Expired |
| `notes` | TEXT | Notes |

### Insurance Types
- Health
- Life
- Motor
- Home
- Travel
- Pet
- Disability

### Status Values
- `Active`
- `Inactive`
- `Expired`

### Example Data
```json
{
  "id": 1,
  "policyName": "Health Insurance",
  "insuranceType": "Health",
  "policyNumber": "POL123456",
  "premiumAmount": 15000,
  "premiumDueDate": "2026-08-01",
  "policyStartDate": "2026-01-01",
  "policyEndDate": "2027-12-31",
  "provider": "HDFC Insurance",
  "status": "Active",
  "notes": "Family coverage"
}
```

---

## 8. Loans Table

Tracks loan details and EMI.

```sql
CREATE TABLE loans (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    loan_name VARCHAR(255) NOT NULL,
    loan_type VARCHAR(100) NOT NULL,
    principal_amount DOUBLE NOT NULL,
    remaining_amount DOUBLE NOT NULL,
    interest_rate DOUBLE NOT NULL,
    tenure_in_months INTEGER NOT NULL,
    monthly_emi DOUBLE,
    loan_start_date DATE NOT NULL,
    expected_end_date DATE,
    lender VARCHAR(255),
    status VARCHAR(50) NOT NULL,
    notes TEXT
);
```

### Fields

| Field | Type | Description |
|-------|------|-------------|
| `id` | INTEGER | Primary Key |
| `loan_name` | VARCHAR(255) | Loan name |
| `loan_type` | VARCHAR(100) | Type (Home, Auto, etc.) |
| `principal_amount` | DOUBLE | Original loan amount |
| `remaining_amount` | DOUBLE | Outstanding balance |
| `interest_rate` | DOUBLE | Annual interest rate (%) |
| `tenure_in_months` | INTEGER | Loan tenure in months |
| `monthly_emi` | DOUBLE | Monthly EMI amount |
| `loan_start_date` | DATE | When loan started |
| `expected_end_date` | DATE | Expected payoff date |
| `lender` | VARCHAR(255) | Lender bank/company |
| `status` | VARCHAR(50) | Active / Completed |
| `notes` | TEXT | Notes |

### Loan Types
- Home
- Auto
- Personal
- Education
- Business

### Status Values
- `Active`
- `Completed`

### Example Data
```json
{
  "id": 1,
  "loanName": "Home Loan",
  "loanType": "Home",
  "principalAmount": 2000000,
  "remainingAmount": 1500000,
  "interestRate": 6.5,
  "tenureInMonths": 240,
  "monthlyEmi": 13000,
  "loanStartDate": "2020-01-01",
  "expectedEndDate": "2040-01-01",
  "lender": "HDFC Bank",
  "status": "Active",
  "notes": "Home loan for residence"
}
```

---

## 9. Credit Cards Table

Tracks credit card information.

```sql
CREATE TABLE credit_cards (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    card_name VARCHAR(255) NOT NULL,
    card_issuer VARCHAR(100) NOT NULL,
    last_4_digits VARCHAR(4),
    credit_limit DOUBLE NOT NULL,
    current_balance DOUBLE NOT NULL,
    min_due_amount DOUBLE,
    due_date DATE,
    interest_rate DOUBLE,
    status VARCHAR(50) NOT NULL,
    notes TEXT
);
```

### Fields

| Field | Type | Description |
|-------|------|-------------|
| `id` | INTEGER | Primary Key |
| `card_name` | VARCHAR(255) | Card name (e.g., Premium Card) |
| `card_issuer` | VARCHAR(100) | Bank name |
| `last_4_digits` | VARCHAR(4) | Last 4 digits for identification |
| `credit_limit` | DOUBLE | Credit limit |
| `current_balance` | DOUBLE | Current outstanding balance |
| `min_due_amount` | DOUBLE | Minimum due amount |
| `due_date` | DATE | Payment due date |
| `interest_rate` | DOUBLE | Interest rate (%) if unpaid |
| `status` | VARCHAR(50) | Active / Inactive / Closed |
| `notes` | TEXT | Notes |

### Calculated Fields
- **Available Credit**: `credit_limit - current_balance`
- **Utilization %**: `(current_balance / credit_limit) * 100`

### Status Values
- `Active`
- `Inactive`
- `Closed`

### Example Data
```json
{
  "id": 1,
  "cardName": "Premium Card",
  "cardIssuer": "HDFC",
  "last4Digits": "1234",
  "creditLimit": 500000,
  "currentBalance": 125000,
  "minDueAmount": 10000,
  "dueDate": "2026-08-15",
  "interestRate": 2.5,
  "status": "Active",
  "notes": "Cashback card"
}
```

---

## 10. Settings Table

Application-level settings and configuration.

```sql
CREATE TABLE settings (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    setting_key VARCHAR(255) NOT NULL UNIQUE,
    setting_value VARCHAR(255) NOT NULL,
    description TEXT,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL
);
```

### Fields

| Field | Type | Description |
|-------|------|-------------|
| `id` | INTEGER | Primary Key |
| `setting_key` | VARCHAR(255) | Setting key (unique) |
| `setting_value` | VARCHAR(255) | Setting value |
| `description` | TEXT | Description |
| `created_at` | TIMESTAMP | Creation time |
| `updated_at` | TIMESTAMP | Last update |

### Example Settings

```json
[
  {
    "settingKey": "app_name",
    "settingValue": "Financial OS",
    "description": "Application name"
  },
  {
    "settingKey": "default_currency",
    "settingValue": "INR",
    "description": "Default currency"
  },
  {
    "settingKey": "financial_year_start",
    "settingValue": "04-01",
    "description": "Financial year start date"
  }
]
```

---

## Database Relationships

```
Users (1) ── Many (Income)
Users (1) ── Many (Expense)
Users (1) ── Many (MutualFunds)
Users (1) ── Many (Stocks)
Users (1) ── Many (Goals)
Users (1) ── Many (Insurance)
Users (1) ── Many (Loans)
Users (1) ── Many (CreditCards)
```

*Note: Phase 1 doesn't enforce foreign keys; Phase 2 will add user_id to all tables*

---

## Useful SQL Queries

### Dashboard Summary
```sql
SELECT 
    (SELECT SUM(amount) FROM income) as total_income,
    (SELECT SUM(amount) FROM expense) as total_expense,
    (SELECT SUM(amount) FROM income) - (SELECT SUM(amount) FROM expense) as surplus,
    (SELECT SUM(current_value) FROM mutual_funds) as mf_value,
    (SELECT SUM(quantity * current_price) FROM stocks) as stock_value;
```

### Monthly Breakdown
```sql
SELECT 
    strftime('%Y-%m', date) as month,
    SUM(CASE WHEN id IN (SELECT id FROM income) THEN amount ELSE 0 END) as income,
    SUM(CASE WHEN id IN (SELECT id FROM expense) THEN amount ELSE 0 END) as expense
FROM income, expense
GROUP BY month;
```

### Goal Progress
```sql
SELECT 
    goal_name,
    target_amount,
    current_amount,
    ROUND((current_amount * 100.0 / target_amount), 2) as progress_percent,
    ROUND(target_amount - current_amount, 2) as remaining
FROM goals
WHERE status = 'In Progress'
ORDER BY progress_percent DESC;
```

### Investment Summary
```sql
SELECT 
    'Mutual Funds' as asset_type,
    SUM(amount_invested) as invested,
    SUM(current_value) as current_value,
    SUM(current_value - amount_invested) as profit
FROM mutual_funds

UNION ALL

SELECT 
    'Stocks',
    SUM(total_invested),
    SUM(quantity * current_price),
    SUM(quantity * current_price - total_invested)
FROM stocks;
```

---

## Database Backup

To backup `financial.db`:

```powershell
# Windows
Copy-Item financial.db financial.db.backup

# Restore
Copy-Item financial.db.backup financial.db
```

---

## Database Maintenance

### Check Database Integrity
```sql
PRAGMA integrity_check;
```

### Optimize Database
```sql
PRAGMA optimize;
VACUUM;
```

### Reset All Data (Development Only)
```sql
DELETE FROM income;
DELETE FROM expense;
DELETE FROM mutual_funds;
DELETE FROM stocks;
DELETE FROM goals;
DELETE FROM insurance;
DELETE FROM loans;
DELETE FROM credit_cards;
DELETE FROM users;
DELETE FROM settings;
```

---

This schema supports Phase 1 MVP. Phase 2 will add:
- User authentication with user_id foreign keys
- Transaction categories and budgets
- Recurring transactions
- Tax planning
- Advanced reports



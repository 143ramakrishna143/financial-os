# Financial OS - API Documentation

Complete REST API documentation for Financial OS Phase 1.

## Base URL
```
http://localhost:8080
```

---

## 🏠 Dashboard Endpoints

### Get Complete Financial Dashboard
```http
GET /api/dashboard
```

**Response:**
```json
{
  "totalIncome": 150000,
  "totalExpense": 45000,
  "surplus": 105000,
  "netWorth": 155000,
  "mutualFundValue": 200000,
  "stockValue": 150000,
  "liabilities": 250000,
  "goalsProgress": 50000,
  "goalsTarget": 500000
}
```

---

## 💰 Income Endpoints

### List All Income Records
```http
GET /api/income
```

**Response:**
```json
[
  {
    "id": 1,
    "source": "Salary",
    "amount": 70000,
    "date": "2026-07-01",
    "notes": "Monthly salary"
  }
]
```

### Create Income Record
```http
POST /api/income
Content-Type: application/json

{
  "source": "Salary",
  "amount": 70000,
  "date": "2026-07-01",
  "notes": "Monthly salary"
}
```

### Get Specific Income Record
```http
GET /api/income/{id}
```

### Update Income Record
```http
PUT /api/income/{id}
Content-Type: application/json

{
  "source": "Salary",
  "amount": 75000,
  "date": "2026-07-01",
  "notes": "Updated salary"
}
```

### Delete Income Record
```http
DELETE /api/income/{id}
```

---

## 💸 Expense Endpoints

### List All Expenses
```http
GET /api/expense
```

**Response:**
```json
[
  {
    "id": 1,
    "category": "Food",
    "amount": 5000,
    "date": "2026-07-02",
    "notes": "Groceries"
  }
]
```

### Create Expense Record
```http
POST /api/expense
Content-Type: application/json

{
  "category": "Food",
  "amount": 5000,
  "date": "2026-07-02",
  "notes": "Groceries"
}
```

### Get Specific Expense
```http
GET /api/expense/{id}
```

### Update Expense
```http
PUT /api/expense/{id}
Content-Type: application/json

{
  "category": "Groceries",
  "amount": 5500,
  "date": "2026-07-02",
  "notes": "Updated groceries"
}
```

### Delete Expense
```http
DELETE /api/expense/{id}
```

---

## 📊 Budget Endpoints

### Get Budget Summary
```http
GET /api/budget/summary
```

**Response:**
```json
{
  "totalIncome": 150000,
  "totalExpense": 45000,
  "surplus": 105000
}
```

### Get Expenses by Category
```http
GET /api/budget/expenses/category/{category}
```

**Response:**
```json
[
  {
    "id": 1,
    "category": "Food",
    "amount": 5000,
    "date": "2026-07-02",
    "notes": "Groceries"
  }
]
```

### Get Total Expense by Category
```http
GET /api/budget/expenses/category/{category}/total
```

**Response:**
```json
5000
```

### Get Income by Source
```http
GET /api/budget/income/source/{source}
```

### Get Total Income by Source
```http
GET /api/budget/income/source/{source}/total
```

---

## 📈 Mutual Funds Endpoints

### List All Mutual Funds
```http
GET /api/mutual-funds
```

**Response:**
```json
[
  {
    "id": 1,
    "fundName": "SBI Bluechip Fund",
    "fundCode": "SBICF001",
    "amountInvested": 100000,
    "currentValue": 125000,
    "sipAmount": 5000,
    "sipStartDate": "2026-01-01",
    "investmentDate": "2026-01-01",
    "notes": "Long-term investment"
  }
]
```

### Create Mutual Fund Entry
```http
POST /api/mutual-funds
Content-Type: application/json

{
  "fundName": "SBI Bluechip Fund",
  "fundCode": "SBICF001",
  "amountInvested": 100000,
  "currentValue": 125000,
  "sipAmount": 5000,
  "sipStartDate": "2026-01-01"
}
```

### Get Mutual Fund Summary
```http
GET /api/mutual-funds/summary
```

**Response:**
```json
{
  "totalInvested": 200000,
  "totalCurrentValue": 260000,
  "totalProfit": 60000
}
```

### Update Mutual Fund
```http
PUT /api/mutual-funds/{id}
```

### Delete Mutual Fund
```http
DELETE /api/mutual-funds/{id}
```

---

## 📊 Stocks Endpoints

### List All Stocks
```http
GET /api/stocks
```

**Response:**
```json
[
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
]
```

### Create Stock Entry
```http
POST /api/stocks
Content-Type: application/json

{
  "tickerSymbol": "HAL",
  "companyName": "Hindustan Aeronautics",
  "quantity": 100,
  "buyPrice": 3500,
  "currentPrice": 4200,
  "purchaseDate": "2026-01-15"
}
```

### Get Stock Summary
```http
GET /api/stocks/summary
```

**Response:**
```json
{
  "totalInvested": 700000,
  "totalCurrentValue": 850000,
  "totalProfit": 150000
}
```

---

## 🎯 Goals Endpoints

### List All Goals
```http
GET /api/goals
```

**Response:**
```json
[
  {
    "id": 1,
    "goalName": "Emergency Fund",
    "goalType": "Emergency Fund",
    "targetAmount": 500000,
    "currentAmount": 150000,
    "targetDate": "2027-12-31",
    "status": "In Progress",
    "notes": "6 months expenses"
  }
]
```

### Create Goal
```http
POST /api/goals
Content-Type: application/json

{
  "goalName": "Emergency Fund",
  "goalType": "Emergency Fund",
  "targetAmount": 500000,
  "targetDate": "2027-12-31"
}
```

### Get Goals by Status
```http
GET /api/goals/status/{status}
```

Example statuses: `In Progress`, `Completed`, `On Hold`

### Get Goals by Type
```http
GET /api/goals/type/{type}
```

Example types: `Farm`, `House`, `Marriage`, `Emergency Fund`, `Retirement`

### Get Goals Summary
```http
GET /api/goals/summary
```

**Response:**
```json
{
  "totalTargetAmount": 2000000,
  "totalCurrentAmount": 500000
}
```

---

## 🛡️ Insurance Endpoints

### List All Insurance Policies
```http
GET /api/insurance
```

**Response:**
```json
[
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
]
```

### Create Insurance Policy
```http
POST /api/insurance
Content-Type: application/json

{
  "policyName": "Health Insurance",
  "insuranceType": "Health",
  "policyNumber": "POL123456",
  "premiumAmount": 15000,
  "policyStartDate": "2026-01-01",
  "policyEndDate": "2027-12-31"
}
```

### Get Insurance by Type
```http
GET /api/insurance/type/{type}
```

### Get Active Insurance Only
```http
GET /api/insurance/active
```

### Get Insurance Summary
```http
GET /api/insurance/summary
```

**Response:**
```json
{
  "totalAnnualPremium": 60000,
  "activeCount": 4
}
```

---

## 💳 Loan Endpoints

### List All Loans
```http
GET /api/loans
```

**Response:**
```json
[
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
]
```

### Create Loan
```http
POST /api/loans
Content-Type: application/json

{
  "loanName": "Home Loan",
  "loanType": "Home",
  "principalAmount": 2000000,
  "interestRate": 6.5,
  "tenureInMonths": 240,
  "loanStartDate": "2020-01-01"
}
```

### Get Loans by Type
```http
GET /api/loans/type/{type}
```

### Get Active Loans
```http
GET /api/loans/active
```

### Get Loan Summary
```http
GET /api/loans/summary
```

**Response:**
```json
{
  "totalPrincipal": 3000000,
  "totalRemaining": 2000000,
  "totalMonthlyEmi": 20000
}
```

---

## 💳 Credit Cards Endpoints

### List All Credit Cards
```http
GET /api/credit-cards
```

**Response:**
```json
[
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
]
```

### Create Credit Card
```http
POST /api/credit-cards
Content-Type: application/json

{
  "cardName": "Premium Card",
  "cardIssuer": "HDFC",
  "last4Digits": "1234",
  "creditLimit": 500000,
  "dueDate": "2026-08-15"
}
```

### Get Active Credit Cards
```http
GET /api/credit-cards/active
```

### Get Cards by Issuer
```http
GET /api/credit-cards/issuer/{issuer}
```

### Get Credit Card Summary
```http
GET /api/credit-cards/summary
```

**Response:**
```json
{
  "totalCreditLimit": 1500000,
  "totalBalance": 350000,
  "totalAvailableCredit": 1150000
}
```

---

## 🤖 AI Assistant Endpoints

### Ask Financial Question
```http
POST /api/ai/ask
Content-Type: application/json

{
  "question": "How much did I spend this month?"
}
```

**Response:**
```json
{
  "question": "How much did I spend this month?",
  "answer": "Based on your financial data, your total expenses are ₹45,000. This includes categories like food, transportation, utilities, and entertainment."
}
```

### Example Questions
- "How much did I spend?"
- "What is my total income?"
- "How are my investments performing?"
- "What is my net worth?"
- "How much do I owe on my loans?"
- "What are my financial goals?"

**Note:** Requires Ollama to be running locally

---

## Error Responses

### 404 - Not Found
```json
{
  "error": "Resource not found"
}
```

### 400 - Bad Request
```json
{
  "error": "Invalid request data"
}
```

### 500 - Server Error
```json
{
  "error": "Internal server error"
}
```

---

## Common Expense Categories
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

## Common Income Sources
- Salary
- Bonus
- Interest
- Dividend
- Other Income

## Financial Goal Types
- Farm
- House
- Marriage
- Emergency Fund
- Retirement

---

## Testing with curl

### Add Income (PowerShell)
```powershell
curl -X POST http://localhost:8080/api/income `
  -H "Content-Type: application/json" `
  -d '{
    "source":"Salary",
    "amount":70000,
    "date":"2026-07-01",
    "notes":"July salary"
  }'
```

### View Dashboard
```powershell
curl http://localhost:8080/api/dashboard
```

### Ask AI
```powershell
curl -X POST http://localhost:8080/api/ai/ask `
  -H "Content-Type: application/json" `
  -d '{
    "question":"How much did I spend?"
  }'
```

---

## Rate Limiting
None implemented in Phase 1 — add in Phase 2

## Authentication
None implemented in Phase 1 — add in Phase 2

## CORS
Enabled for local development — configure in Phase 2


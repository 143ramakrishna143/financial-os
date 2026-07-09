Migration Guide - legacy income/expense → transactions

Overview

A migration utility is provided to copy existing `income` and `expense` rows into the new `transactions` table. The approach preserves legacy tables for safety and ease of verification.

Steps

1. Backup database file:

```powershell
cp financial.db financial.db.v0.1.backup
```

2. Start the application (so Spring context and repositories are available).

3. Trigger the migration endpoint (admin route):

```powershell
curl -X POST http://localhost:8080/api/admin/migrate-legacy
```

Response:

```
Migrated incomes: <count>, expenses: <count>, to default account id: <id>
```

What migration does

- Creates a `Default Account` (name "Default Account") if it does not exist.
- For each `income` row, creates a `Transaction` with:
  - `type=INCOME`
  - `category` = income.source
  - `toAccount` = Default Account
  - `amount` = income.amount
  - `occurredAt` = income.date at midnight
  - `notes` = "Legacy Income ID: <id>"

- For each `expense` row, creates a `Transaction` with:
  - `type=EXPENSE`
  - `category` = expense.category
  - `fromAccount` = Default Account
  - `amount` = expense.amount
  - `occurredAt` = expense.date at midnight
  - `notes` = "Legacy Expense ID: <id>"

Notes

- Legacy tables (`income`, `expense`) are preserved and not deleted. The Finance Engine will preferentially read `transactions` and fall back to `income`/`expense` if no transactions are present for a period.
- After validating the migrated data, legacy tables can be removed in a later release.

Verification

- Check the `transactions` table using SQLite Browser to ensure migrated rows exist.
- Verify account balance changed for the Default Account by calling:

```powershell
curl http://localhost:8080/api/accounts/total-balance
```

- Run finance endpoints for cash flow and net worth:

```powershell
curl http://localhost:8080/api/v2/finance/net-worth
curl "http://localhost:8080/api/v2/finance/cash-flow?start=2026-07-01T00:00:00&end=2026-07-31T23:59:59"
```

Rollback

- If any issue, stop the application and restore the backup:

```powershell
cp financial.db.v0.1.backup financial.db
```



# Financial OS — Phase 1

A local, offline Spring Boot app storing your income/expenses in SQLite, with a dashboard summary endpoint.

## What's included (Phase 1 scope)
- SQLite database (auto-created as `financial.db` in the project folder)
- `Income` and `Expense` entities with full CRUD REST APIs
- `/api/dashboard` endpoint: total income, total expense, surplus, net worth (simplified)

## How to run (Windows + IntelliJ)

1. Unzip `financial-os.zip` somewhere like `C:\Projects\financial-os`.
2. Open IntelliJ IDEA.
3. **File → Open** → select the `financial-os` folder (the one containing `pom.xml`).
4. IntelliJ will detect it's a Maven project and prompt to load it — click **Load Maven Project** (or it happens automatically). This downloads Spring Boot, SQLite driver, etc. from Maven Central — needs internet, takes a minute or two the first time.
5. Once indexing/downloading finishes, open `FinancialOsApplication.java`
   (`src/main/java/com/financialos/FinancialOsApplication.java`).
6. Click the green ▶ Run button next to the `main` method, or right-click the file → **Run 'FinancialOsApplication'**.
7. You should see console output ending with:
   ```
   Financial OS is running!
   Try: http://localhost:8080/api/dashboard
   ```

A file called `financial.db` will appear in your project root — that's your entire database, a single portable file.

## Test it (no UI yet — this is backend-only for Phase 1)

Use your browser, Postman, or `curl` (from PowerShell/cmd):

**Add income:**
```bash
curl -X POST http://localhost:8080/api/income ^
  -H "Content-Type: application/json" ^
  -d "{\"source\":\"Salary\",\"amount\":70000,\"date\":\"2026-07-01\",\"notes\":\"July salary\"}"
```

**Add expense:**
```bash
curl -X POST http://localhost:8080/api/expense ^
  -H "Content-Type: application/json" ^
  -d "{\"category\":\"Food\",\"amount\":5000,\"date\":\"2026-07-02\",\"notes\":\"Groceries\"}"
```

**View dashboard:**
```
http://localhost:8080/api/dashboard
```
(just open this URL in your browser — GET requests work directly)

**View all income / expenses:**
```
http://localhost:8080/api/income
http://localhost:8080/api/expense
```

## Troubleshooting

- **"Cannot resolve symbol" errors in IntelliJ**: Maven hasn't finished downloading dependencies yet. Right-click `pom.xml` → **Maven → Reload Project**.
- **Port 8080 already in use**: change `server.port=8080` in `application.properties` to e.g. `8081`.
- **No internet on first build**: Maven needs to download Spring Boot jars once. After that, it's cached locally and works offline.

## Next steps (Phase 2, once this works)
- Add Mutual Fund and Stock entities + endpoints
- Add a Goal tracker entity
- Wire net worth to include MF/stocks, not just cash surplus
- Start on a simple JavaFX or React dashboard UI instead of raw JSON

Let me know once you've got this running and we'll move to Phase 2.

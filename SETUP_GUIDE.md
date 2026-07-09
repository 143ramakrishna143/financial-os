# Financial OS - Setup Guide

Complete setup instructions for Financial OS Phase 1.

## Prerequisites

### System Requirements
- **OS**: Windows 10/11 or Mac/Linux
- **Java**: JDK 21 or higher
- **RAM**: 2GB minimum (4GB recommended)
- **Storage**: 500MB free space
- **Internet**: Required for first Maven build only

### Software Installation

#### 1. Install Java 21 (if not already installed)

**Windows:**
1. Download JDK 21 from: https://www.oracle.com/java/technologies/downloads/#java21
2. Run the installer
3. Follow the installation steps
4. Open PowerShell and verify:
   ```powershell
   java -version
   ```
   Should show: `java version "21.x.x"`

#### 2. Install IntelliJ IDEA

1. Download from: https://www.jetbrains.com/idea/download/
2. Choose "Community Edition" (free) or "Ultimate" (paid)
3. Run the installer
4. During setup, check:
   - ✅ Add IntelliJ IDEA to PATH
   - ✅ Associate with .java files

#### 3. Install Maven (Optional - IntelliJ has built-in Maven)

**Windows:**
1. Download from: https://maven.apache.org/download.cgi
2. Extract to `C:\Program Files\Apache\maven`
3. Add to System Environment Variables:
   - Variable: `MAVEN_HOME`
   - Value: `C:\Program Files\Apache\maven`
4. Add to PATH: `%MAVEN_HOME%\bin`
5. Verify in PowerShell:
   ```powershell
   mvn -version
   ```

#### 4. Install Ollama (For AI Features)

**Windows:**
1. Download from: https://ollama.ai
2. Run installer and follow setup
3. Run Ollama:
   ```powershell
   ollama serve
   ```
4. In another PowerShell window, pull Qwen 8B model:
   ```powershell
   ollama pull qwen:8b
   ```

This downloads the ~6GB model file (one-time only).

---

## Project Setup in IntelliJ

### Step 1: Clone/Extract Project

1. Clone or extract financial-os to a location like:
   ```
   C:\Projects\financial-os
   ```

2. Ensure the folder contains:
   - `pom.xml`
   - `src/` folder
   - `README.md`

### Step 2: Open Project in IntelliJ

1. Open IntelliJ IDEA
2. **File → Open...**
3. Navigate to your `financial-os` folder
4. Click **Open**
5. IntelliJ may ask: "Trust this project?" → Click **Trust Project**

### Step 3: Configure Project SDK

1. **File → Project Structure** (or **Ctrl+Alt+Shift+S**)
2. **Project → SDK → Edit**
3. Click **+** and select **JDK**
4. Choose your JDK 21 installation
5. Click **OK**

### Step 4: Load Maven Dependencies

1. Right-click `pom.xml` in the file tree
2. **Maven → Reload Project**
3. Wait for downloads to complete (1-2 minutes first time)
4. Check bottom status bar for "Build successful"

If you see yellow warning icons:
- Right-click on the warning
- Click the "Quick Fix" suggestion

### Step 5: Index Project

Let IntelliJ finish indexing:
1. Watch bottom-right corner for "Indexing..."
2. Wait until it completes
3. All red errors should resolve

---

## Running the Application

### Method 1: Run from IntelliJ (Recommended)

1. Open `src/main/java/com/financialos/FinancialOsApplication.java`
2. Click the green ▶ **Run** button next to `main()` method
3. OR right-click file → **Run 'FinancialOsApplication'**
4. Watch console for:
   ```
   =================================================
    Financial OS is running!
    Try: http://localhost:8080/api/dashboard
   =================================================
   ```

### Method 2: Run from Terminal

```powershell
cd C:\Projects\financial-os
mvn clean install
mvn spring-boot:run
```

### Method 3: Run Built JAR

```powershell
cd C:\Projects\financial-os
mvn clean package
java -jar target/financial-os-1.0.0.jar
```

---

## Verify Installation

### 1. Check Application is Running

Open in browser:
```
http://localhost:8080/api/dashboard
```

Should return JSON with financial data.

### 2. Test with curl

Open PowerShell:

```powershell
# Get all income
curl http://localhost:8080/api/income

# Get all expenses
curl http://localhost:8080/api/expense

# Get dashboard
curl http://localhost:8080/api/dashboard
```

### 3. Add Test Data

```powershell
# Add income
curl -X POST http://localhost:8080/api/income `
  -H "Content-Type: application/json" `
  -d '{
    "source":"Salary",
    "amount":50000,
    "date":"2026-07-09",
    "notes":"Test salary"
  }'

# Add expense
curl -X POST http://localhost:8080/api/expense `
  -H "Content-Type: application/json" `
  -d '{
    "category":"Food",
    "amount":1000,
    "date":"2026-07-09",
    "notes":"Test expense"
  }'
```

### 4. View Database

Database file: `financial.db` in project root

To view with SQLite:
1. Download SQLite Browser: https://sqlitebrowser.org/
2. Open `financial.db`
3. Browse tables created by the app

---

## Configuration

### Server Port

Edit `src/main/resources/application.properties`:

```properties
# Default: 8080
server.port=8080

# Change to:
server.port=8081
```

Then restart application.

### Database Location

```properties
# Default: project root
spring.datasource.url=jdbc:sqlite:financial.db

# Custom path:
spring.datasource.url=jdbc:sqlite:/path/to/my/database.db
```

### Ollama Configuration

```properties
# Ollama server (if running on different machine)
ollama.baseUrl=http://localhost:11434

# Model to use
ollama.model=qwen:8b

# Timeout in seconds
ollama.timeout=300
```

---

## Troubleshooting

### Issue: "Cannot resolve symbol" errors

**Solution:**
1. Right-click `pom.xml`
2. **Maven → Reload Project**
3. Wait for indexing
4. If still red, right-click red icon → Quick Fix

### Issue: Port 8080 already in use

**Solution:**
1. Edit `application.properties`
2. Change `server.port=8080` to `8081` or another port
3. Restart application

### Issue: Maven won't download dependencies

**Solution:**
1. Ensure you have internet connection
2. Right-click `pom.xml`
3. **Maven → Update Project (Force Update)**
4. Wait for download to complete

### Issue: Java version mismatch

**Check Java version:**
```powershell
java -version
```

Should show 21.x.x

**If wrong version:**
1. Install JDK 21
2. **File → Project Structure → SDK → Edit**
3. Select correct JDK 21
4. Click OK and rebuild

### Issue: Ollama "connection refused"

**Solution:**
1. Ensure Ollama is running:
   ```powershell
   ollama serve
   ```
2. Check if Qwen model is installed:
   ```powershell
   ollama list
   ```
3. If not installed:
   ```powershell
   ollama pull qwen:8b
   ```

### Issue: Slow first build

**Why:** Maven downloads all dependencies (~300MB)

**Solution:**
1. First build: Takes 2-3 minutes
2. Subsequent builds: Fast (cached)
3. Ensure stable internet connection

### Issue: "financial.db not found"

**This is normal!** Database file is auto-created on first run.

Check:
1. Run application
2. Make a request to `/api/dashboard`
3. Look in project root for `financial.db`
4. Should appear after first request

### Issue: Cannot access API endpoints

**Check:**
1. Is application running? (Check console)
2. Are you on correct port? (Default: 8080)
3. Try: `http://localhost:8080/api/dashboard`
4. Check firewall (Windows may block Java)

**Allow Java through Windows Firewall:**
1. Windows Security → Firewall & Network Protection
2. Allow an app through firewall
3. Find Java and check both Private/Public

---

## IDE Shortcuts (IntelliJ)

| Action | Shortcut |
|--------|----------|
| Run | **Ctrl+Shift+F10** or green ▶ button |
| Debug | **Shift+F9** |
| Stop | **Ctrl+F2** |
| Format Code | **Ctrl+Alt+L** |
| Find/Replace | **Ctrl+H** |
| Go to Class | **Ctrl+N** |
| Go to File | **Ctrl+Shift+N** |
| Rename | **Shift+F6** |
| Build | **Ctrl+F9** |
| Maven Reload | Right-click pom.xml → Maven → Reload Project |

---

## Next Steps

1. ✅ Application running
2. ✅ Database created
3. ✅ APIs working
4. Next: Create test data with `/api/income` and `/api/expense`
5. Next: Explore endpoints with Postman
6. Next: Set up Ollama for AI features
7. Next: Begin Phase 2 (JavaFX UI)

---

## Getting Help

### Check Logs

**In IDE Console:**
- Scroll to see error messages
- Search for "ERROR" or "Exception"

**From Terminal:**
```powershell
mvn spring-boot:run 2>&1 | Tee-Object -FilePath app.log
```

### Common Error Messages

| Error | Cause | Solution |
|-------|-------|----------|
| `Port already in use` | 8080 in use | Change server.port |
| `Cannot resolve symbol` | Maven not loaded | Reload Maven project |
| `ClassNotFoundException` | JAR missing | Maven reload + clean |
| `Connection refused` | App not running | Run application |
| `NullPointerException` | Database not initialized | Make a GET request first |

---

## Performance Tips

1. **Close unnecessary IntelliJ plugins** for faster IDE
2. **Allocate more RAM** to IntelliJ:
   - Help → Change Memory Settings
   - Set to 2048MB or higher
3. **Use SSD** for project directory (faster build)
4. **Upgrade to Ollama GPU mode** for faster AI:
   - Requires NVIDIA CUDA or AMD ROCm
   - Significantly speeds up LLM inference

---

## Uninstallation

To completely remove Financial OS:

1. Close IntelliJ
2. Delete the entire `financial-os` folder
3. Delete the `financial.db` file if in project root
4. (Optional) Uninstall JDK/Maven/Ollama if not needed

IntelliJ stores project settings in:
- Windows: `C:\Users\YourUsername\.IntelliJIdea*`
- Mac: `~/.IntelliJIdea*`
- Linux: `~/.IntelliJIdea*`

---

Ready to go! 🚀 Start building your financial OS!


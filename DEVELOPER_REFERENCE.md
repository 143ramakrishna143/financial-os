# Financial OS - Developer Reference

Quick reference guide for developers working on Financial OS.

## Project Architecture

### Layered Architecture

```
┌─────────────────────────────────────┐
│      REST Controller Layer          │  HTTP Endpoints
├─────────────────────────────────────┤
│      Service Layer                  │  Business Logic
├─────────────────────────────────────┤
│      Repository Layer (JPA)         │  Data Access
├─────────────────────────────────────┤
│      Entity/Model Layer             │  Database Entities
├─────────────────────────────────────┤
│      SQLite Database                │  Data Persistence
└─────────────────────────────────────┘
```

### Key Technologies

- **Spring Boot 3.3.4** - Application framework
- **Spring Data JPA** - ORM abstraction
- **Hibernate** - JPA implementation
- **SQLite JDBC** - Database driver
- **RestTemplate** - HTTP client

---

## File Organization

### Controller Layer
Location: `src/main/java/com/financialos/controller/`

```java
@RestController
@RequestMapping("/api/resource")
public class ResourceController {
    private final ResourceService service;
    
    @GetMapping
    public ResponseEntity<List<Resource>> getAll() { }
    
    @PostMapping
    public ResponseEntity<Resource> create(@RequestBody Resource entity) { }
    
    @PutMapping("/{id}")
    public ResponseEntity<Resource> update(@PathVariable Long id, @RequestBody Resource entity) { }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) { }
}
```

### Service Layer
Location: `src/main/java/com/financialos/service/`

```java
@Service
public class ResourceService {
    private final ResourceRepository repository;
    
    public ResourceService(ResourceRepository repository) {
        this.repository = repository;
    }
    
    public Resource save(Resource resource) {
        // Business logic here
        return repository.save(resource);
    }
    
    public List<Resource> getAll() {
        return repository.findAll();
    }
}
```

### Repository Layer
Location: `src/main/java/com/financialos/repository/`

```java
@Repository
public interface ResourceRepository extends JpaRepository<Resource, Long> {
    List<Resource> findByName(String name);
    Optional<Resource> findById(Long id);
    // Add custom query methods as needed
}
```

### Model/Entity Layer
Location: `src/main/java/com/financialos/model/`

```java
@Entity
@Table(name = "resources")
public class Resource {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String name;
    
    private Double value;
    
    private LocalDate date;
    
    // Constructors, getters, setters
}
```

---

## Adding a New Feature

### Step 1: Create Entity
`Model/FeatureName.java`

```java
@Entity
@Table(name = "feature_name")
public class FeatureName {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String field1;
    private Double field2;
    private LocalDate field3;
    
    // Getters, setters, constructors
}
```

### Step 2: Create Repository
`Repository/FeatureNameRepository.java`

```java
@Repository
public interface FeatureNameRepository extends JpaRepository<FeatureName, Long> {
    List<FeatureName> findByField1(String field1);
}
```

### Step 3: Create Service
`Service/FeatureNameService.java`

```java
@Service
public class FeatureNameService {
    private final FeatureNameRepository repository;
    
    public FeatureNameService(FeatureNameRepository repository) {
        this.repository = repository;
    }
    
    public List<FeatureName> getAll() {
        return repository.findAll();
    }
    
    public FeatureName save(FeatureName entity) {
        return repository.save(entity);
    }
}
```

### Step 4: Create Controller
`Controller/FeatureNameController.java`

```java
@RestController
@RequestMapping("/api/feature-name")
public class FeatureNameController {
    private final FeatureNameService service;
    
    public FeatureNameController(FeatureNameService service) {
        this.service = service;
    }
    
    @GetMapping
    public ResponseEntity<List<FeatureName>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }
    
    @PostMapping
    public ResponseEntity<FeatureName> create(@RequestBody FeatureName entity) {
        FeatureName created = service.save(entity);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }
}
```

### Step 5: Test

Use curl or Postman:
```bash
curl -X POST http://localhost:8080/api/feature-name \
  -H "Content-Type: application/json" \
  -d '{"field1":"value","field2":100.0,"field3":"2026-07-09"}'
```

---

## Common Patterns

### Custom Query
```java
// In Repository
@Query("SELECT e FROM Entity e WHERE e.field = :value")
List<Entity> customFind(@Param("value") String value);

// In Service
public List<Entity> findByCustom(String value) {
    return repository.customFind(value);
}
```

### Aggregation (Sum)
```java
public Double getTotalAmount() {
    return repository.findAll().stream()
            .mapToDouble(Entity::getAmount)
            .sum();
}
```

### Filtering
```java
public List<Entity> getByStatus(String status) {
    return repository.findAll().stream()
            .filter(e -> status.equals(e.getStatus()))
            .collect(Collectors.toList());
}
```

### Date Range
```java
public List<Entity> getByDateRange(LocalDate start, LocalDate end) {
    return repository.findByDateBetween(start, end);
}
```

---

## Dependency Injection

All services use constructor injection:

```java
@Service
public class MyService {
    private final DependencyA depA;
    private final DependencyB depB;
    
    // Constructor injection - required for all dependencies
    public MyService(DependencyA depA, DependencyB depB) {
        this.depA = depA;
        this.depB = depB;
    }
}
```

---

## Error Handling

### Return ResponseEntity with Proper Status

```java
// Success
return ResponseEntity.ok(entity);
return ResponseEntity.status(HttpStatus.CREATED).body(entity);

// Not found
return ResponseEntity.notFound().build();

// Server error
return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
    .body(new ErrorResponse("Error message"));

// Bad request
return ResponseEntity.badRequest()
    .body(new ErrorResponse("Invalid data"));
```

---

## Testing Endpoints

### Using curl (Windows PowerShell)

```powershell
# GET all
curl http://localhost:8080/api/resource

# POST new
curl -X POST http://localhost:8080/api/resource `
  -H "Content-Type: application/json" `
  -d '{"field":"value"}'

# PUT update
curl -X PUT http://localhost:8080/api/resource/1 `
  -H "Content-Type: application/json" `
  -d '{"field":"updated"}'

# DELETE
curl -X DELETE http://localhost:8080/api/resource/1
```

### Using Postman

1. New → Request
2. Set method (GET, POST, etc.)
3. Enter URL: `http://localhost:8080/api/resource`
4. For POST/PUT: Body → raw → JSON
5. Send

---

## Debugging

### Enable SQL Logging

In `application.properties`:
```properties
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
spring.jpa.properties.hibernate.use_sql_comments=true
```

### Debug in IDE

1. Set breakpoint (click line number)
2. Click **Debug** button (not Run)
3. Code pauses at breakpoint
4. Inspect variables in Debug panel
5. Step through with F10/F11

### View HTTP Requests

```properties
logging.level.org.springframework.web=DEBUG
logging.level.org.hibernate.SQL=DEBUG
```

---

## Maven Commands

```bash
# Install dependencies
mvn clean install

# Run application
mvn spring-boot:run

# Build JAR
mvn clean package

# Skip tests
mvn clean package -DskipTests

# Compile only
mvn clean compile

# Run tests
mvn test

# Check for dependency updates
mvn versions:display-dependency-updates

# Clean build artifacts
mvn clean
```

---

## Git Workflow (Team)

```bash
# Clone repo
git clone <repo-url>
cd financial-os

# Create feature branch
git checkout -b feature/new-feature

# Make changes and commit
git add .
git commit -m "Add new feature"

# Push to remote
git push origin feature/new-feature

# Create pull request on GitHub
# After review and approval, merge to main

# Update local main
git checkout main
git pull origin main
```

### Commit Message Convention
```
feature: Add new feature description
fix: Fix bug description
refactor: Refactor component description
docs: Update documentation
test: Add test for feature
chore: Update dependencies
```

---

## Database Migration (Phase 2)

For future database migrations, add Flyway:

```xml
<dependency>
    <groupId>org.flywaydb</groupId>
    <artifactId>flyway-core</artifactId>
</dependency>
```

Create migration scripts in `src/main/resources/db/migration/`:
- `V1__Initial_schema.sql`
- `V2__Add_new_table.sql`
- `V3__Add_column.sql`

---

## Performance Optimization Tips

1. **Use pagination for large datasets**
   ```java
   Page<Entity> findAll(Pageable pageable);
   ```

2. **Add database indexes**
   ```java
   @Column(name = "email")
   @Index(name = "idx_email")
   private String email;
   ```

3. **Lazy load collections**
   ```java
   @OneToMany(fetch = FetchType.LAZY)
   private List<Child> children;
   ```

4. **Use caching**
   ```java
   @Cacheable("myCache")
   public List<Entity> getAll() { }
   ```

5. **Batch operations**
   ```java
   @Transactional
   public void batchSave(List<Entity> entities) {
       entities.forEach(repository::save);
       repository.flush();
   }
   ```

---

## Code Style Guide

### Naming Conventions

```java
// Classes - PascalCase
public class ExpenseController { }

// Methods and variables - camelCase
public void addExpense() { }
private Double totalAmount;

// Constants - UPPER_SNAKE_CASE
public static final Double TAX_RATE = 0.18;

// Test methods - descriptive
@Test
public void shouldReturnAllExpenses() { }
```

### Code Structure

```java
@RestController
@RequestMapping("/api/resource")
public class ResourceController {
    
    // 1. Fields
    private final ResourceService service;
    
    // 2. Constructor
    public ResourceController(ResourceService service) {
        this.service = service;
    }
    
    // 3. GET endpoints
    @GetMapping
    public List<Resource> getAll() { }
    
    // 4. POST endpoints
    @PostMapping
    public Resource create(@RequestBody Resource entity) { }
    
    // 5. PUT endpoints
    @PutMapping("/{id}")
    public Resource update(@PathVariable Long id, @RequestBody Resource entity) { }
    
    // 6. DELETE endpoints
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) { }
}
```

---

## IDE Shortcuts (IntelliJ)

| Action | Shortcut |
|--------|----------|
| Run | Ctrl+Shift+F10 |
| Debug | Shift+F9 |
| Reformat Code | Ctrl+Alt+L |
| Generate (Getters/Setters) | Alt+Insert |
| Find Usages | Ctrl+Alt+F7 |
| Rename | Shift+F6 |
| Extract Method | Ctrl+Alt+M |
| Comment | Ctrl+/ |

---

## Phase 2 Roadmap (Developer)

- [ ] Add user authentication (Spring Security)
- [ ] Add JWT tokens
- [ ] Implement role-based access control
- [ ] Add request validation (@Valid)
- [ ] Add comprehensive error handling
- [ ] Add unit tests (JUnit 5)
- [ ] Add integration tests
- [ ] Add Swagger/OpenAPI documentation
- [ ] Implement API versioning (/api/v1/)
- [ ] Add rate limiting
- [ ] Add request/response logging
- [ ] Optimize database queries
- [ ] Add caching layer (Redis)
- [ ] Add background jobs (Quartz)
- [ ] Dockerize application

---

## Useful Resources

- [Spring Boot Documentation](https://spring.io/projects/spring-boot)
- [Spring Data JPA Reference](https://spring.io/projects/spring-data-jpa)
- [Hibernate ORM Guide](https://hibernate.org/orm/)
- [REST API Best Practices](https://restfulapi.net/)
- [SQLite Documentation](https://www.sqlite.org/docs.html)
- [Java Collections Framework](https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/util/package-summary.html)

---

## Troubleshooting

### Changes not reflecting
1. Rebuild: **Ctrl+F9**
2. Restart application
3. Clear cache: **File → Invalidate Caches**

### Entity not being persisted
1. Check `@Entity` annotation
2. Check `@Table` name matches database
3. Enable SQL logging to see queries
4. Check transaction boundaries

### Repository method not found
1. Ensure interface extends `JpaRepository<Entity, ID>`
2. Check method naming (Spring Data conventions)
3. Rebuild project if using generated methods

### Port conflicts
1. Find what's using port 8080: `netstat -ano | findstr :8080`
2. Kill process: `taskkill /PID <PID> /F`
3. Or change port in `application.properties`

---

## Questions?

Refer to the main README.md or consult the team lead!



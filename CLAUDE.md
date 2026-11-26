# Backend — Spring Boot

## Structure

```
foodordering-app-backend/
├── pom.xml                              # Parent POM (multi-module Maven)
├── FoodOrderingApp-api/                 # REST controllers, Swagger, application.yaml
│   └── src/main/java/.../api/controller/
├── FoodOrderingApp-service/             # Business logic, entities, DAOs
│   └── src/main/java/.../service/
│       ├── business/                    # Service classes
│       ├── dao/                         # Data access (JPA)
│       ├── entity/                      # Hibernate entities
│       └── common/                      # Utilities, error codes, constants
└── FoodOrderingApp-db/                  # DB scripts
    └── src/main/resources/sql/          # tables.sql, V2__google_sso.sql, ...
```

## Tech Stack

| Component | Version |
|-----------|---------|
| Spring Boot | 3.3.6 |
| Java (Temurin) | 17 |
| Maven | global `mvn` (no wrapper) |
| PostgreSQL | 17 |
| Hibernate / Jakarta EE | 6 |
| SpringDoc OpenAPI | 2.3.0 |

## Database

- **Host:** localhost **Port:** 5434 (not default 5432)
- **Database:** restaurantdb
- **Credentials:** see `.env.example` (never hardcode)
- **JAVA_HOME:** `/Library/Java/JavaVirtualMachines/temurin-17.jdk/Contents/Home`

## Auth System

Custom hand-rolled auth — **do NOT add spring-boot-starter-security:**
- `JwtTokenProvider` generates JWT tokens (8-hour expiry)
- Tokens stored in `customer_auth` table
- Login: Basic auth → Bearer `access-token` in response header
- Protected endpoints: Bearer token in `Authorization` header
- Google SSO: POST /api/customer/google-login with Google ID token
- Roles: `role` column on `customer` table (CUSTOMER, RESTAURANT_OWNER, DRIVER, ADMIN)
- `RoleGuard` utility enforces role checks in controllers

## SQL Migrations

Migrations are numbered and **immutable** — never modify an existing one:
`V3__rbac.sql`, `V4__email_unique.sql`, ... `V10__customer_favorites.sql`.

Every new migration must also be appended to `../db-init/01-schema.sh` so
Docker fresh builds include it.

## Build & Run

```bash
export JAVA_HOME=/Library/Java/JavaVirtualMachines/temurin-17.jdk/Contents/Home

# Build
mvn clean install -DskipTests

# Run
java -jar FoodOrderingApp-api/target/FoodOrderingApp-api-1.0-SNAPSHOT.jar

# Or use the restart script
./restart.sh
```

API at `http://localhost:8080/api/` · Swagger at `http://localhost:8080/api/swagger-ui.html`

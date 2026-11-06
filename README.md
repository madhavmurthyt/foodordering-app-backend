# Food Ordering App — Backend

A Spring Boot REST API for the Food Ordering Application, providing endpoints for customer management, restaurant browsing, ordering, and payments.

## Prerequisites

- **Java 17** (Temurin or similar)
- **Maven 3.8+**
- **PostgreSQL** (running on port 5434)

## Database Setup

1. Start PostgreSQL on port 5434:
   ```bash
   pg_ctl -D /opt/homebrew/var/postgresql@17 -o "-p 5434" start
   ```

2. Create the database:
   ```bash
   createdb -p 5434 restaurantdb
   ```

3. Ensure the `postgres` role exists with the correct password:
   ```bash
   psql -p 5434 -c "ALTER ROLE postgres WITH PASSWORD 'password'"
   ```

4. Run the schema and seed data:
   ```bash
   psql -p 5434 -U postgres -d restaurantdb -f FoodOrderingApp-db/src/main/resources/sql/tables.sql
   psql -p 5434 -U postgres -d restaurantdb -f FoodOrderingApp-db/src/main/resources/sql/insert.sql
   ```

   This creates 14 tables and populates them with 8 restaurants, 102 menu items, 7 categories, payment methods, coupons, states, and a test customer.

### Database Configuration

If your PostgreSQL runs on a different port or uses different credentials, update both files:

- `FoodOrderingApp-api/src/main/resources/application.yaml` — datasource URL, username, password
- `FoodOrderingApp-db/src/main/resources/config/localhost.properties` — server.port, database credentials

## Building

Set `JAVA_HOME` to JDK 17 if you have multiple JDKs:
```bash
export JAVA_HOME=/Library/Java/JavaVirtualMachines/temurin-17.jdk/Contents/Home
```

Build all modules:
```bash
mvn clean install -DskipTests
```

## Running

Start the API server:
```bash
cd FoodOrderingApp-api
mvn spring-boot:run
```

The API will be available at `http://localhost:8080/api/`.

Swagger UI is available at `http://localhost:8080/api/swagger-ui.html`.

## Verifying the Server

```bash
curl http://localhost:8080/api/restaurant
```

Should return a JSON response with 8 restaurants.

## Project Structure

```
foodordering-app-backend/
├── FoodOrderingApp-api/       REST controllers, Swagger config, generated models
├── FoodOrderingApp-service/   Business logic, DAOs, JPA entities, exceptions
└── FoodOrderingApp-db/        SQL schema and seed data
```

## API Endpoints

| Area | Base Path | Key Operations |
|---|---|---|
| Customer | `/customer` | signup, login, logout, update profile, change password |
| Restaurant | `/restaurant` | list all, search by name, filter by category, get details, rate |
| Order | `/order` | place order, get past orders, get coupon |
| Address | `/address` | save, list, delete addresses; list states |
| Category | `/category` | list all, get by ID with items |
| Payment | `/payment` | list payment methods |
| Item | `/item` | top 5 popular items per restaurant |

All protected endpoints require a `Bearer <access-token>` header obtained from the login endpoint.

## Test Customer

A pre-seeded customer is available for testing:
- **Contact**: 1111111111
- **Password**: The password is hashed in the database. Register a new customer via the signup endpoint for testing.

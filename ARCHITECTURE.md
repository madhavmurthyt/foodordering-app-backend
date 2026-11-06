# Food Ordering App — Backend

## Tech Stack

| Layer | Technology | Version |
|---|---|---|
| Framework | Spring Boot | 2.3.2.RELEASE |
| Language | Java | 8+ (source), currently JDK 17 on this machine |
| Build | Maven (multi-module POM) | Not installed locally — needs install |
| Database | PostgreSQL | 17 (Homebrew) |
| ORM | Spring Data JPA / Hibernate | (managed by Spring Boot parent) |
| Auth | Custom JWT (java-jwt 3.10.3) + password hashing (SHA-256 + salt) |
| API Docs | Swagger 2 (Springfox 2.9.2) + Swagger Codegen for model generation |
| Logging | Logback 1.2.3 |

## Module Structure

```
foodordering-app-backend/          (parent POM, packaging: pom)
├── FoodOrderingApp-api/           REST controllers, Swagger config, generated models
│   └── src/main/resources/
│       ├── application.yaml       Spring Boot config (port 8080, context /api)
│       └── endpoints/*.json       Swagger specs (customer, restaurant, order, address, category, item, payment)
├── FoodOrderingApp-service/       Business logic, DAOs, JPA entities, exceptions
└── FoodOrderingApp-db/            SQL schema setup + seed data (Maven sql-maven-plugin)
    └── src/main/resources/
        ├── config/localhost.properties   DB connection config (port 5434, db: restaurantdb)
        └── sql/
            ├── tables.sql         DDL for 14 tables
            └── insert.sql         Seed data (customers, restaurants, items, categories, etc.)
```

## Database Schema (14 tables)

| Table | Purpose |
|---|---|
| CUSTOMER | User accounts (firstname, lastname, email, contact_number, password, salt) |
| CUSTOMER_AUTH | JWT tokens, login/logout/expiry timestamps |
| STATE | Indian states list (36 entries) |
| ADDRESS | Flat, locality, city, pincode, state FK, active flag |
| CUSTOMER_ADDRESS | Many-to-many: customers ↔ addresses |
| RESTAURANT | Name, photo_url, rating, avg_price, address FK |
| CATEGORY | Category name (Italian, Indian, Snacks, Drinks, Sweet Dish, Chinese, Continental) |
| ITEM | Item name, price, type (0=VEG, 1=NON_VEG) |
| CATEGORY_ITEM | Many-to-many: categories ↔ items |
| RESTAURANT_ITEM | Many-to-many: restaurants ↔ items |
| RESTAURANT_CATEGORY | Many-to-many: restaurants ↔ categories |
| COUPON | Coupon name + discount percent (NEW30, BEST20, FLAT50, FLAT30) |
| PAYMENT | Payment modes (Cash on Delivery, Wallet, Net Banking, COD, Debit/Credit Card) |
| ORDERS | Order record: bill, discount, date, FKs to customer, address, restaurant, coupon, payment |
| ORDER_ITEM | Order line items: item FK, quantity, price |

## REST API Endpoints

### Customer (`/customer`)
| Method | Path | Auth | Description |
|---|---|---|---|
| POST | `/signup` | None | Register new customer |
| POST | `/login` | Basic (contact:password) | Login, returns JWT in `access-token` header |
| POST | `/logout` | Bearer JWT | Invalidate token |
| PUT | `/` | Bearer JWT | Update customer name |
| PUT | `/password` | Bearer JWT | Change password |

### Restaurant (`/restaurant`)
| Method | Path | Auth | Description |
|---|---|---|---|
| GET | `/` | None | List all restaurants sorted by rating |
| GET | `/name/{name}` | None | Search restaurants by name |
| GET | `/category/{category_id}` | None | Restaurants by category |
| GET | `/{restaurant_id}` | None | Restaurant details with categories and items |
| PUT | `/{restaurant_id}?customer_rating=X` | Bearer JWT | Update restaurant rating |

### Order (`/order`)
| Method | Path | Auth | Description |
|---|---|---|---|
| GET | `/` | Bearer JWT | Get customer's past orders |
| POST | `/` | Bearer JWT | Place new order |
| GET | `/coupon/{coupon_name}` | Bearer JWT | Get coupon details |

### Address
| Method | Path | Auth | Description |
|---|---|---|---|
| GET | `/address/customer` | Bearer JWT | Get customer's addresses |
| POST | `/address` | Bearer JWT | Save new address |
| DELETE | `/address/{address_id}` | Bearer JWT | Delete address |
| GET | `/states` | None | List all states |

### Category (`/category`)
| Method | Path | Auth | Description |
|---|---|---|---|
| GET | `/` | None | List all categories |
| GET | `/{category_id}` | None | Category details with items |

### Payment (`/payment`)
| Method | Path | Auth | Description |
|---|---|---|---|
| GET | `/` | None | List all payment methods |

### Item
| Method | Path | Auth | Description |
|---|---|---|---|
| GET | `/item/restaurant/{restaurant_id}` | None | Top 5 popular items for a restaurant |

## Application Configuration

- **Server port**: 8080
- **Context path**: `/api`
- **Database URL**: `jdbc:postgresql://localhost:5434/restaurantdb`
- **Database user**: `postgres` / `password`
- **Hibernate dialect**: PostgreSQL9Dialect

## Authentication Flow

1. Customer registers with contact number, email, name, password
2. Password is hashed with SHA-256 + random salt before storage
3. Login with Basic auth (`contact:password`) generates a JWT access token (8-hour expiry)
4. JWT returned in `access-token` response header
5. Subsequent requests send `Bearer <token>` in `authorization` header
6. Logout invalidates the token by setting `logout_at` timestamp

## Seed Data

- 1 pre-registered customer (contact: 1111111111)
- 36 Indian states
- 12 addresses (Mumbai, Bengaluru, Hyderabad)
- 8 restaurants with photos and ratings
- 102 menu items across 7 categories
- 4 discount coupons
- 5 payment methods

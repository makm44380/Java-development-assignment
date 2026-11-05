# Ark Investment API

A small Spring Boot REST API for managing investment funds, investors, and their transactions. It uses Spring Web, Spring Data JPA, Bean Validation, and an in-memory H2 database.

## Start the application

From this directory, run:

```bash
./mvnw spring-boot:run
```

The API is available at `http://localhost:8080`. Data is stored in memory and is reset when the application stops. The H2 console is at `http://localhost:8080/h2-console` with JDBC URL `jdbc:h2:mem:arkinvestment`, user `sa`, and a blank password.

## API endpoints

| Method | Path | Purpose |
| --- | --- | --- |
| `POST` | `/api/funds` | Create a fund |
| `GET` | `/api/funds` | List funds |
| `GET` | `/api/funds/{id}` | View a fund |
| `PUT` | `/api/funds/{id}` | Update a fund |
| `DELETE` | `/api/funds/{id}` | Delete a fund without transactions |
| `POST` | `/api/investors` | Create an investor |
| `GET` | `/api/investors` | List investors |
| `GET` | `/api/investors/{id}` | View an investor |
| `PUT` | `/api/investors/{id}` | Update an investor |
| `DELETE` | `/api/investors/{id}` | Delete an investor without transactions |
| `POST` | `/api/transactions` | Record an investor transaction in a fund |
| `GET` | `/api/transactions` | List transactions |
| `GET` | `/api/transactions/{id}` | View a transaction |
| `GET` | `/api/reports/funds` | Get fund balances and transaction counts |
| `GET` | `/api/reports/investors` | Get investor balances and transaction counts |

Create a transaction with JSON such as:

```json
{
  "investorId": 1,
  "fundId": 1,
  "transactionDate": "2026-01-15",
  "amount": 100.00,
  "type": "CONTRIBUTION"
}
```

Amounts must be positive. `CONTRIBUTION` and `INTEREST_INCOME` add to the report balance. `DISTRIBUTION`, `GENERAL_EXPENSE`, and `MANAGEMENT_FEE` subtract from it. Fund or investor records with transactions cannot be deleted, so the transaction history is preserved.

## Project layout

- `model` contains the JPA entities and transaction types.
- `repository` contains the Spring Data database interfaces.
- `service` contains CRUD, transaction, and report logic.
- `controller` exposes the REST endpoints.
- `api` contains transaction request and report response records.

Run the tests with `./mvnw test`.
# Java-development-assignment

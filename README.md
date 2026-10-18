<div align="center">

# Simple CRM — Backend

[![Java](https://img.shields.io/badge/Java-21-007396?logo=openjdk&logoColor=white)](https://openjdk.org/projects/jdk/21/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-4.1.1-6DB33F?logo=springboot&logoColor=white)](https://spring.io/projects/spring-boot)
[![PostgreSQL](https://img.shields.io/badge/PostgreSQL-16-4169E1?logo=postgresql&logoColor=white)](https://www.postgresql.org/)
[![Maven](https://img.shields.io/badge/Maven-Wrapper-C71A36?logo=apachemaven&logoColor=white)](https://maven.apache.org/)
[![Docker](https://img.shields.io/badge/Docker-ready-2496ED?logo=docker&logoColor=white)](https://www.docker.com/)
[![Railway](https://img.shields.io/badge/Deployed%20on-Railway-0B0D0E?logo=railway&logoColor=white)](https://simple-crm-backend-production.up.railway.app/customers)
[![Frontend](https://img.shields.io/badge/Frontend-GitHub%20Pages-181717?logo=github&logoColor=white)](https://ngys9919.github.io/simple-crm-frontend/)

**REST API for a lightweight customer relationship management (CRM) app — the backend half of a full-stack project.**

[Live App](https://ngys9919.github.io/simple-crm-frontend/) · [API Endpoint](https://simple-crm-backend-production.up.railway.app/customers) · [Frontend Repo](https://github.com/ngys9919/simple-crm-frontend) · [Report Bug](../../issues) · [Request Feature](../../issues)

</div>

---

## About

Simple CRM is a full-stack customer management application. This repository is the **backend**: a Spring Boot REST API that stores customer records in PostgreSQL and exposes full CRUD operations over JSON. The **frontend** lives in a separate repository, [ngys9919/simple-crm-frontend](https://github.com/ngys9919/simple-crm-frontend), and consumes this API from the browser.

| Part | Repository | Hosted at |
|------|-----------|-----------|
| Frontend (UI) | [simple-crm-frontend](https://github.com/ngys9919/simple-crm-frontend) | https://ngys9919.github.io/simple-crm-frontend/ |
| Backend (API) | this repo | https://simple-crm-backend-production.up.railway.app/customers |

### Key features

- Full CRUD for customers (create, list, fetch by ID, update, delete)
- Customer profile: name, email, phone, status, tags, company, notes, creation date
- Bean Validation on required fields (`firstName`, `lastName`) and email format
- Tags stored as a JPA element collection in a dedicated `customer_tags` table
- Layered architecture: controller → service → repository
- CORS restricted to the deployed frontend origin (`https://ngys9919.github.io`)
- Seed data loaded on startup via `DataLoader`
- Multi-stage Dockerfile and `docker-compose.yml` for one-command local runs
- Unit and web-layer tests running against in-memory H2

## Tech Stack

| Category | Technology |
|----------|-----------|
| Language | Java 21 |
| Framework | Spring Boot 4.1.1 (Spring Web MVC, Spring Data JPA, Bean Validation) |
| Database | PostgreSQL 16 (runtime), H2 (tests) |
| Build | Maven (wrapper included) |
| Utilities | Lombok, Spring Boot DevTools |
| Containers | Docker, Docker Compose |
| Backend hosting | Railway |
| Frontend hosting | GitHub Pages |

## Architecture

```
┌──────────────────────────────────────────────┐
│  Browser                                     │
│  Frontend UI — GitHub Pages                  │
│  ngys9919.github.io/simple-crm-frontend      │
└──────────────────────┬───────────────────────┘
                       │  HTTPS · JSON · CORS
                       ▼
┌──────────────────────────────────────────────┐
│  Spring Boot API — Railway                   │
│                                              │
│  CustomerController   /customers             │
│          │                                   │
│  CustomerServiceImpl  business logic         │
│          │                                   │
│  CustomerRepository   Spring Data JPA        │
└──────────────────────┬───────────────────────┘
                       │  JDBC
                       ▼
┌──────────────────────────────────────────────┐
│  PostgreSQL                                  │
│  simple_crm · customer_tags · interaction    │
└──────────────────────────────────────────────┘
```

## API Reference

Base URL: `https://simple-crm-backend-production.up.railway.app` (local: `http://localhost:8080`)

| Method | Endpoint | Description | Success | Errors |
|--------|----------|-------------|---------|--------|
| `GET` | `/customers` | List all customers | `200` | — |
| `GET` | `/customers/{id}` | Get one customer | `200` | `404` |
| `POST` | `/customers` | Create a customer | `201` | `400` |
| `PUT` | `/customers/{id}` | Update a customer | `200` | `404` |
| `DELETE` | `/customers/{id}` | Delete a customer | `204` | `404` |

### Customer JSON

```json
{
  "id": 1,
  "firstName": "Jane",
  "lastName": "Tan",
  "email": "jane.tan@example.com",
  "phone": "+65 9123 4567",
  "status": "active",
  "tags": ["vip", "newsletter"],
  "company": "Acme Pte Ltd",
  "notes": "Prefers email contact.",
  "createdAt": "2026-10-18"
}
```

Try it:

```bash
curl https://simple-crm-backend-production.up.railway.app/customers
```

> **Note:** CORS only allows requests from `https://ngys9919.github.io`. Browser calls from other origins are blocked; `curl` and server-side clients are unaffected.

## Project Structure

```
simple-crm-backend/
├── Dockerfile                  # Multi-stage build (Maven → JRE 21 Alpine)
├── docker-compose.yml          # App + PostgreSQL for local use
├── pom.xml
├── mvnw / mvnw.cmd             # Maven wrapper
└── src/
    ├── main/
    │   ├── java/sg/edu/ntu/simple_crm/
    │   │   ├── SimpleCrmApplication.java
    │   │   ├── config/         # DataLoader (seed data)
    │   │   ├── controller/     # CustomerController (REST endpoints)
    │   │   ├── exceptions/     # CustomerNotFoundException
    │   │   ├── model/          # Customer, Interaction entities
    │   │   ├── repository/     # CustomerRepository, InteractionRepository
    │   │   └── service/        # CustomerService, CustomerServiceImpl, DemoService
    │   └── resources/
    │       └── application.properties
    └── test/
        ├── java/sg/edu/ntu/simple_crm/
        │   ├── controller/     # CustomerControllerTest
        │   └── service/        # CustomerServiceImplTest, DemoServiceTest
        └── resources/
            └── application.properties   # H2 test config
```

## Getting Started

### Prerequisites

- Java 21 (JDK)
- Docker and Docker Compose (for the easiest setup), or a local PostgreSQL 16 instance
- Git

### Clone

```bash
git clone https://github.com/ngys9919/simple-crm-backend.git
cd simple-crm-backend
```

### Option A — Docker Compose (recommended)

Starts PostgreSQL and the API together:

```bash
docker compose up --build
```

The API is then available at http://localhost:8080/customers.

### Option B — Run with Maven

The app reads its database settings from environment variables:

| Variable | Example |
|----------|---------|
| `SPRING_DATASOURCE_URL` | `jdbc:postgresql://localhost:5432/simplecrm` |
| `SPRING_DATASOURCE_USERNAME` | `postgres` |
| `SPRING_DATASOURCE_PASSWORD` | `password` |

```bash
# macOS / Linux
export SPRING_DATASOURCE_URL=jdbc:postgresql://localhost:5432/simplecrm
export SPRING_DATASOURCE_USERNAME=postgres
export SPRING_DATASOURCE_PASSWORD=password
./mvnw spring-boot:run
```

```powershell
# Windows PowerShell
$env:SPRING_DATASOURCE_URL="jdbc:postgresql://localhost:5432/simplecrm"
$env:SPRING_DATASOURCE_USERNAME="postgres"
$env:SPRING_DATASOURCE_PASSWORD="password"
.\mvnw.cmd spring-boot:run
```

Hibernate runs with `ddl-auto=update`, so tables are created on first start and data is preserved across restarts.

### Run the tests

```bash
./mvnw test
```

Tests use an in-memory H2 database, so no PostgreSQL is needed.

## Deployment

### Docker

```bash
docker build -t simple-crm-backend .
docker run -p 8080:8080 \
  -e SPRING_DATASOURCE_URL=jdbc:postgresql://<host>:5432/<db> \
  -e SPRING_DATASOURCE_USERNAME=<user> \
  -e SPRING_DATASOURCE_PASSWORD=<password> \
  simple-crm-backend
```

### Railway (production)

The backend is deployed on [Railway](https://railway.app/) from the `Dockerfile`, with a Railway PostgreSQL service attached:

1. Create a Railway project from this GitHub repository.
2. Add a PostgreSQL service to the project.
3. Set `SPRING_DATASOURCE_URL`, `SPRING_DATASOURCE_USERNAME` and `SPRING_DATASOURCE_PASSWORD` on the API service. The URL must be in JDBC form (`jdbc:postgresql://host:port/db`).
4. Deploy. Railway builds the image and exposes the service on a public domain.

### Frontend (GitHub Pages)

The UI is served from GitHub Pages at https://ngys9919.github.io/simple-crm-frontend/ and calls the Railway API. If you host the frontend on a different origin, update the `@CrossOrigin` origin in [CustomerController.java](src/main/java/sg/edu/ntu/simple_crm/controller/CustomerController.java).

## Contributing

1. Fork this repository.
2. Create a feature branch: `git checkout -b feature/my-change`.
3. Commit your changes and make sure `./mvnw test` passes.
4. Push the branch and open a Pull Request against `main`.

Questions and ideas are welcome in the [issues](../../issues).

## Developed By

>> Coder: **Ng Yew Seng (353F)**

>> NTU SCTP AI Engineering

>> for Module 4: DevSecOps - Automating Integration and Deployment

>> © Copyright 2026


## Acknowledgements

- [Spring Boot](https://spring.io/projects/spring-boot) and the Spring team
- [PostgreSQL](https://www.postgresql.org/) and [Railway](https://railway.app/)
- [GitHub Pages](https://pages.github.com/) for frontend hosting
- NTU SCTP Advanced Intelligent Engineering programme, Module 4

---

<div align="center">

If this project helped you, please consider giving it a ⭐

</div>

# Spring Boot KOL Match Engine

> High-concurrency KOL (Key Opinion Leader) matchmaking engine with cosine-similarity scoring, Redis caching, RabbitMQ async processing, and Swagger API documentation. Built with **Spring Boot 3**, **MyBatis Plus**, and **H2/MySQL**.

---

## ✨ Features

- 🧠 **Cosine Similarity Engine** — Multi-dimensional scoring (category, followers, engagement, budget)
- 📊 **Scoring Breakdown** — Per-dimension scores + letter grades (S/A/B/C)
- ⚡ **Redis Caching** — Optional cache layer with in-memory fallback
- 📨 **RabbitMQ** — Optional async match event processing
- 🗃️ **MyBatis Plus** — Type-safe ORM with pagination and soft-delete
- 📖 **Swagger UI** — Interactive API documentation at `/swagger-ui.html`
- 🐳 **Docker Compose** — One-command full-stack deployment
- ✅ **Zero-Config Start** — H2 in-memory DB, works out of the box

---

## 🚀 Quick Start

### Prerequisites
- Java 17+
- Maven 3.8+

```bash
# 1. Clone
git clone https://github.com/KhalDragonFeng/spring-kol-match-engine.git
cd spring-kol-match-engine

# 2. Run (H2 in-memory, no external deps needed)
mvn spring-boot:run

# 3. Open Swagger UI
# http://localhost:8080/swagger-ui.html

# 4. Try a match
curl -X POST http://localhost:8080/api/v1/match \
  -H "Content-Type: application/json" \
  -d '{"targetCategory":"beauty","targetTags":"skincare,tutorials","minFollowers":500000,"maxBudget":40000,"minEngagement":3.5}'
```

### With Docker (Full Stack: App + Redis + RabbitMQ)

```bash
mvn clean package -DskipTests
docker compose up --build
```

---

## 📖 API Endpoints

| Method | Path | Description |
|--------|------|-------------|
| `POST` | `/api/v1/match` | Execute full match with scoring |
| `POST` | `/api/v1/match/quick?category=beauty` | Quick match by category |
| `GET` | `/api/v1/kols?page=1&size=10` | List KOLs (paginated) |
| `GET` | `/api/v1/kols/{id}` | Get KOL by ID |
| `POST` | `/api/v1/kols` | Create KOL profile |
| `PUT` | `/api/v1/kols/{id}` | Update KOL profile |
| `DELETE` | `/api/v1/kols/{id}` | Soft-delete KOL |
| `GET` | `/api/v1/health` | Health check |
| `GET` | `/swagger-ui.html` | Swagger UI |
| `GET` | `/h2-console` | H2 Database console |

---

## 🏗️ Architecture

```
┌─────────────┐     ┌──────────────┐     ┌─────────────────┐
│  Controller  │────▶│ MatchService │────▶│ CosineSimilarity │
│   (REST)     │     │  (Business)  │     │    Engine        │
└─────────────┘     └──────┬───────┘     └─────────────────┘
                           │
                    ┌──────┴───────┐
                    │              │
              ┌─────▼────┐  ┌─────▼──────┐
              │   Cache   │  │  MyBatis+  │
              │  Service  │  │  (Mapper)  │
              └─────┬─────┘  └─────┬──────┘
                    │              │
              ┌─────▼────┐  ┌─────▼──────┐
              │  Redis /  │  │  H2 / MySQL│
              │  In-Mem   │  │  Database  │
              └──────────┘  └────────────┘
```

---

## 🛠️ Tech Stack

| Component | Technology |
|-----------|-----------|
| Framework | Spring Boot 3.4 |
| ORM | MyBatis Plus 3.5 |
| Database | H2 (default) / MySQL 8 |
| Cache | Redis 7 (optional) |
| Message Queue | RabbitMQ 3 (optional) |
| API Docs | SpringDoc OpenAPI (Swagger) |
| Build | Maven |
| Container | Docker + Docker Compose |

---

## 📂 Project Structure

```
src/main/java/com/qifanfeng/matchengine/
├── MatchEngineApplication.java          # Entry point
├── config/                              # Spring configurations
│   ├── MyBatisPlusConfig.java           # Pagination, mapper scan
│   ├── OpenApiConfig.java               # Swagger metadata
│   ├── RedisConfig.java                 # Conditional Redis setup
│   ├── RabbitMQConfig.java              # Conditional RabbitMQ setup
│   ├── WebConfig.java                   # CORS
│   └── GlobalExceptionHandler.java      # Error handling
├── controller/                          # REST endpoints
│   ├── MatchController.java             # Match engine API
│   ├── KolController.java               # KOL CRUD API
│   └── HealthController.java            # System health
├── service/                             # Business logic
│   ├── MatchService.java                # Orchestrates matching flow
│   ├── KolService.java                  # KOL CRUD operations
│   └── CacheService.java               # Cache with fallback
├── engine/                              # Core algorithms
│   └── CosineSimilarityEngine.java      # 4D similarity scoring
├── model/
│   ├── entity/                          # Database entities
│   ├── dto/                             # Request/Response DTOs
│   └── vo/                              # API response wrapper
└── mapper/                              # MyBatis Plus mappers
```

---

## 🎯 Matching Algorithm

The engine scores each KOL across 4 weighted dimensions:

| Dimension | Weight | Description |
|-----------|--------|-------------|
| Category | 35% | Exact category + Jaccard tag similarity |
| Followers | 20% | Sigmoid curve around min requirement |
| Engagement | 25% | Normalized against threshold |
| Budget | 20% | Price fit within constraints |

Final score is computed via **weighted cosine similarity** in 4D feature space.

---

## 👨‍💻 Author

**Qifan Feng** — Senior Full-Stack Architect & DevOps Specialist

- 🏠 [Portfolio](https://khaldragonfeng.github.io/portfolio/)
- 💻 [GitHub](https://github.com/KhalDragonFeng)

## 📄 License

MIT

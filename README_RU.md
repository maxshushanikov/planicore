# `planicore` — REST API для управления задачами (To-Do)

Этот проект представляет собой современное Spring Boot приложение, реализующее RESTful API для управления списком задач (To-Do). Проект разработан с использованием лучших практик и включает в себя автоматизированную CI/CD пайплайн, контейнеризацию и инструменты для тестирования.

---

## 📋 Описание

**`planicore`** — это серверная часть приложения для управления задачами. Он предоставляет CRUD-операции (создание, чтение, обновление, удаление) для сущности `Task`, а также стандартные эндпоинты мониторинга через Spring Boot Actuator.

### 🔧 Функциональность
- Создание новой задачи (`POST /api/v1/tasks`)
- Получение списка всех задач (`GET /api/v1/tasks`)
- Получение задачи по ID (`GET /api/v1/tasks/{id}`)
- Обновление задачи (`PUT /api/v1/tasks/{id}`)
- Удаление задачи (`DELETE /api/v1/tasks/{id}`)
- Проверка состояния сервиса (`GET /actuator/health`, `GET /ready`)

Каждая задача содержит:
- Заголовок (`title`)
- Описание (`description`)
- Статус: `TODO`, `IN_PROGRESS`, `DONE`
- Дата выполнения (`dueDate`)
- Даты создания и обновления

---

## 🛠️ Технологии

| Категория | Используемые технологии |
|---------|------------------------|
| **Язык и платформа** | Java 21, Spring Boot 3.5.5 |
| **Сборка** | Gradle 8.14.3 |
| **ORM и БД** | Spring Data JPA, Hibernate, PostgreSQL (prod), H2 (dev/test) |
| **Миграции** | Flyway |
| **Маппинг DTO** | MapStruct 1.5.5 |
| **Тестирование** | JUnit 5, Testcontainers, Mockito |
| **Контейнеризация** | Docker, Docker Compose |
| **CI/CD** | GitHub Actions |
| **API-документация** | Postman-коллекция |

---

## 🚀 Быстрый старт

### Предварительные требования
- JDK 21
- Docker и Docker Compose
- Gradle (опционально, используется wrapper)

### Запуск через Docker Compose
```bash
# Собрать и запустить весь стек (бэкенд + база)
docker-compose up --build

# Приложение будет доступно на:
# - API: http://localhost:8080
# - PostgreSQL: порт 5432
```

### Локальная сборка и запуск
```bash
# Собрать проект
./gradlew build

# Запустить приложение (профиль dev)
./gradlew bootRun

# Или запустить JAR напрямую
java -jar build/libs/planicore-1.0.0.jar
```

Приложение запустится на `http://localhost:8080`.

---

## 🌐 Профили конфигурации

Проект поддерживает несколько профилей:

| Профиль | База данных | Назначение |
|--------|------------|-----------|
| `default` | — | Общие настройки |
| `dev` | H2 in-memory | Разработка, H2-консоль доступна по `/h2-console` |
| `test` | Testcontainers + PostgreSQL | Интеграционные тесты |
| `prod` | PostgreSQL | Продакшен, настройки логирования и безопасности |

Активация профиля:
```bash
# Через переменную окружения
SPRING_PROFILES_ACTIVE=prod ./gradlew bootRun

# Или в docker-compose.yml
environment:
  SPRING_PROFILES_ACTIVE: prod
```

---

## 🧪 Тестирование

Запуск тестов:
```bash
./gradlew test
```

- **Юнит-тесты**: проверяют логику сервисов.
- **Интеграционные тесты**: используют `Testcontainers` для запуска реального PostgreSQL во время тестов.

Результаты тестов доступны в:
```
build/reports/tests/test/index.html
```

---

## 🤖 CI/CD Pipeline

Настроена автоматическая пайплайн в GitHub Actions:

1. **Build & Test** — запускается при пуше или PR в `master`.
2. **Build Docker Image** — собирает образ и отправляет в Docker Hub.
3. **Deploy to Production** — деплоит на сервер при пуше в `main`.

> ⚠️ Для работы CI/CD необходимо настроить секреты в GitHub:
> - `DOCKERHUB_USERNAME`, `DOCKERHUB_TOKEN`
> - `PROD_SERVER_HOST`, `PROD_SERVER_USER`, `PROD_SSH_KEY`

---

## 📦 Архитектура проекта

```
src/main/java/org/planicore/
├── server/
│   ├── controller/     # REST-контроллеры
│   ├── domain/         # JPA-сущности
│   ├── dto/            # Data Transfer Objects
│   ├── mapper/         # MapStruct-мапперы
│   ├── repository/     # Spring Data JPA репозитории
│   ├── service/        # Бизнес-логика
│   └── HealthController.java
└── PlanicoreApplication.java
```

---

## 🔄 API Endpoints (Postman)

Готовые коллекции Postman находятся в папке `postmann/`:
- `todo-api.postman_collection.json` — основные запросы
- `todo-api-dev.postman_environment.json` — переменные для dev
- `todo-api-prod.postman_environment.json` — переменные для prod

Пример запроса на создание задачи:
```json
POST {{baseUrl}}/api/v1/tasks
Content-Type: application/json

{
  "title": "Buy groceries",
  "description": "Milk, bread, eggs",
  "status": "TODO",
  "dueDate": "2025-09-20T18:00:00"
}
```

---

## 📂 Конфигурация

Основные файлы конфигурации:
- `src/main/resources/application.yml` — основная конфигурация с профилями
- `src/main/resources/application.properties` — общие свойства
- `src/main/resources/db/migration/V1__create_tasks_table.sql` — SQL-миграция

---

## 🐳 Docker

- **Бэкенд**: `Dockerfile` в корне проекта использует multi-stage сборку.
- **База данных**: PostgreSQL 16 в `docker-compose.yml`.
- **Health Check**: проверка через `/actuator/health`.

---

## 📊 Мониторинг

Доступные эндпоинты:
- `GET /actuator/health` — статус приложения
- `GET /actuator/info` — информация о версии
- `GET /actuator/metrics` — метрики (Prometheus-совместимо)
- `GET /ready` — простой readiness check

---

## 📄 Лицензия

Проект не имеет лицензии (по умолчанию — авторские права).

---

## 🙋 Поддержка

Для вопросов и предложений используйте Issues на GitHub.

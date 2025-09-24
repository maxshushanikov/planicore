# `planicore` – REST-API zur Aufgabenverwaltung (To-Do)

Dieses Projekt ist eine moderne Spring Boot-Anwendung, die eine RESTful-API zur Verwaltung von Aufgaben (To-Do) bereitstellt. Es wurde nach aktuellen Best Practices entwickelt und enthält automatisierte CI/CD-Pipelines, Containerisierung sowie umfassende Testwerkzeuge.

---

## 📋 Übersicht

**`planicore`** ist die Backend-Komponente einer Aufgabenverwaltungsanwendung. Sie bietet vollständige CRUD-Operationen (Erstellen, Lesen, Aktualisieren, Löschen) für die Entität `Task` sowie standardmäßige Überwachungsendpunkte über Spring Boot Actuator.

### 🔧 Funktionen
- Erstellen einer neuen Aufgabe (`POST /api/v1/tasks`)
- Abrufen aller Aufgaben (`GET /api/v1/tasks`)
- Abrufen einer Aufgabe anhand ihrer ID (`GET /api/v1/tasks/{id}`)
- Aktualisieren einer Aufgabe (`PUT /api/v1/tasks/{id}`)
- Löschen einer Aufgabe (`DELETE /api/v1/tasks/{id}`)
- Health- und Readiness-Checks (`GET /actuator/health`, `GET /ready`)

Jede Aufgabe enthält:
- Titel (`title`)
- Beschreibung (`description`)
- Status: `TODO`, `IN_PROGRESS`, `DONE`
- Fälligkeitsdatum (`dueDate`)
- Erstellungs- und Aktualisierungszeitpunkte

---

## 🛠️ Technologien

| Kategorie | Verwendete Technologien |
|--------|-------------------|
| **Sprache & Plattform** | Java 21, Spring Boot 3.5.5 |
| **Build-Tool** | Gradle 8.14.3 |
| **ORM & Datenbank** | Spring Data JPA, Hibernate, PostgreSQL (Produktion), H2 (Entwicklung/Test) |
| **Datenbank-Migrationen** | Flyway |
| **DTO-Mapping** | MapStruct 1.5.5 |
| **Testen** | JUnit 5, Testcontainers, Mockito |
| **Containerisierung** | Docker, Docker Compose |
| **CI/CD** | GitHub Actions |
| **API-Dokumentation** | Postman-Sammlung |

---

## 🚀 Schnellstart

### Voraussetzungen
- JDK 21
- Docker und Docker Compose
- Gradle (optional, Wrapper enthalten)

### Ausführen mit Docker Compose
```bash
# Build und Start des gesamten Stacks (Backend + Datenbank)
docker-compose up --build

# Die Anwendung ist verfügbar unter:
# - API: http://localhost:8080
# - PostgreSQL: Port 5432
```

### Lokaler Build und Start
```bash
# Projekt bauen
./gradlew build

# Anwendung starten (Entwicklungsprofil)
./gradlew bootRun

# Oder JAR direkt ausführen
java -jar build/libs/planicore-1.0.0.jar
```

Die Anwendung startet unter `http://localhost:8080`.

---

## 🌐 Konfigurationsprofile

Das Projekt unterstützt mehrere Profile:

| Profil | Datenbank | Zweck |
|--------|---------|--------|
| `default` | — | Allgemeine Einstellungen |
| `dev` | H2 im Arbeitsspeicher | Entwicklung, H2-Konsole verfügbar unter `/h2-console` |
| `test` | Testcontainers + PostgreSQL | Integrationstests |
| `prod` | PostgreSQL | Produktivumgebung mit erweiterter Protokollierung und Sicherheit |

Aktivierung eines Profils:
```bash
# Mit Umgebungsvariable
SPRING_PROFILES_ACTIVE=prod ./gradlew bootRun

# Oder in docker-compose.yml
environment:
  SPRING_PROFILES_ACTIVE: prod
```

---

## 🧪 Tests

Ausführen der Tests:
```bash
./gradlew test
```

- **Unit-Tests**: Überprüfen der Service-Logik.
- **Integrationstests**: Nutzen `Testcontainers`, um während der Tests eine echte PostgreSQL-Instanz zu starten.

Testergebnisse sind verfügbar unter:
```
build/reports/tests/test/index.html
```

Alle Tests wurden erfolgreich durchgeführt.

---

## 🤖 CI/CD-Pipeline

Eine automatisierte Pipeline ist mit GitHub Actions konfiguriert:

1. **Build & Test** – Wird bei Push oder PR in `master` ausgeführt.
2. **Docker-Image bauen** – Baut das Image und pusht es nach Docker Hub.
3. **In Produktion deployen** – Deployt auf den Produktionsserver bei Push in `main`.

> ⚠️ Für die CI/CD muss Folgendes in GitHub konfiguriert werden:
> - `DOCKERHUB_USERNAME`, `DOCKERHUB_TOKEN`
> - `PROD_SERVER_HOST`, `PROD_SERVER_USER`, `PROD_SSH_KEY`

---

## 📦 Projektstruktur

```
src/main/java/org/planicore/
├── server/
│   ├── controller/     # REST-Controller
│   ├── domain/         # JPA-Entitäten
│   ├── dto/            # Data Transfer Objects
│   ├── mapper/         # MapStruct-Mapper
│   ├── repository/     # Spring Data JPA-Repositories
│   ├── service/        # Geschäftslogik
│   └── HealthController.java
└── PlanicoreApplication.java
```

---

## 🔄 API-Endpunkte (Postman)

Fertige Postman-Sammlungen befinden sich im Ordner `postmann/`:
- `todo-api.postman_collection.json` — Haupt-API-Anfragen
- `todo-api-dev.postman_environment.json` — Entwicklungsvariablen
- `todo-api-prod.postman_environment.json` — Produktionsvariablen

Beispiel: Aufgabe erstellen
```json
POST {{baseUrl}}/api/v1/tasks
Content-Type: application/json

{
  "title": "Einkaufen",
  "description": "Milch, Brot, Eier",
  "status": "TODO",
  "dueDate": "2025-09-20T18:00:00"
}
```

---

## 📂 Konfigurationsdateien

Wichtige Konfigurationsdateien:
- `src/main/resources/application.yml` — Hauptkonfiguration mit Profilen
- `src/main/resources/application.properties` — allgemeine Eigenschaften
- `src/main/resources/db/migration/V1__create_tasks_table.sql` — SQL-Migrationsdatei

---

## 🐳 Docker

- **Backend**: Multi-stage `Dockerfile` im Hauptverzeichnis.
- **Datenbank**: PostgreSQL 16 in `docker-compose.yml`.
- **Health Check**: Implementiert über `/actuator/health`.

---

## 📊 Überwachung

Verfügbare Endpunkte:
- `GET /actuator/health` — Anwendungsstatus
- `GET /actuator/info` — Versionsinformation
- `GET /actuator/metrics` — Prometheus-kompatible Metriken
- `GET /ready` — einfacher Readiness-Check

---

## 📄 Lizenz

Keine Lizenz angegeben (alle Rechte vorbehalten).

---

## 🙋 Unterstützung

Für Fragen oder Anregungen nutzen Sie bitte die GitHub Issues.

---

> 💡 **Tipp**: Stellen Sie vor dem ersten Start sicher, dass alle Abhängigkeiten installiert sind und die Umgebungsvariablen korrekt konfiguriert sind.
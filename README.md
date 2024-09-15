# tendering_service
Приложение, работающее с тендерной системой.

## Инструменты
- OpenJDK 17
- Maven
- Spring
- PostgreSQL
- Docker
- Liquibase
- JUnit 5
- Mockito

## Сущности
### Пользователь и организация

Сущности пользователя и организации уже созданы и представлены в базе данных следующим образом:

Пользователь (User):

```sql
CREATE TABLE employee (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    username VARCHAR(255) UNIQUE NOT NULL,
    first_name VARCHAR(255),
    last_name VARCHAR(255),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
```

Организация (Organization):
```sql
CREATE TYPE organization_type AS ENUM (
    'IE',
    'LLC',
    'JSC'
);

CREATE TABLE organization (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    name VARCHAR(255) NOT NULL,
    description VARCHAR(255) NOT NULL,
    type organization_type VARCHAR(50),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE organization_responsible (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    organization_id UUID REFERENCES organization(id) ON DELETE CASCADE,
    user_id UUID REFERENCES employee(id) ON DELETE CASCADE
);
```

## Запуск работы базы данных

```bash
docker compose up -d
```

## Остановка работы базы данных

```bash
docker compose down
```

## Очистка базы данных
```bash
docker volume rm tendering_service_tendering_data
```

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

Пользователь (User)

```sql
CREATE TABLE employee (
    id UUID PRIMARY KEY NOT NULL,
    username VARCHAR(255) UNIQUE NOT NULL,
    first_name VARCHAR(255),
    last_name VARCHAR(255),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
```

Организация (Organization)
```sql
CREATE TABLE organization (
    id UUID PRIMARY KEY NOT NULL,
    name VARCHAR(255) NOT NULL,
    description VARCHAR(255) NOT NULL,
    type VARCHAR(50),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE organization_responsible (
    id UUID PRIMARY KEY NOT NULL,
    organization_id UUID REFERENCES organization(id) ON DELETE CASCADE,
    user_id UUID REFERENCES employee(id) ON DELETE CASCADE
);
```

### Заявка и тендер
Сущности заявки и тендера представлены в базе данных следующим образом:

Bid (Заявка)
```sql
CREATE TABLE bid (
    id UUID PRIMARY KEY NOT NULL,
    name VARCHAR(255) NOT NULL,
    description VARCHAR(255) NOT NULL,
    tender_id UUID NOT NULL REFERENCES tender(id),
    organization_id UUID NOT NULL REFERENCES organization(id),
    author_type VARCHAR(50),
    status VARCHAR(50),
    version INT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    author_id UUID NOT NULL,
    decision VARCHAR(50),
    feedback VARCHAR(255)
);

CREATE TABLE bid_history (
    id UUID PRIMARY KEY NOT NULL,
    bid_id UUID NOT NULL REFERENCES bid(id) ON DELETE CASCADE,
    name VARCHAR(255) NOT NULL,
    description VARCHAR(255) NOT NULL,
    organization_id UUID NOT NULL REFERENCES organization(id),
    author_type VARCHAR(50),
    status VARCHAR(50),
    version INT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    author_id UUID NOT NULL,
    decision VARCHAR(50),
    feedback VARCHAR(255)
);
```

Tender (Тендер)
```sql
CREATE TABLE tender (
    id UUID PRIMARY KEY NOT NULL,
    name VARCHAR(255) NOT NULL,
    description VARCHAR(255) NOT NULL,
    organization_id UUID NOT NULL REFERENCES organization(id),
    service_type VARCHAR(50),
    status VARCHAR(50),
    version INT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    creator_username VARCHAR(255) NOT NULL
);

CREATE TABLE tender_history (
    id UUID PRIMARY KEY NOT NULL,
    tender_id UUID NOT NULL REFERENCES tender(id) ON DELETE CASCADE,
    name VARCHAR(255) NOT NULL,
    description VARCHAR(255) NOT NULL,
    organization_id UUID NOT NULL REFERENCES organization(id),
    service_type VARCHAR(50),
    status VARCHAR(50),
    version INT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    creator_username VARCHAR(255) NOT NULL
);
```

## Запуск приложения
```bash
docker compose up -d --build
```

## Остановка приложения
```bash
docker compose down
```

## Просмотр логов приложения

```bash
docker logs tendering_service-app-1
```

## Очистка базы данных
```bash
docker volume rm tendering_service_data
```

## Данные для авторизации в БД

- ### Для базы данных приложения (PostgreSQL, порт - 5432):
    - username: admin
    - password: password

## Примечания
- Спецификация API находится в файле ```openapi.yml```.

# Money Transfer Service

REST-сервис для перевода денег между банковскими картами с подтверждением операции по коду.

---

## Технологии

- Java 17+
- Spring Boot
- Maven
- Docker + Docker Compose
- Testcontainers
- Mockito

---

## Запуск проекта

Через Docker Compose: `docker-compose up --build`

---

## Доступ к сервису

- Backend API: http://localhost:8080
- Frontend: http://localhost:3000

---

## API методы

### Перевод денег: `POST /transfer`

Request:
```aiignore
{
  "cardFromNumber": String,
  "cardFromValidTill": String,
  "cardFromCVV": String,
  "cardToNumber": String,
  "amount": {
    "value": Long,
    "currency": String
  }
}
```

Response:
```aiignore
{
  "operationId": String
}
```

### Подтверждение операции: `POST /confirmOperation`

Request:
```aiignore
{
  "operationId": String,
  "code": String
}
```

Response:
```aiignore
{
  "operationId": String
}
```

---

## Ошибки

### 400 BAD REQUEST

Пример:
```aiignore
{
  "id": 2,
  "message": "Invalid cardFromNumber format"
}
```

### 500 INTERNAL SERVER ERROR

Пример:
```aiignore
{
  "id": 8,
  "message": "Invalid confirmation code"
}
```

---

## Логирование

Все операции сохраняются в файл `./backend/logs/transfer-log.jsonl`

Пример записи:
```aiignore
{
  "operationId": "0",
  "transfer": { ... },
  "date": "2026-04-21",
  "time": "13:59:44",
  "commission": "100",
  "status": "CONFIRMED"
}
```



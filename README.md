# my-bank-app
Учебный проект 3 модуля для курса мидл java-разработчик, яндекс практикум

## Настройка и запуск

Клонируйте репозиторий:
```bash
git clone https://github.com/lesha226/my-bank-app.git
cd ./my-bank-app
```

Соберите проект:
```bash
mvn clean package -DskipTests
```
запустите докер:
```bash
docker-compose up -d
```

Приложение будет доступно по адресу: ```http://localhost:8080```
Для входа доступно 3 пользователя:
login=user password=user
login=user2 password=user2
login=user3 password=user3

Содержимое докера:
http://localhost:8180           - keycloak admin:admin
localhost:5432                  - postgres postgres:password
http://localhost:8761/eureka    - discovery (my-bank-app/eureka)
http://localhost:8888           - config (my-bank-app/config)
http://localhost:8081           - gateway (my-bank-app/gateway)
http://localhost:8085           - accounts-service (my-bank-app/account)
http://localhost:8086           - cash-service (my-bank-app/cash)
http://localhost:8087           - transfer-service (my-bank-app/transfer)
http://localhost:8088           - notification-service (my-bank-app/notification)
http://localhost:8080           - front-ui user:user user2:user2 user3:user3 (my-bank-app/front-ui)



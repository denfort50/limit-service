# limit-service

## Описание
Микросервис лимитов.

## Стек используемых технологий
* Java 17
* Gradle 8.6
* Spring Boot 3.3.2
* Spring Web
* Spring Data JPA
* Spring Validation
* PostgreSQL
* Testcontainers
* JUnit 5
* FlyWay
* Lombok
* Checkstyle 

## Требуемое окружение для запуска
* JDK 17
* Gradle 8.6
* Docker

## Инструкция по запуску проекта
1) Запустить docker-контейнер с тестовой базой данных в `config/docker/docker-compose.yaml`
2) Проверить работу ендпойнтов, использя запросы в файле `test.http`

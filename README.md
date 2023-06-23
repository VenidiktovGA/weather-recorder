Регистратор погоды

## DataBase
docker run --name weather-recorder -p 5433:5432 -e POSTGRES_PASSWORD=qwerty -d postgres:13.3

## Документация API
http://localhost:8010/weather-recorder/swagger-ui

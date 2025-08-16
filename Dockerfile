# 1. Сборка проекта с Maven
FROM maven:3.9.2-eclipse-temurin-17 AS build
WORKDIR /app

# Копируем pom.xml и скачиваем зависимости (кэширование)
COPY pom.xml .
RUN mvn dependency:go-offline

# Копируем исходники и собираем проект
COPY src ./src
RUN mvn clean package -DskipTests

# 2. Лёгкий образ для запуска
FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app

# Копируем JAR из предыдущего шага
COPY --from=build /app/target/*.jar app.jar

# Переменные окружения по умолчанию — пустые строки, чтобы Docker не ругался
ENV DB_URL=""
ENV DB_NAME=""
ENV DB_USERNAME=""
ENV DB_PASSWORD=""
ENV ACCESS_TOKEN=""
ENV REFRESH_TOKEN=""

# Открываем порт приложения
EXPOSE 8080

# Запуск приложения
ENTRYPOINT ["sh", "-c", "java -jar app.jar"]
# Stage 1: Сборка приложения
FROM gradle:8.8-jdk21 AS build
WORKDIR /home/gradle/project
COPY . .
RUN gradle build --no-daemon -x test

# Stage 2: Финальный образ
FROM eclipse-temurin:21-jre-jammy
EXPOSE 8080
WORKDIR /app

# Копируем JAR и конфигурационные файлы
COPY --from=build /home/gradle/project/build/libs/*.jar app.jar
COPY --from=build /home/gradle/project/src/main/resources/application.properties /app/config/application.properties

# Настройка запуска
RUN echo 'java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /app/app.jar' > /app/start.sh \
    && chmod +x /app/start.sh

# Health check для мониторинга состояния приложения
HEALTHCHECK --interval=30s --timeout=10s --retries=5 \
  CMD curl -f http://localhost:8080/actuator/health || exit 1

ENTRYPOINT ["/app/start.sh"]
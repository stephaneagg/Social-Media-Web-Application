# BUILD
FROM eclipse-temurin:21-jdk as BUILD

WORKDIR /app

COPY .mvn/ .mvn/
COPY mvnw pom.xml ./

RUN ./mvnw dependency:go-offline

COPY src ./src

RUN ./mvnw clean package -DskipTests

# Runtime
FROM eclipse-temurin:21-jre

RUN apt-get update \
    && apt-get install -y --no-install-recommends curl \
    && rm -rf /var/lib/apt/lists/* \
    && useradd --system --create-home --uid 1001 appuser

WORKDIR /app

COPY --from=build /app/target/spring-boot-0.0.1-SNAPSHOT.jar app.jar

RUN chown -R appuser:appuser /app

USER appuser

EXPOSE 8080

CMD ["java", "-jar", "app.jar"]

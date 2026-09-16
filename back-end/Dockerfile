# BUILD
FROM eclipse-temurin:17-jdk as BUILD

WORKDIR /app

COPY .mvn/ .mvn/
COPY mvnw pom.xml ./

RUN ./mvnw dependency:go-offline

COPY src ./src

RUN ./mvnw clean package -DskipTests

# Runtime
FROM eclipse-temurin:17-jre

WORKDIR /app

COPY --from=build /app/target/spring-boot-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

CMD ["java", "-jar", "app.jar"]

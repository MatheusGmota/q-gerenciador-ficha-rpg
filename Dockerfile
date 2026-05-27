# =========================
# Build Stage
# =========================
FROM maven:3.9.9-eclipse-temurin-21 AS build

WORKDIR /app

COPY pom.xml .
COPY src ./src

RUN mvn clean package -DskipTests

# =========================
# Runtime Stage
# =========================
FROM eclipse-temurin:21-jre

WORKDIR /work/

COPY --from=build /app/target/quarkus-app/ /work/

EXPOSE 8080

CMD ["java", "-jar", "quarkus-run.jar"]
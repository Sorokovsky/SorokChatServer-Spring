FROM maven:3.9.11-amazoncorretto-24-al2023 AS build
WORKDIR /build
COPY pom.xml .
RUN --mount=type=cache,target=/root/.m2 mvn dependency:go-offline -B
COPY src ./src
RUN --mount=type=cache,target=/root/.m2 mvn clean package -B -DskipTests

FROM bellsoft/liberica-openjre-debian:24
RUN adduser --system spring-boot && addgroup --system spring-boot && adduser spring-boot spring-boot
USER spring-boot
WORKDIR /app
COPY --from=build /build/target/application.jar /application.jar
ENTRYPOINT ["java", "-jar", "/application.jar"]
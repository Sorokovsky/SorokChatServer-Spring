FROM maven:3.9.11-amazoncorretto-24-al2023 as build
WORKDIR /build
COPY pom.xml .
COPY src ./src
RUN ls -la && mvn clean package -X && ls -la target/
RUN mvn dependency:copy-dependencies -DincludeScope=runtime
RUN ls -la target/

FROM bellsoft/liberica-openjdk-debian:24
RUN adduser --system spring-boot && addgroup --system spring-boot && adduser spring-boot spring-boot
USER spring-boot
WORKDIR /app
COPY --from=build /build/target/dependency /lib
COPY --from=build /build/target/application.jar /application.jar
ENTRYPOINT ["java", "-jar", "/application.jar"]
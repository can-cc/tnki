FROM gradle:jdk8 as builder
COPY . /app
WORKDIR /app
RUN ./gradlew build -x test

FROM openjdk:8-jdk-alpine
ARG JAR_FILE=/app/target/*.jar
COPY --from=builder ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
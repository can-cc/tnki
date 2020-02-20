FROM gradle:jdk8 as builder
COPY . /app
WORKDIR /app
RUN ./gradlew build -x test

FROM openjdk:8-jdk-alpine
WORKDIR /app
COPY --from=builder /app/build .
ENTRYPOINT ["java","-jar","/app/build/libs/*.jar"]
FROM gradle:jdk8 as builder
COPY . /app
WORKDIR /app
RUN ./gradlew build -x test

FROM openjdk:8-jdk-alpine
COPY --from=builder /app/build .
ENTRYPOINT ["java","-jar","/build/lib/*.jar"]
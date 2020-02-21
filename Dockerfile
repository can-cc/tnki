FROM gradle:jdk8
COPY . /app
WORKDIR /app
RUN ./gradlew build -x test
ENTRYPOINT ["java","-jar","/app/build/libs/core-0.0.1.jar"]

FROM gradle:jdk8
RUN addgroup -S spring && adduser -S spring -G spring
COPY . /app
WORKDIR /app
RUN ./gradlew build -x test
ENTRYPOINT ["java","-jar","build/libs/*.jar"]

# Build
FROM eclipse-temurin:21-jdk-alpine as build
WORKDIR /home/umcu/build
COPY . .
RUN ./gradlew clean
RUN ./gradlew bootJar

# Run
FROM eclipse-temurin:21-jre-alpine
WORKDIR /home/umcu/run
COPY --from=build /home/umcu/build/build/libs/*.jar app.jar
CMD ["java", "-jar", "app.jar"]

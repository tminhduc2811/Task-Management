FROM maven:3.5.2-jdk-8-alpine AS build-stage
WORKDIR /build/
COPY pom.xml /build/
RUN mvn -B -f pom.xml dependency:go-offline
COPY src /build/src/

#RUN mvn package -DskipTests, will change later
RUN mvn -B package -DskipTests

FROM openjdk:8-jre-alpine AS production

WORKDIR /app

COPY --from=build-stage /build/target/task-management-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT exec java -XX:+UseSerialGC -Xss512k -XX:MaxRAM=200m -jar app.jar
FROM openjdk:8-jre-alpine AS production
WORKDIR /app
COPY target/task-management-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT exec java -XX:+UseSerialGC -Xss512k -XX:MaxRAM=200m -jar app.jar
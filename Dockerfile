# Use the arm64v8/ubuntu base image
FROM arm64v8/ubuntu

# Install dependencies and configure your application
RUN apt-get update && apt-get install -y docker.io

#
# Build stage
#
FROM maven:3.8.2-jdk-11 AS build
COPY . .
RUN mvn clean package -DskipTests

#
# Package stage
#
FROM openjdk:11-jdk-slim
COPY --from=build /target/smart_inventory-1.0.0.jar smart_inventory.jar
# ENV PORT=8080
EXPOSE 8080
ENTRYPOINT ["java","-jar","smart_inventory.jar"]

# First stage: Build the application
FROM maven:3.6.3-openjdk-11 AS build
WORKDIR /app
COPY . /app
RUN mvn clean package

# Second stage: Run the application
FROM openjdk:11-jre-slim
ARG INTERFACE
WORKDIR /app
COPY --from=build /app/target/mock-endpoint-framework.jar /app/mock-endpoint-framework.jar
EXPOSE 8089
CMD ["java", "-jar", "mock-endpoint-framework.jar", "${INTERFACE}"]

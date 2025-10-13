# Start with a base image for Java 17
FROM openjdk:17-jdk-slim

# Set the working directory
WORKDIR /app

# Copy the Maven Wrapper files first (best practice for caching)
COPY mvnw .
COPY .mvn .mvn

# Copy the Maven project file and source code
COPY pom.xml .
COPY src ./src

# Make the mvnw script executable
RUN chmod +x ./mvnw

# Build the Spring Boot application using the Maven Wrapper
RUN ./mvnw package -DskipTests

# Expose the port the application will run on
EXPOSE 8080

# Command to run the application
ENTRYPOINT ["java", "-jar", "target/product-serv-0.0.1-SNAPSHOT.jar"]
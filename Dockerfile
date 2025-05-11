# Use a lightweight Java 17 base image
FROM eclipse-temurin:17-jdk-jammy

# Set environment variables for the application
ENV SPRING_DATASOURCE_URL=jdbc:postgresql://db:5432/projectx
ENV SPRING_DATASOURCE_USERNAME=postgres
ENV SPRING_DATASOURCE_PASSWORD=123

# Set the working directory inside the container
WORKDIR /app

# Copy the JAR file from the target directory into the container
COPY target/projectx-0.0.1-SNAPSHOT.jar application.jar

# Expose the application port
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "/app/application.jar"]

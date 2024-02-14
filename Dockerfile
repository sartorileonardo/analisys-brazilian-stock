FROM openjdk:11

# Copy your application code
WORKDIR /app

COPY . .

# Install dependencies (if Maven)
RUN mvn clean install

# Expose ports (if needed)
EXPOSE 8080

# Entry point
CMD ["java", "-jar", "stock-0.0.1-SNAPSHOT.jar"]

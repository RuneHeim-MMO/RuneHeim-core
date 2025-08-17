FROM eclipse-temurin:21-jdk

WORKDIR /plugin

# Copy Gradle wrapper and config
COPY gradlew .
COPY gradle gradle
COPY build.gradle .
COPY settings.gradle .

# Copy source code
COPY src src
COPY libs libs

RUN chmod +x ./gradlew

# Default build command
CMD ["./gradlew", "build"]

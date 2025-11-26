FROM eclipse-temurin:21-jdk

WORKDIR /app

COPY mutantes/gradlew .
COPY mutantes/gradle ./gradle
COPY mutantes/build.gradle .
COPY mutantes/settings.gradle .
COPY mutantes/src ./src

RUN chmod +x ./gradlew
RUN ./gradlew build -x test

CMD ["java", "-jar", "build/libs/mutantes-0.0.1-SNAPSHOT.jar"]

FROM openjdk:21-jdk as builder

WORKDIR /app

COPY build.gradle ./
COPY settings.gradle ./
COPY gradle ./gradle
COPY gradlew ./gradlew
COPY src ./src

RUN microdnf install findutils
RUN chmod +x ./gradlew
RUN ./gradlew clean build -x test --stacktrace

FROM openjdk:21-jdk

WORKDIR /app

COPY --from=builder /app/build/libs/*.jar ./app.jar

ENV TZ=Asia/Seoul

ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=dev", "app.jar"]

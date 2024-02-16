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

ENTRYPOINT ["java", "-jar", "app.jar"]


#FROM adoptopenjdk/openjdk11:jre-11.0.6_10-alpine
#ARG JAR_FILE=build/libs/*.jar
#COPY ${JAR_FILE} app.jar
#ENV SPRING_PROFILES_ACTIVE=prod
#ENTRYPOINT ["java","-jar", "-Dspring.profiles.active=prod", "/app.jar"]


# 되는거
#FROM openjdk:21-slim
#ARG JAR_FILE=build/libs/*.jar
#COPY ${JAR_FILE} ./app.jar
#ENV TZ=Asia/Seoul
#ENTRYPOINT ["java", "-jar", "app.jar"]
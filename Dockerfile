FROM amazoncorretto:21 as builder

WORKDIR /app

COPY build.gradle ./
COPY settings.gradle ./
COPY gradle ./gradle
COPY gradlew ./gradlew
COPY src ./src

RUN chmod +x ./gradlew
RUN ./gradlew clean build

FROM amazoncorretto:21

WORKDIR /app

COPY --from=builder /app/build/libs/*.jar ./app.jar

ENV TZ=Asia/Seoul

ENTRYPOINT ["java", "-jar", "app.jar"]
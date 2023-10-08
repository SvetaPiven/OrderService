FROM gradle:jdk17-alpine AS build-image
WORKDIR /app
COPY --chown=gradle:gradle build.gradle settings.gradle /app/
COPY --chown=gradle:gradle src /app/src
RUN gradle --no-daemon -x test build

FROM openjdk:17-alpine
COPY --from=build-image /app/build/libs/OrderService-1.0-SNAPSHOT.jar order.jar
ENTRYPOINT java -jar order.jar
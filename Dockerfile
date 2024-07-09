FROM amazoncorretto:21-alpine3.17-jdk
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring
VOLUME /tmp
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
COPY src/main/resources/fbadmin.json .
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app.jar"]
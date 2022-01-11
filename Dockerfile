FROM openjdk:11
EXPOSE 8080
WORKDIR /app
COPY ./target/*.jar /app/app.jar
ENTRYPOINT [ "java", "-jar", "/app/app.jar" ]
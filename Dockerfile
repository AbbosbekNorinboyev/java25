FROM eclipse-temurin:25-jdk
WORKDIR /app
COPY target/*.jar app.jar
EXPOSE 1236
ENTRYPOINT ["java","-jar","/app/app.jar"]

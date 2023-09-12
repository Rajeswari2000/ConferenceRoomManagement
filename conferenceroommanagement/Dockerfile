FROM maven:3.6.3-jdk-8 AS build
COPY . .
RUN mvn clean package -DskipTests

FROM openjdk:8-jdk-alpine 
COPY --from=build /target/conferenceroommanagement-0.0.1-SNAPSHOT.jar conferenceroommanagement.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "conferenceroommanagement.jar"]
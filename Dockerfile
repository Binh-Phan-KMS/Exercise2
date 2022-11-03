FROM openjdk:11.0.16
COPY target/Exercise2-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
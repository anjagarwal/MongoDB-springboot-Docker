FROM adoptopenjdk/openjdk11:alpine-jre
ADD target/xebia-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
EXPOSE 27017
ENTRYPOINT ["java","-jar","app.jar"]
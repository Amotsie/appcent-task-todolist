#FROM java:8
FROM adoptopenjdk/openjdk11:alpine-jre

EXPOSE 8080

ADD target/docker-todo.jar docker-todo.jar

ENTRYPOINT ["java","-jar","docker-demo.jar"]

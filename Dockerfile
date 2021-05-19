FROM java:8

EXPOSE 8080

ADD target/docker-todo.jar docker-todo.jar

ENTRYPOINT ["java","-jar","docker-demo.jar"]
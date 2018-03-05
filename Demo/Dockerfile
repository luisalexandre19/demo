FROM openjdk:8-jdk-alpine
MAINTAINER Luis Alexandre <luisalexandre19@gmail.com>
VOLUME /tmp
EXPOSE 8080
ADD /target/Demo-1.0.0.jar demo.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/demo.jar"]
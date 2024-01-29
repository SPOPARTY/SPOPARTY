FROM azul/zulu-openjdk:17

MAINTAINER HyeonWoo <gogoadl@naver.com> 

ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]

RUN \
    apt-get update && \
    apt-get install -y vim \

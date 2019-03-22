FROM openjdk:8-jre-slim

ARG JAR_FILE=census-rm-casesvc*.jar
RUN apt-get update
RUN apt-get -yq install curl
RUN apt-get -yq clean
COPY target/$JAR_FILE /opt/census-rm-casesvc.jar

ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -jar /opt/census-rm-casesvc.jar" ]


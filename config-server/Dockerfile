FROM openjdk:17-ea-11-jdk-slim
VOLUME /tmp
COPY springCloudConfigServer.jks springCloudConfigServer.jks
COPY build/libs/config-server-1.0.jar config-server-1.0.jar
ENTRYPOINT ["java", "-jar", "config-server-1.0.jar"]
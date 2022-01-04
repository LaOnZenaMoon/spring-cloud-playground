FROM openjdk:17-ea-11-jdk-slim
VOLUME /tmp
COPY build/libs/gateway-server-1.0.jar gateway-server-1.0.jar
ENTRYPOINT ["java", "-jar", "gateway-server-1.0.jar"]
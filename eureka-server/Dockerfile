FROM openjdk:17-ea-11-jdk-slim
VOLUME /tmp
COPY build/libs/eureka-server-1.0.jar eureka-server-1.0.jar
ENTRYPOINT ["java", "-jar", "eureka-server-1.0.jar"]
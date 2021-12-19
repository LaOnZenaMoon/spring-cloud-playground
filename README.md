# spring-cloud-playground
* All about Spring Cloud 
* Will be used with other projects[spring-cloud-config-server]

## eureka-server
* Spring Cloud Netflix Eureka

## gateway-server
* Spring Cloud Gateway

## user-service
* Microservice for test
* User REST API

## catalog-service
* Microservice for test
* Product catalog REST API

## order-service
* Microservice for test
* Order REST API

## admin-service
* Microservice for test

## core
* Common module used by all microservices

## config-server
* Spring Cloud Config Server
* Using Spring Cloud Bus & RabbitMQ, broadcast configuration files.
  ```
  docker run -it --rm --name rabbitmq -p 5672:5672 -p 15672:15672 rabbitmq:3.9-management
  ```

## skills
* Java 11
* Spring Boot 2.4
* Spring Boot Actuator
* Spring Cloud
  * Spring Cloud Netflix Eureka
  * Spring Cloud Gateway
  * Spring Cloud Config
  * Spring Cloud Bus
package me.lozm.global.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//@Configuration
public class FilterConfig {

//    @Bean
    public RouteLocator gatewayRoutes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route(r -> r.path("/user-service/**")
                        .filters(f -> f.addRequestHeader("user-request", "user-request-header")
                        .addResponseHeader("user-response", "user-response-header"))
                        .uri("http://localhost:8081"))
                .route(r -> r.path("/admin-service/**")
                        .filters(f -> f.addRequestHeader("admin-request", "admin-request-header")
                                .addResponseHeader("admin-response", "admin-response-header"))
                        .uri("http://localhost:8082"))
                .build();
    }

}

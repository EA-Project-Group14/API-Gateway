package com.example.apigateway;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {
    @Autowired
    private SecurityFilter filter;

//    @Bean
//    public RouteLocator routes(RouteLocatorBuilder builder) {
//        return builder.routes()
//                .route("auth-service", r -> r.path("/auth/**").uri("lb://auth-service"))
//                .route("commentService", r -> r.path("/comments/**").filters(f -> f.filter(filter)).uri("lb://commentService"))
//                .route("posts-service", r -> r.path("/posts/**").filters(f -> f.filter(filter)).uri("http://localhost:8090"))
////                .route("hello", r -> r.path("/hello/**").filters(f -> f.filter(filter)).uri("lb://hello"))
//                .build();
//    }
}

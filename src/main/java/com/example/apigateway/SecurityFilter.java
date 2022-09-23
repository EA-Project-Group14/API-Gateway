//package com.example.apigateway;
//
//import org.springframework.cloud.gateway.filter.GatewayFilterChain;
//import org.springframework.cloud.gateway.filter.GlobalFilter;
//import org.springframework.stereotype.Component;
//import org.springframework.web.server.ServerWebExchange;
//import reactor.core.publisher.Mono;
//
//@Component
//public class SecurityFilter implements GlobalFilter {
//
//    @Override
//    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
//        if (!exchange.getRequest().getHeaders().containsKey("Authorization")) {
//            exchange.getRequest().getBody()
//        }
//        return chain.filter(exchange)
//    }
//}

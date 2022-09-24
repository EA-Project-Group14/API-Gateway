package com.example.apigateway;

import com.example.apigateway.exception.JwtTokenMalformedException;
import com.example.apigateway.exception.JwtTokenMissingException;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Predicate;

@Component
public class MyFilterFactory extends AbstractGatewayFilterFactory<MyFilterFactory.Config> {

    @Autowired
    private JwtUtil jwtUtil;

    public MyFilterFactory( JwtUtil jwtUtil) {
        super(Config.class);
        this.jwtUtil = jwtUtil;
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            ServerHttpRequest request = (ServerHttpRequest) exchange.getRequest();

            final List<String> apiEndpoints = List.of("/register", "/login","/authenticate");

            Predicate<ServerHttpRequest> isApiSecured = r -> apiEndpoints.stream()
                    .noneMatch(uri -> r.getURI().getPath().contains(uri));

            if (isApiSecured.test(request)) {
                if (!request.getHeaders().containsKey("Authorization")) {
                    ServerHttpResponse response = exchange.getResponse();
                    response.setStatusCode(HttpStatus.UNAUTHORIZED);

                    return response.setComplete();
                }

                final String token = request.getHeaders().getOrEmpty("Authorization").get(0);

                try {
                    jwtUtil.validateToken(token);
                } catch (JwtTokenMalformedException | JwtTokenMissingException e) {
                    // e.printStackTrace();

                    ServerHttpResponse response = exchange.getResponse();
                    response.setStatusCode(HttpStatus.BAD_REQUEST);

                    return response.setComplete();
                }

                Claims claims = jwtUtil.getClaims(token);
                exchange.getRequest().mutate().header("id", String.valueOf(claims.get("id"))).build();
            }

            return chain.filter(exchange);
        };
    }


    public static class Config{

    }
}

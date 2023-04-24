package com.rasitesdmr.apigateway.config;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
public class CorsPostFilter implements GatewayFilter {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        HttpHeaders headers = exchange.getResponse().getHeaders();

        if (exchange.getRequest().getMethod() == HttpMethod.POST || exchange.getRequest().getMethod() == HttpMethod.OPTIONS) {
            if (!headers.containsKey("Access-Control-Allow-Origin")) {
                headers.add("Access-Control-Allow-Origin", "http://localhost:3000");
            }

            if (!headers.containsKey("Access-Control-Allow-Methods")) {
                headers.add("Access-Control-Allow-Methods", "GET,POST,PUT,DELETE,OPTIONS,HEAD");
            }

            if (!headers.containsKey("Access-Control-Allow-Headers")) {
                headers.add("Access-Control-Allow-Headers", "*");
            }

            if (!headers.containsKey("Access-Control-Allow-Credentials")) {
                headers.add("Access-Control-Allow-Credentials", "true");
            }

            if (!headers.containsKey("Access-Control-Max-Age")) {
                headers.add("Access-Control-Max-Age", "36000");
            }
        }

        if (exchange.getRequest().getMethod() == HttpMethod.OPTIONS) {
            exchange.getResponse().setStatusCode(HttpStatus.OK);
            return Mono.empty();
        }

        return chain.filter(exchange);
    }
}

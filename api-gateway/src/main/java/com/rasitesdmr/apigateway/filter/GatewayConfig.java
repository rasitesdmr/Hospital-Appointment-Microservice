package com.rasitesdmr.apigateway.filter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {

    @Bean
    public ErrorResponseGlobalFilter errorResponseGlobalFilter() {
        return new ErrorResponseGlobalFilter();
    }
}

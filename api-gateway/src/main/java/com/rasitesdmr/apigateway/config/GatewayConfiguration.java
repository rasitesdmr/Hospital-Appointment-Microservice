package com.rasitesdmr.apigateway.config;

import com.rasitesdmr.apigateway.filter.AuthenticationFilter;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;


@Configuration
public class GatewayConfiguration {

    @Bean
    public CorsWebFilter corsWebFilter() {

        CorsConfiguration corsConfig = new CorsConfiguration();
        corsConfig.setAllowCredentials(true);
        corsConfig.addAllowedOriginPattern("*");
        corsConfig.addAllowedHeader("*");
        corsConfig.addAllowedMethod("*");
        corsConfig.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfig);

        return new CorsWebFilter(source);
    }


    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder, AuthenticationFilter authenticationFilter) {

        AuthenticationFilter.Config config = new AuthenticationFilter.Config();
        GatewayFilter authFilter = authenticationFilter.apply(config);
        CorsPostFilter corsPostFilter = new CorsPostFilter();


        return builder.routes()
                // API Gateway -> JWT Service
                .route("proxy-security-service", r -> r.path("/proxy-auth/**")
                        .filters(f -> f.rewritePath("/proxy-auth/(?<segment>.*)", "/auth/${segment}")
                                .dedupeResponseHeader("Access-Control-Allow-Origin", "RETAIN_UNIQUE")
                                .dedupeResponseHeader("Access-Control-Allow-Credentials", "RETAIN_UNIQUE"))
                        .uri("lb://SECURITY-SERVICE"))

                // API Gateway -> Hospital Service
                .route("proxy-hospital-service-1", r -> r.path("/proxy-city/**")
                        .filters(f -> f.rewritePath("/proxy-city/(?<segment>.*)", "/city/${segment}")
                                .dedupeResponseHeader("Access-Control-Allow-Origin", "RETAIN_UNIQUE")
                                .dedupeResponseHeader("Access-Control-Allow-Credentials", "RETAIN_UNIQUE")
                                .filter(authFilter).filter(corsPostFilter))
                        .uri("lb://HOSPITAL-SERVICE"))

                .route("proxy-hospital-service-2", r -> r.path("/proxy-clinic/**")
                        .filters(f -> f.rewritePath("/proxy-clinic/(?<segment>.*)", "/clinic/${segment}")
                                .dedupeResponseHeader("Access-Control-Allow-Origin", "RETAIN_UNIQUE")
                                .dedupeResponseHeader("Access-Control-Allow-Credentials", "RETAIN_UNIQUE")
                                .filter(authFilter).filter(corsPostFilter))
                        .uri("lb://HOSPITAL-SERVICE"))

                .route("proxy-hospital-service-3", r -> r.path("/proxy-hospitalAndClinic/**")
                        .filters(f -> f.rewritePath("/proxy-hospitalAndClinic/(?<segment>.*)", "/hospitalAndClinic/${segment}")
                                .dedupeResponseHeader("Access-Control-Allow-Origin", "RETAIN_UNIQUE")
                                .dedupeResponseHeader("Access-Control-Allow-Credentials", "RETAIN_UNIQUE")
                                .filter(authFilter).filter(corsPostFilter))
                        .uri("lb://HOSPITAL-SERVICE"))

                .route("proxy-hospital-service-4", r -> r.path("/proxy-doctor/**")
                        .filters(f -> f.rewritePath("/proxy-doctor/(?<segment>.*)", "/doctor/${segment}")
                                .dedupeResponseHeader("Access-Control-Allow-Origin", "RETAIN_UNIQUE")
                                .dedupeResponseHeader("Access-Control-Allow-Credentials", "RETAIN_UNIQUE")
                                .filter(authFilter).filter(corsPostFilter))
                        .uri("lb://HOSPITAL-SERVICE"))


                .route("proxy-hospital-service-5", r -> r.path("/proxy-hospital/**")
                        .filters(f -> f.rewritePath("/proxy-hospital/(?<segment>.*)", "/hospital/${segment}")
                                .dedupeResponseHeader("Access-Control-Allow-Origin", "RETAIN_UNIQUE")
                                .dedupeResponseHeader("Access-Control-Allow-Credentials", "RETAIN_UNIQUE")
                                .filter(authFilter).filter(corsPostFilter))
                        .uri("lb://HOSPITAL-SERVICE"))

                // API Gateway -> Appointment Service
                .route("proxy-appointment-service", r -> r.path("/proxy-appointment/**")
                        .filters(f -> f.rewritePath("/proxy-appointment/(?<segment>.*)", "/appointment/${segment}")
                                .dedupeResponseHeader("Access-Control-Allow-Origin", "RETAIN_UNIQUE")
                                .dedupeResponseHeader("Access-Control-Allow-Credentials", "RETAIN_UNIQUE")
                                .filter(authFilter).filter(corsPostFilter))
                        .uri("lb://APPOINTMENT-SERVICE"))

                // API Gateway -> Excel Service
                .route("proxy-excel-service", r -> r.path("/proxy-excel/**")
                        .filters(f -> f.rewritePath("/proxy-excel/(?<segment>.*)", "/excel/${segment}")
                                .dedupeResponseHeader("Access-Control-Allow-Origin", "RETAIN_UNIQUE")
                                .dedupeResponseHeader("Access-Control-Allow-Credentials", "RETAIN_UNIQUE")
                                .filter(authFilter).filter(corsPostFilter))
                        .uri("lb://EXCEL-SERVICE"))

                // API Gateway -> Client
                .route("client", r -> r.path("/client/**", "/security/**")

                        .uri("lb://CLIENT"))
                .build();
    }
}

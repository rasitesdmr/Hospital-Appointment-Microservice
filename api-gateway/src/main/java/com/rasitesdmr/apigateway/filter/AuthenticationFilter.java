package com.rasitesdmr.apigateway.filter;

import com.rasitesdmr.apigateway.exception.UnauthorizedException;
import com.rasitesdmr.apigateway.util.JwtUtil;
import jakarta.ws.rs.core.MultivaluedHashMap;
import jakarta.ws.rs.core.MultivaluedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config> {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private RouteValidator routeValidator;

    private final MultivaluedMap<String, String> requestPathControlMap;

    public AuthenticationFilter() {
        super(Config.class);
        this.requestPathControlMap = new MultivaluedHashMap<>();
//        requestPathControlMap.add("/city/createCity", "ROLE_ADMIN");
//        requestPathControlMap.add("/city/getCityList", "ROLE_ADMIN");
//        requestPathControlMap.add("/city/getCityList", "ROLE_USER");
//        requestPathControlMap.add("/clinic/createClinic", "ROLE_ADMIN");
//        requestPathControlMap.add("/clinic/getClinicList", "ROLE_ADMIN");
//        requestPathControlMap.add("/clinic/getClinicList", "ROLE_USER");
//        requestPathControlMap.add("/doctor/createDoctor", "ROLE_ADMIN");
//        requestPathControlMap.add("/doctor/getDoctorList", "ROLE_ADMIN");
//        requestPathControlMap.add("/doctor/getDoctorList", "ROLE_USER");
//        requestPathControlMap.add("/hospitalAndClinic/hospitalAndClinicMatching", "ROLE_ADMIN");
//        requestPathControlMap.add("/hospital/createHospital", "ROLE_ADMIN");
//        requestPathControlMap.add("/hospital/getHospitalList", "ROLE_ADMIN");
//        requestPathControlMap.add("/hospital/getHospitalList", "ROLE_USER");
//        requestPathControlMap.add("/appointment/createAppointment", "ROLE_USER");
//        requestPathControlMap.add("/excel/convertExcelToModel", "ROLE_ADMIN");
//        requestPathControlMap.add("/appointment/getAllCityList", "ROLE_USER");
//        requestPathControlMap.add("/appointment/getHospitalsByCityName", "ROLE_USER");
//        requestPathControlMap.add("/appointment/getClinicsByHospitalName", "ROLE_USER");
//        requestPathControlMap.add("/appointment/getDoctorsByClinicName", "ROLE_USER");

    }


    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            if (routeValidator.isSecured.test(exchange.getRequest())) {
                if (!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                    return Mono.error(new UnauthorizedException("Eksik yetkilendirme başlığı"));
                }
                String authHeader = exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
                if (authHeader != null && authHeader.startsWith("Bearer ")) {
                    authHeader = authHeader.substring(7);
                }
                try {
                    jwtUtil.validateToken(authHeader);
                } catch (Exception exception) {
                    return Mono.error(new UnauthorizedException("Uygulamaya Yetkisiz Erişim"));
                }

                var identityNumber = jwtUtil.getIdentityNumberFromToken(authHeader);
                List<String> roles = jwtUtil.getRolesFromToken(authHeader);
                String role = roles.stream().findFirst().get();
                List<String> key = requestPathControlMap.get(exchange.getRequest().getPath().toString());

                if (key == null) {
                    return Mono.error(new UnauthorizedException("Yalnızca admin yetkisi olan kullanıcılar bu işlemi gerçekleştirebilir"));
                }
                if (!key.contains(role)) {
                    return Mono.error(new UnauthorizedException("Yalnızca admin yetkisi olan kullanıcılar bu işlemi gerçekleştirebilir"));
                }

                ServerHttpRequest modifiedRequest = exchange.getRequest().mutate()
                        .header("identityNumber", identityNumber)
                        .build();

                ServerWebExchange modifiedExchange = exchange.mutate()
                        .request(modifiedRequest)
                        .build();

                return chain.filter(modifiedExchange);

            }

            return chain.filter(exchange);
        };


    }


    public static class Config {

    }


}





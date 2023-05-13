package com.rasitesdmr.apigateway.filter;

import com.rasitesdmr.apigateway.exception.UnauthorizedException;
import com.rasitesdmr.apigateway.util.JwtUtil;
import jakarta.ws.rs.core.MultivaluedHashMap;
import jakarta.ws.rs.core.MultivaluedMap;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config> {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private RouteValidator routeValidator;

    private final MultivaluedMap<String, String> requestPathControlMap;

    public AuthenticationFilter() {
        super(Config.class);
        this.requestPathControlMap = new MultivaluedHashMap<>();
        requestPathControlMap.add("/city/createCity", "ROLE_ADMIN");
        requestPathControlMap.add("/clinic/createClinic", "ROLE_ADMIN");
        requestPathControlMap.add("/doctor/createDoctor", "ROLE_ADMIN");
        requestPathControlMap.add("/hospital/createHospital", "ROLE_ADMIN");
        requestPathControlMap.add("/doctorClinicRelationship/associateDoctorWithClinic", "ROLE_ADMIN");
        requestPathControlMap.add("/doctorHospitalRelationship/associateDoctorWithHospital", "ROLE_ADMIN");
        requestPathControlMap.add("/hospitalClinicRelationship/associateHospitalWithClinic", "ROLE_ADMIN");
        requestPathControlMap.add("/excel/processSingleExcelFile", "ROLE_ADMIN");

        requestPathControlMap.addAll("/city/getCityList","ROLE_PATIENT");
        requestPathControlMap.addAll("/hospital/getHospitalListByCityName","ROLE_PATIENT");
        requestPathControlMap.addAll("/clinic/getClinicListByHospitalName","ROLE_PATIENT");
        requestPathControlMap.addAll("/doctor/getDoctorListByClinicName","ROLE_PATIENT");


        requestPathControlMap.addAll("/appointment/appointmentMakingProcess","ROLE_PATIENT","ROLE_DOCTOR");



//        requestPathControlMap.add("/appointment/getAllCityList", "ROLE_USER");
//        requestPathControlMap.add("/appointment/getHospitalsByCityName", "ROLE_USER");
//        requestPathControlMap.add("/appointment/getClinicsByHospitalName", "ROLE_USER");
//        requestPathControlMap.add("/appointment/getDoctorsByClinicName", "ROLE_USER");

    }


    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            if (routeValidator.isSecured.test(exchange.getRequest())) {
                String methodName = new Object() {}.getClass().getEnclosingMethod().getName();

                if (!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                    log.error("[Metot : {}] - Eksik yetkilendirme başlığı",methodName);
                    return Mono.error(new UnauthorizedException("Eksik yetkilendirme başlığı"));
                }
                String authHeader = exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
                if (authHeader != null && authHeader.startsWith("Bearer ")) {
                    authHeader = authHeader.substring(7);
                }
                try {
                    jwtUtil.validateToken(authHeader);
                } catch (Exception exception) {
                    log.error("[Metot : {}] - Uygulamaya Yetkisiz Erişim",methodName);
                    return Mono.error(new UnauthorizedException("Uygulamaya Yetkisiz Erişim"));
                }

                var identityNumber = jwtUtil.getIdentityNumberFromToken(authHeader);
                List<String> roles = jwtUtil.getRolesFromToken(authHeader);

                boolean matchStatus = false;

                for (String role : roles) {
                    List<String> key = requestPathControlMap.get(exchange.getRequest().getPath().toString());
                    if (key.contains(role)) {
                        matchStatus = true;
                        break;
                    }
                }

                if (!matchStatus) {
                    log.error("[Metot : {}] - Rol yetki hatası ",methodName);
                    return Mono.error(new UnauthorizedException("Rol yetki hatası"));
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





package com.rasitesdmr.securityservice.config;

import com.rasitesdmr.securityservice.repository.UserRepository;
import com.rasitesdmr.securityservice.userDetails.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
public class SecurityConfig {

    /**
     * CustomerUserDetailsService nesnesini oluşturarak UserRepository'yi kullanır.
     * Bu bean, kullanıcı detaylarını yüklemem içi UserRepository kullanır.
     *
     * @param userRepository UserRepository nesnesi.
     * @return CustomerUserDetailsService ile yapılandırılmış UserDetailsService
     */
    @Bean
    public UserDetailsService userDetailsService(UserRepository userRepository) {
        return new CustomUserDetailsService(userRepository);
    }

    /**
     * Güvenlik filtre zincirini oluşturur ve yapılandırır.
     * Bu bean, uygulamanın güvenlik filtersini yapılandırarak istekleri izin verilen yollara yönlendirir.
     *
     * @param http HttpSecurity nesnesi
     * @return Yapılandırılmış SecurityFilterChain
     * @throws Exception
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.cors().and().csrf().disable()
                .authorizeHttpRequests()
                .requestMatchers("/auth/patientRegister","/auth/doctorRegister", "/auth/login").permitAll()
                .and()
                .build();
    }

    /**
     * BCryptPasswordEncoder nesnesi oluşturarak şifrelerin güvenli kodlanması ve kod çözülmesi sağlar
     *
     * @return Yeni BCryptPasswordEncoder
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * AuthenticationProvider nesnesi oluşturularak , kullanıcı kimlik doğrulama işlemleri gerçekleştirilir.
     *
     * @param userDetailsService UserDetailsService nesnesi
     * @return Yapılandırılmış DaoAuthenticationProvider
     */
    @Bean
    public AuthenticationProvider authenticationProvider(UserDetailsService userDetailsService) {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

    /**
     * AuthenticationManager nesnesi alarak kimlik doğrulama işlemleri yönetilir.
     *
     * @param config AuthenticationConfiguration nesnesi
     * @return AuthenticationManager
     * @throws Exception
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}

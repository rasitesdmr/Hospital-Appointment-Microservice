package com.rasitesdmr.securityservice.userDetails;

import com.rasitesdmr.securityservice.repository.UserRepository;
import kafka.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String identityNumber) throws UsernameNotFoundException {
        User user = userRepository.getByIdentityNumber(identityNumber);
        if (user == null){
            String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
            log.error("[Metot: {}] - Geçersiz kullanıcı adı: {}", methodName, identityNumber);
            throw new UsernameNotFoundException("Geçersiz kullanıcı adı");
        }
        return new CustomUserDetails(user);
    }
}

package com.rasitesdmr.securityservice.loader;


import com.rasitesdmr.securityservice.enums.ERole;
import com.rasitesdmr.securityservice.repository.RoleRepository;
import com.rasitesdmr.securityservice.repository.UserRepository;
import kafka.model.Role;
import kafka.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Slf4j
public class DbLoader implements ApplicationRunner {
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    UserRepository userRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {

        if (roleRepository.count() == 0)
            Arrays.stream(ERole.values()).map(role -> new Role(role)).forEach(role -> roleRepository.save(role));
        if (userRepository.count() == 0) {

            BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

            userRepository.save(new User("11111111111", "Admin", "admin", "admin@admin.com", bCryptPasswordEncoder.encode("Admin"), List.of(roleRepository.findByName(ERole.ROLE_ADMIN))));

        }

    }
}

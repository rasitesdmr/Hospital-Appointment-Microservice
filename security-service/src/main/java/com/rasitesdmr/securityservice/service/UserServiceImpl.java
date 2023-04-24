package com.rasitesdmr.securityservice.service;

import com.rasitesdmr.securityservice.enums.ERole;
import com.rasitesdmr.securityservice.exception.AlreadyAvailableException;
import com.rasitesdmr.securityservice.exception.InvalidException;
import com.rasitesdmr.securityservice.exception.RegistrationException;
import com.rasitesdmr.securityservice.producer.KafkaProducer;
import com.rasitesdmr.securityservice.repository.RoleRepository;
import com.rasitesdmr.securityservice.repository.UserRepository;
import com.rasitesdmr.securityservice.util.RegexUtils;
import com.rasitesdmr.securityservice.util.TokenCreation;
import kafka.model.Role;
import kafka.model.User;
import kafka.model.dto.request.UserKafkaRequest;
import kafka.model.dto.request.UserLoginRequest;
import kafka.model.dto.request.UserRequest;
import kafka.model.dto.response.UserLoginResponse;
import kafka.model.dto.response.UserResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final KafkaProducer kafkaProducer;
    private final AuthenticationManager authenticationManager;

    private final TokenCreation tokenCreation;

    @Override
    public UserResponse createUser(UserRequest userRequest) {
        String methodName = new Object() {}.getClass().getEnclosingMethod().getName();
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        boolean isIdentityNumberPresent = userRepository.existsById(userRequest.getIdentityNumber());
        if (isIdentityNumberPresent) {
            log.error("[Metot: {}] - Kimlik numarasına sahip kullanıcı zaten mevcut : {}", methodName, userRequest.getIdentityNumber());
            throw new AlreadyAvailableException(userRequest.getIdentityNumber() + " kimlik numarasına sahip kullanıcı zaten mevcut !!!");
        }
        boolean isEmailAddressPresent = userRepository.existsByEmail(userRequest.getEmail());
        if (isEmailAddressPresent) {
            log.error("[Metot: {}] - Mail adresi zaten mevcut : {}", methodName, userRequest.getEmail());
            throw new AlreadyAvailableException(userRequest.getEmail() + " mail adresi zaten mevcut !!!");
        }
        Role role = roleRepository.findByName(ERole.ROLE_PATIENT);
        User user = new User();
        try{
            user.setIdentityNumber(RegexUtils.identityNumberNo(userRequest.getIdentityNumber()));
            user.setFirstName(userRequest.getFirstName());
            user.setLastName(userRequest.getLastName());
            user.setEmail(RegexUtils.emailRegex(userRequest.getEmail()));
            user.setPassword(bCryptPasswordEncoder.encode(RegexUtils.passwordRegex(userRequest.getPassword())));
            user.setRoleList(Collections.singletonList(role));
            userRepository.save(user);
        }catch (Exception exception){
            log.error("[Metot: {}] - Kullanıcı kayıt sırasında hata oluştu : {}", methodName ,exception.getMessage());
            throw new RegistrationException("Kullanıcı kayıt sırasında hata oluştu : " + exception.getMessage());
        }
        UserKafkaRequest userKafkaRequest = UserKafkaRequest.builder()
                .identityNumber(user.getIdentityNumber())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .build();
        kafkaProducer.sendUserToKafka(userKafkaRequest);
        log.info("[Metot: {}] - {} kimlik numarasına sahip kullanıcı bilgileri kafka ile kuyruğa gönderildi",methodName,userKafkaRequest.getIdentityNumber());

        return UserResponse.builder()
                .identityNumber(user.getIdentityNumber())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .password(user.getPassword())
                .build();
    }

    @Override
    public UserLoginResponse userLogin(UserLoginRequest userLoginRequest) {
        String methodName = new Object() {}.getClass().getEnclosingMethod().getName();
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        boolean isIdentityNumberPresent = userRepository.existsById(userLoginRequest.getIdentityNumber());
        if (!isIdentityNumberPresent){
            log.error("[Metot : {}] - Geçersiz kimlik numarası : {}" , methodName,userLoginRequest.getIdentityNumber());
            throw new InvalidException(" Geçersiz kimlik numarası : " + userLoginRequest.getIdentityNumber());
        }
        User user = userRepository.findById(userLoginRequest.getIdentityNumber()).get();
        boolean isPasswordMatch = passwordEncoder.matches(userLoginRequest.getPassword(),user.getPassword());
        if (!isPasswordMatch){
            log.error("[Metot : {}] - Şifre yanlış : {}" , methodName,userLoginRequest.getPassword());
            throw new InvalidException(" Şifre yanlış : " + userLoginRequest.getPassword());
        }
        try{
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userLoginRequest.getIdentityNumber(),userLoginRequest.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }catch (Exception exception){
            log.error("[Metot : {}] - Kimlik doğrulama başarısız",methodName);
            throw  new InvalidException("Kimlik doğrulama başarısız");
        }
        String token = tokenCreation.generateAccessToken(user);

        return UserLoginResponse.builder()
                .email(user.getEmail())
                .token(token)
                .build();
    }
}

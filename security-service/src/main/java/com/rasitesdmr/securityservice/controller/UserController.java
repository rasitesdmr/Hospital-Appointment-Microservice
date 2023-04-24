package com.rasitesdmr.securityservice.controller;


import com.rasitesdmr.securityservice.service.UserService;
import kafka.model.User;
import kafka.model.dto.request.UserLoginRequest;
import kafka.model.dto.request.UserRequest;
import kafka.model.dto.response.UserLoginResponse;
import kafka.model.dto.response.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<UserResponse> createUser(@RequestBody UserRequest userRequest) {
        return new ResponseEntity<>(userService.createUser(userRequest), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<UserLoginResponse> userLogin(@RequestBody UserLoginRequest userLoginRequest) {
        return new ResponseEntity<>(userService.userLogin(userLoginRequest),HttpStatus.OK);
    }

}

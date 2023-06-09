package com.rasitesdmr.securityservice.service;

import kafka.model.dto.request.UserLoginRequest;
import kafka.model.dto.request.UserRequest;
import kafka.model.dto.response.UserLoginResponse;
import kafka.model.dto.response.UserResponse;

public interface UserService {

    UserResponse createPatientUser(UserRequest userRequest);
    UserResponse createDoctorUser(UserRequest userRequest);

    UserLoginResponse userLogin(UserLoginRequest userLoginRequest);

}

package com.huisa.service;

import com.huisa.dto.LoginRequestDto;
import com.huisa.dto.LoginResponseDto;
import com.huisa.dto.UserRequestDto;
import com.huisa.dto.UserResponseDto;

public interface AuthService {

    LoginResponseDto login(LoginRequestDto loginRequestDto);

    UserResponseDto register(UserRequestDto request);
}

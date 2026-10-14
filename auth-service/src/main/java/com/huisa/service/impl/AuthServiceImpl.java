package com.huisa.service.impl;


import com.huisa.config.jwt.JwtService;
import com.huisa.dto.LoginRequestDto;
import com.huisa.dto.LoginResponseDto;
import com.huisa.dto.UserRequestDto;
import com.huisa.dto.UserResponseDto;
import com.huisa.service.AuthService;
import com.huisa.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserService userService;

    @Override
    public LoginResponseDto login(LoginRequestDto loginRequestDto) {
        // TODO: Implement login logic
        // For example, you might check the user's credentials and generate a JWT token
      Authentication authentication =
              authenticationManager.authenticate(
                      new UsernamePasswordAuthenticationToken(
                              loginRequestDto.email(),
                              loginRequestDto.password()
                      )
              );
        UserDetails userDetails =
                (UserDetails) authentication.getPrincipal();
        String token =  jwtService.generateToken(userDetails);
        return LoginResponseDto.builder()
                .token(token)
                .type("Bearer")
                .email(userDetails.getUsername())
                .build();
    }

    @Override
    public UserResponseDto register(UserRequestDto request) {
        return userService.create(request);
    }
}

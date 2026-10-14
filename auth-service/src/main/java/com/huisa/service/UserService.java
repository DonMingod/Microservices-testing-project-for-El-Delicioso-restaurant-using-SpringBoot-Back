package com.huisa.service;

import com.huisa.dto.UserRequestDto;
import com.huisa.dto.UserResponseDto;

import java.util.List;

public interface UserService {

    UserResponseDto create (UserRequestDto request);

    UserResponseDto findById (Long id);

    List<UserResponseDto> findAll ();

    UserResponseDto update (Long id, UserRequestDto request);

    void delete (Long id);

}

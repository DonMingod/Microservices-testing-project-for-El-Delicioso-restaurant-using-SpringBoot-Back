package com.huisa.util;

import com.huisa.dto.UserRequestDto;
import com.huisa.dto.UserResponseDto;
import com.huisa.model.User;
import org.mapstruct.Mapper;
import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

  // User toEntity(UserRequestDto request);

    UserResponseDto toDto(User user);

    List<UserResponseDto> toDtoList(List<User> users);
}

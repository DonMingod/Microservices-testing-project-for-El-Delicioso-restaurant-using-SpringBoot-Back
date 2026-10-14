package com.huisa.service.impl;

import com.huisa.dto.UserRequestDto;
import com.huisa.dto.UserResponseDto;
import com.huisa.exception.BadRequestException;
import com.huisa.exception.ResourceNotFoundException;
import com.huisa.model.Role;
import com.huisa.model.User;
import com.huisa.repository.RoleRepository;
import com.huisa.repository.UserRepository;
import com.huisa.service.UserService;
import com.huisa.util.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserResponseDto create(UserRequestDto request) {

        if (userRepository.existsByEmail(request.email())) {
            throw new BadRequestException(
                    "Email already exists: " + request.email()
            );
        }

        Set<Role> roles = getRoles(request.roleIds());

        User user = User.builder()
                .email(request.email())
                .name(request.name())
                .password(passwordEncoder.encode(request.password()))
                .enabled(
                        request.enabled() != null
                                ? request.enabled()
                                : true
                )
                .roles(roles)
                .build();

        User savedUser = userRepository.save(user);

        return userMapper.toDto(savedUser);
    }

    @Override
    public UserResponseDto findById(Long id) {

        User user = userRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "User not found with id: " + id
                        )
                );

        return userMapper.toDto(user);
    }

    @Override
    public List<UserResponseDto> findAll() {

        List<User> users = userRepository.findAll();

        return userMapper.toDtoList(users);
    }

    @Override
    public UserResponseDto update(
            Long id,
            UserRequestDto request
    ) {

        User user = userRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "User not found with id: " + id
                        )
                );

        if (!user.getEmail().equals(request.email())
                && userRepository.existsByEmail(request.email())) {

            throw new BadRequestException(
                    "Email already exists: " + request.email()
            );
        }

        user.setEmail(request.email());
        user.setName(request.name());

        if (request.password() != null
                && !request.password().isBlank()) {

            user.setPassword(
                    passwordEncoder.encode(request.password())
            );
        }

        if (request.enabled() != null) {
            user.setEnabled(request.enabled());
        }

        if (request.roleIds() != null) {
            user.setRoles(
                    getRoles(request.roleIds())
            );
        }

        User updatedUser = userRepository.save(user);

        return userMapper.toDto(updatedUser);
    }

    @Override
    public void delete(Long id) {

        if (!userRepository.existsById(id)) {
            throw new ResourceNotFoundException(
                    "User not found with id: " + id
            );
        }

        userRepository.deleteById(id);
    }

    private Set<Role> getRoles(Set<Long> roleIds) {

        if (roleIds == null || roleIds.isEmpty()) {
            return new HashSet<>();
        }

        List<Role> roles = roleRepository.findAllById(roleIds);

        if (roles.size() != roleIds.size()) {
            throw new ResourceNotFoundException(
                    "One or more roles were not found"
            );
        }

        return new HashSet<>(roles);
    }
}


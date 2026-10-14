package com.huisa.service.impl;

import com.huisa.dto.RoleRequestDto;
import com.huisa.dto.RoleResponseDto;
import com.huisa.exception.ResourceNotFoundException;
import com.huisa.model.Role;
import com.huisa.repository.RoleRepository;
import com.huisa.service.RoleService;
import com.huisa.util.RoleMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;


@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;
    private final RoleMapper roleMapper;

    @Override
    public RoleResponseDto create(RoleRequestDto request) {
        Role role =roleMapper.toEntity(request);
        Role savedRole= roleRepository.save(role);
        return roleMapper.toDto(savedRole);
    }

    @Override
    public RoleResponseDto findById(Long id) {
        Role role = roleRepository.findById(id).orElseThrow(()->
                new ResourceNotFoundException("Role not found with id:"+ id));
        return roleMapper .toDto(role);
    }

    @Override
    public List<RoleResponseDto> findAll() {
        List<Role> roles = roleRepository.findAll();
        return roleMapper.toDtoList(roles);
    }

    @Override
    public RoleResponseDto update(Long id, RoleRequestDto request) {
        Role role = roleRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Role not found with id:"+ id));
        role.setName(request.name());
        role.setDescription(request.description());
        Role updateRole = roleRepository.save(role);

        return roleMapper.toDto(updateRole);
    }

    @Override
    public void delete(Long id) {
        if (!roleRepository.existsById(id)) {
            throw new ResourceNotFoundException(
                    "Role not found with id: " + id
            );
        }

        roleRepository.deleteById(id);
    }
}

package com.huisa.service;

import com.huisa.dto.RoleRequestDto;
import com.huisa.dto.RoleResponseDto;

import java.util.List;

public interface RoleService {


    RoleResponseDto create (RoleRequestDto request);

    RoleResponseDto findById (Long id);

    List<RoleResponseDto> findAll ();

    RoleResponseDto update (Long id, RoleRequestDto request);

    void delete (Long id);

}

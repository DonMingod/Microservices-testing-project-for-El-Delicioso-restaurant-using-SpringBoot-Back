package com.huisa.repository;

import com.huisa.model.Role;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository  extends JpaRepository<@NonNull Role, @NonNull Long> {

}

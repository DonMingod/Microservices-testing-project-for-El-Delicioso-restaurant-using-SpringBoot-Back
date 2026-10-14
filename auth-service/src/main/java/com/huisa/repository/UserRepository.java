package com.huisa.repository;

import com.huisa.model.User;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository  extends JpaRepository <@NonNull User, @NonNull Long> {

    Optional<@NonNull User> findByEmail(@NonNull String email);

    boolean existsByEmail(@NonNull String email);
}

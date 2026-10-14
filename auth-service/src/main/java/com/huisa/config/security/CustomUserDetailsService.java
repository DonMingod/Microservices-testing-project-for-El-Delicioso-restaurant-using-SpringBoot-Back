package com.huisa.config.security;

import com.huisa.model.Role;
import com.huisa.model.User;
import com.huisa.repository.UserRepository;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // TODO Auto-generated method stub
      User user = userRepository.findByEmail(email)
              .orElseThrow(() ->
                      new UsernameNotFoundException("User not found with email: " + email
                      )
              );
        return org.springframework.security.core.userdetails.User
                .withUsername(user.getEmail())
                .password(user.getPassword())
                .authorities(
                        user.getRoles()
                                .stream()
                                .map(Role::getName)
                                .map(SimpleGrantedAuthority::new)
                                .collect(Collectors.toSet())
                )
                .disabled(!user.getEnabled())
                .build();
    }
}

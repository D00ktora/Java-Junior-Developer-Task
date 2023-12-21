package com.markovskisolutions.JJDT.service;

import com.markovskisolutions.JJDT.model.Role;
import com.markovskisolutions.JJDT.model.UserEntity;
import com.markovskisolutions.JJDT.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class JJDTSecurityService implements UserDetailsService {
    private final UserRepository userRepository;

    public JJDTSecurityService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email)
                .map(JJDTSecurityService::map)
                .orElseThrow( () -> new UsernameNotFoundException("User " + email + " not found!"));
    }

    private static UserDetails map(UserEntity user) {
        return User.withUsername(user.getEmail())
                .password(user.getPassword())
                .authorities(user.getRoles().stream().map(JJDTSecurityService::mapRoles).toList())
                .build();
    }

    private static GrantedAuthority mapRoles(Role role) {
        return new SimpleGrantedAuthority("ROLE_" + role.getRoleEnum().name());
    }
}

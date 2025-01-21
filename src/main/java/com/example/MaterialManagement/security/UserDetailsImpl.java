package com.example.MaterialManagement.security;

import com.example.MaterialManagement.entity.User;
import com.example.MaterialManagement.enums.Department;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class UserDetailsImpl implements UserDetails {
    private Long id;
    private String username;
  
    private Department department;
    
    @JsonIgnore
    private String password;
    
    private Collection<? extends GrantedAuthority> authorities;

    public static UserDetailsImpl build(User user) {
        List<GrantedAuthority> authorities = List.of(
            new SimpleGrantedAuthority("ROLE_" + user.getDepartment().name())
        );

        return UserDetailsImpl.builder()
            .id(user.getId())
            .username(user.getUsername())
        
            .password(user.getPassword())
            .department(user.getDepartment())
            .authorities(authorities)
            .build();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
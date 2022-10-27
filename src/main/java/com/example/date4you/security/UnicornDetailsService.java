package com.example.date4you.security;


import com.example.date4you.entity.Unicorn;
import com.example.date4you.repository.UnicornRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

@Configuration
public class UnicornDetailsService implements UserDetailsService {
    @Autowired
    private UnicornRepository unicornRepository;
    @Override
    public UserDetails loadUserByUsername(String username ) throws UsernameNotFoundException {
        Optional<Unicorn> unicorn = unicornRepository.findByEmail( username );
        if( unicorn.isEmpty() ) {
            System.out.println("User not found " + username );
            throw new UsernameNotFoundException( "User not found " + username );
        }
        return new UnicornUser(unicorn.get().getEmail(), unicorn.get().getPassword(), Collections.emptyList(),unicorn.get().getProfile().getId(),unicorn.get().getProfile().getNickname());
    }


}

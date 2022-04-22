package com.example.expensetrackerapi.security;

import com.example.expensetrackerapi.entity.User;
import com.example.expensetrackerapi.repository.UserRepository;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User existingUser = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found for the email:" + email));

        return new org.springframework.security.core.userdetails.User(existingUser.getEmail(),
                existingUser.getPassword(),
                new ArrayList<>());
    }
}

package com.example.expensetrackerapi.service;

import com.example.expensetrackerapi.entity.User;
import com.example.expensetrackerapi.entity.UserModel;
import com.example.expensetrackerapi.exception.ItemAlreadyExistsException;
import com.example.expensetrackerapi.repository.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Override
    public User createUser(UserModel user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new ItemAlreadyExistsException("User is already register with email" + user.getEmail());
        }
        User newUser = new User();
        BeanUtils.copyProperties(user, newUser);
        newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
        return userRepository.save(newUser);
    }

    @Override
    public User readUser(Long id) {
        return userRepository.findById(id).orElseThrow(() ->
                new ItemAlreadyExistsException("User not found for the id:" + id));
    }

    @Override
    public User updateUser(UserModel user, Long id) {
        User existingUser = readUser(id);
        existingUser.setName(user.getName() != null ? user.getName() : existingUser.getName());
        existingUser.setEmail(user.getEmail() != null ? user.getEmail() : existingUser.getEmail());
        existingUser.setPassword(
                user.getPassword() != null ? passwordEncoder.encode(user.getPassword()) : existingUser.getPassword());
        existingUser.setAge(user.getAge() != null ? user.getAge() : existingUser.getAge());
        return userRepository.save(existingUser);
    }

    @Override
    public void deleteUser(Long id) {
        User user = readUser(id);
        userRepository.delete(user);
    }

    @Override
    public User getLoggedInUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found for the email" + email));
    }
}

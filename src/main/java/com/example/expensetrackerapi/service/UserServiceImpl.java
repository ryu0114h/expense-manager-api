package com.example.expensetrackerapi.service;

import com.example.expensetrackerapi.entity.User;
import com.example.expensetrackerapi.entity.UserModel;
import com.example.expensetrackerapi.repository.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User createUser(UserModel user) {
        User newUser = new User();
        BeanUtils.copyProperties(user, newUser);
        return userRepository.save(newUser);
    }
}

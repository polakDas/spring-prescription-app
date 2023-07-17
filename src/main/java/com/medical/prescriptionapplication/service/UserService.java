package com.medical.prescriptionapplication.service;

import org.springframework.stereotype.Service;

import com.medical.prescriptionapplication.model.User;
import com.medical.prescriptionapplication.repository.UserRepository;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getUserById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("User not found"));
    }

}

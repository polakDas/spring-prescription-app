package com.medical.prescriptionapplication.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.medical.prescriptionapplication.repository.UserRepository;

@Service
public class AppUserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("I'm in user details service.");

        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not valid"));
    }

}

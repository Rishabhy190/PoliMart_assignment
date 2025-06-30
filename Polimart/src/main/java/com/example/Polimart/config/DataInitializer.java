package com.example.Polimart.config;

import com.example.Polimart.model.User;
import com.example.Polimart.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        // Create a default user if none exists
        if (userRepository.count() == 0) {
            User defaultUser = new User();
            defaultUser.setEmail("test@example.com");
            defaultUser.setPassword(passwordEncoder.encode("test123"));
            defaultUser.setRole("USER");
            userRepository.save(defaultUser);
            System.out.println("Default user created: test@example.com / test123");
        }
    }
} 
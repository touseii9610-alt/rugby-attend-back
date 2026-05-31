package com.rugby.attend.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rugby.attend.entity.User;
import com.rugby.attend.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserRepository userRepository;

    @GetMapping("/api/users")
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @GetMapping("/api/users/current")
    public User getCurrentUser() {
        return userRepository
                .findByUserName("tousei")
                .orElseThrow();
    }
}
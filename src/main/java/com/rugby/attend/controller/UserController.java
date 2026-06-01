package com.rugby.attend.controller;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.rugby.attend.dto.LineLoginRequest;
import org.springframework.web.bind.annotation.RestController;

import com.rugby.attend.entity.User;
import com.rugby.attend.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@CrossOrigin(origins = { "http://localhost:5173",
        "https://rugby-attend-front.vercel.app" })
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

    @PostMapping("/api/users/line-login")
    public User lineLogin(@RequestBody LineLoginRequest request) {
        return userRepository.findByLineUserId(request.getLineUserId())
                .map(user -> {
                    user.setDisplayName(request.getDisplayName());
                    user.setPictureUrl(request.getPictureUrl());
                    return userRepository.save(user);
                })
                .orElseGet(() -> {
                    User user = new User();
                    user.setLineUserId(request.getLineUserId());
                    user.setUserName(request.getLineUserId());
                    user.setDisplayName(request.getDisplayName());
                    user.setPictureUrl(request.getPictureUrl());
                    user.setRole("MEMBER");
                    return userRepository.save(user);
                });
    }
}
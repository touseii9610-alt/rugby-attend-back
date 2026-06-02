package com.rugby.attend.controller;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.rugby.attend.dto.LineLoginRequest;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;

import com.rugby.attend.entity.Event;

import com.rugby.attend.entity.User;
import com.rugby.attend.repository.AttendanceRepository;
import com.rugby.attend.repository.EventRepository;
import com.rugby.attend.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@CrossOrigin(origins = { "http://localhost:5173",
        "https://rugby-attend-front.vercel.app" })
@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserRepository userRepository;
    private final AttendanceRepository attendanceRepository;
    private final EventRepository eventRepository;

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

    // mypage list
    @GetMapping("/api/users/{userName}/attending-events")
    public List<Event> getMyAttendingEvents(
            @PathVariable String userName) {

        return attendanceRepository
                .findByUserNameAndStatus(userName, "ATTEND")
                .stream()
                .map(attendance -> eventRepository.findById(attendance.getEventId()))
                .flatMap(Optional::stream)
                .filter(event -> !event.getStartDateTime().toLocalDate().isBefore(LocalDate.now()))
                .sorted(Comparator.comparing(Event::getStartDateTime))
                .toList();
    }
}
package com.rugby.attend.controller;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.rugby.attend.dto.AttendanceResponse;
import com.rugby.attend.entity.Attendance;
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
public class AttendanceController {

    private final AttendanceRepository attendanceRepository;
    private final EventRepository eventRepository;
    private final UserRepository userRepository;

    @GetMapping("/api/attendances/{eventId}")
    public List<AttendanceResponse> getAttendances(
            @PathVariable Long eventId) {

        return attendanceRepository.findByEventId(eventId)
                .stream()
                .map(attendance -> {
                    User user = userRepository
                            .findByUserName(attendance.getUserName())
                            .orElse(null);

                    return new AttendanceResponse(
                            attendance.getId(),
                            attendance.getEventId(),
                            attendance.getUserName(),
                            attendance.getStatus(),
                            attendance.getComment(),
                            attendance.getCreatedAt(),
                            user != null ? user.getDisplayName() : attendance.getUserName(),
                            user != null ? user.getPictureUrl() : null);
                })
                .toList();
    }

    @PostMapping("/api/attendances")
    public Attendance saveAttendance(
            @RequestBody Attendance request) {

        return attendanceRepository
                .findByEventIdAndUserName(
                        request.getEventId(),
                        request.getUserName())
                .map(existing -> {
                    existing.setStatus(request.getStatus());
                    existing.setComment(request.getComment());
                    existing.setCreatedAt(request.getCreatedAt());
                    return attendanceRepository.save(existing);
                })
                .orElseGet(() -> attendanceRepository.save(request));
    }

    @PostMapping("/api/events")
    public Event createEvent(@RequestBody Event event) {
        return eventRepository.save(event);
    }

}
package com.rugby.attend.controller;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.rugby.attend.entity.Attendance;
import com.rugby.attend.entity.Event;
import com.rugby.attend.repository.AttendanceRepository;
import com.rugby.attend.repository.EventRepository;

import lombok.RequiredArgsConstructor;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequiredArgsConstructor
public class AttendanceController {

    private final AttendanceRepository attendanceRepository;
    private final EventRepository eventRepository;

    @GetMapping("/api/attendances/{eventId}")
    public List<Attendance> getAttendances(
            @PathVariable Long eventId) {

        return attendanceRepository.findByEventId(
                eventId);
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
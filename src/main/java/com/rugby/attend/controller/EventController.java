package com.rugby.attend.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.rugby.attend.dto.EventResponse;
import com.rugby.attend.entity.Event;
import com.rugby.attend.repository.AttendanceRepository;
import com.rugby.attend.repository.EventRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;

@CrossOrigin(origins = { "http://localhost:5173",
                "https://rugby-attend-front.vercel.app" })
@RestController
@RequiredArgsConstructor
public class EventController {

        private final EventRepository eventRepository;
        private final AttendanceRepository attendanceRepository;

        @GetMapping("/api/events")
        public List<EventResponse> getEvents() {
                return eventRepository
                                .findByEventDateGreaterThanEqualOrderByEventDateAsc(
                                                LocalDate.now())
                                .stream()
                                .map(event -> new EventResponse(
                                                event.getId(),
                                                event.getTitle(),
                                                event.getEventDate(),
                                                event.getStartTime(),
                                                event.getEndTime(),
                                                event.getLocation(),
                                                event.getEventType(),
                                                attendanceRepository.countByEventIdAndStatus(event.getId(), "ATTEND"),
                                                attendanceRepository.countByEventIdAndStatus(event.getId(), "ABSENT"),
                                                attendanceRepository.countByEventIdAndStatus(event.getId(), "MAYBE")))
                                .toList();
        }

        @GetMapping("/api/events/{id}")
        public Event getEvent(
                        @PathVariable Long id) {

                return eventRepository
                                .findById(id)
                                .orElseThrow();
        }

        @PutMapping("/api/events/{id}")
        public Event updateEvent(
                        @PathVariable Long id,
                        @RequestBody Event request) {
                Event event = eventRepository.findById(id).orElseThrow();

                event.setTitle(request.getTitle());
                event.setEventDate(request.getEventDate());
                event.setStartTime(request.getStartTime());
                event.setEndTime(request.getEndTime());
                event.setLocation(request.getLocation());
                event.setEventType(request.getEventType());

                return eventRepository.save(event);
        }

        @Transactional
        @DeleteMapping("/api/events/{id}")
        public void deleteEvent(@PathVariable Long id) {
                attendanceRepository.deleteByEventId(id);
                eventRepository.deleteById(id);
        }
}
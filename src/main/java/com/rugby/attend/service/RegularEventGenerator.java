package com.rugby.attend.service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.rugby.attend.entity.Event;
import com.rugby.attend.repository.EventRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class RegularEventGenerator implements CommandLineRunner {

    private final EventRepository eventRepository;

    @Override
    public void run(String... args) {
        LocalDate today = LocalDate.now();
        LocalDate endDate = today.plusDays(60);

        LocalDate targetDate = today;

        while (!targetDate.isAfter(endDate)) {
            if (targetDate.getDayOfWeek() == DayOfWeek.SUNDAY) {
                createRegularEventIfNotExists(targetDate);
            }

            targetDate = targetDate.plusDays(1);
        }
    }

    private void createRegularEventIfNotExists(LocalDate date) {
        LocalDateTime startDateTime = date.atTime(9, 0);
        LocalDateTime endDateTime = date.atTime(12, 0);

        boolean exists = eventRepository.existsByStartDateTimeAndEventType(
                startDateTime,
                "REGULAR");

        if (exists) {
            return;
        }

        Event event = new Event();
        event.setTitle("通常練習");
        event.setStartDateTime(startDateTime);
        event.setEndDateTime(endDateTime);
        event.setIsAllDay(false);
        event.setLocation("荒川河川敷");
        event.setEventType("REGULAR");

        eventRepository.save(event);
    }
}
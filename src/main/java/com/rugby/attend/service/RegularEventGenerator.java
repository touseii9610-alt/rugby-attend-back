package com.rugby.attend.service;

import java.time.DayOfWeek;
import java.time.LocalDate;
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
        boolean exists = eventRepository.existsByEventDateAndEventType(
                date,
                "REGULAR");

        if (exists) {
            return;
        }

        Event event = new Event();
        event.setTitle("通常練習");
        event.setEventDate(date);
        event.setStartTime(LocalTime.of(9, 0));
        event.setEndTime(LocalTime.of(12, 0));
        event.setLocation("荒川河川敷");
        event.setEventType("REGULAR");

        eventRepository.save(event);
    }
}
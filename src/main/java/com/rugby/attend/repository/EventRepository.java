package com.rugby.attend.repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rugby.attend.entity.Event;

public interface EventRepository extends JpaRepository<Event, Long> {
    boolean existsByStartDateTimeAndEventType(
            LocalDateTime startDateTime,
            String eventType);

    List<Event> findByEventDateGreaterThanEqualOrderByEventDateAsc(
            LocalDate eventDate);
}
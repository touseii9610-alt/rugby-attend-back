package com.rugby.attend.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rugby.attend.entity.Attendance;

public interface AttendanceRepository
        extends JpaRepository<Attendance, Long> {

    List<Attendance> findByEventId(Long eventId);

    Optional<Attendance> findByEventIdAndUserName(
            Long eventId,
            String userName);

    long countByEventIdAndStatus(Long eventId, String status);

    void deleteByEventId(Long eventId);
}
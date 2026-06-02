package com.rugby.attend.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class EventResponse {

    private Long id;
    private String title;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    private Boolean isAllDay;
    private String location;
    private String eventType;

    private long attendCount;
    private long absentCount;
    private long maybeCount;
}
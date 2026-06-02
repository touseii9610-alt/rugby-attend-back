package com.rugby.attend.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AttendanceResponse {

    private Long id;

    private Long eventId;

    private String userName;

    private String status;

    private String comment;

    private LocalDateTime createdAt;

    private String displayName;

    private String pictureUrl;
}
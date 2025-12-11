package com.rabin.backend.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class EventResponseDTO {
    private Long id;
    private String title;
    private String description;
    private String eventImageUrl;
    private LocalDateTime eventDate;
    private double latitude;
    private double longitude;

    private Double finalScore;
}

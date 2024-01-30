package com.blodi.dto;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProfileDTO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pno;
    private int mid;
    private String memberImg = "basic.png";
    private String intro;
    private String gitLink;
    private String instaLink;
    private String blogLink;
    private LocalDateTime regDate;
    private LocalDateTime modDate;
}

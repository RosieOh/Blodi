package com.blodi.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FaqDTO {

    private Long fno;

    private String question;

    private String answer;

    private int cnt;
}

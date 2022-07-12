package com.steady.steadyback.dto;

import com.steady.steadyback.domain.Study;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class TodolistResponseDto {
    private String studyName;
    private Integer hour;
    private Integer minute;

    public TodolistResponseDto(Study study){
        this.studyName = study.getName();
        this.hour = study.getHour();
        this.minute = study.getMinute();
    }
}

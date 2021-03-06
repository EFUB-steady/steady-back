package com.steady.steadyback.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class StudyResponseDto {
    Long studyId;
    String message;

    public StudyResponseDto(Long studyId, String message) {
        this.studyId = studyId;
        this.message = message;
    }
}

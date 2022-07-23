package com.steady.steadyback.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserStudyResponseDto {
    private Long userId;
    private Long studyId;
    private String message;

    public UserStudyResponseDto(Long userId, Long studyId, String message){
        this.userId = userId;
        this.studyId = studyId;
        this.message = message;
    }
}

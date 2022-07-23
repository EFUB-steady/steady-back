package com.steady.steadyback.dto;

import com.steady.steadyback.domain.UserStudy;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserStudyFineResponseDto {

    private Long userId;
    private Long studyId;
    private Integer lastFine;
    private Integer nowFine;

    public UserStudyFineResponseDto(UserStudy userStudy){
        this.userId = userStudy.getUser().getId();
        this.studyId = userStudy.getStudy().getId();
        this.lastFine = userStudy.getLastFine();
        this.nowFine = userStudy.getNowFine();
    }
}

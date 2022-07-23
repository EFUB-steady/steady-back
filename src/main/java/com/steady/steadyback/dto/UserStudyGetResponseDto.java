package com.steady.steadyback.dto;

import com.steady.steadyback.domain.Color;
import com.steady.steadyback.domain.UserStudy;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserStudyGetResponseDto {
    private Long userId;
    private Long studyId;
    private Boolean leader;
    private Integer score;
    private Integer lastFine;
    private Integer nowFine;
    private String color;

    public UserStudyGetResponseDto(UserStudy userStudy){
        this.userId = userStudy.getUser().getId();
        this.studyId = userStudy.getStudy().getId();
        this.leader = userStudy.getLeader();
        this.score = userStudy.getScore();
        this.lastFine = userStudy.getLastFine();
        this.nowFine = userStudy.getNowFine();
        this.color = userStudy.getColor().getValue();
    }
}

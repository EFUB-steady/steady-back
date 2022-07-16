package com.steady.steadyback.dto;

import com.steady.steadyback.domain.StudyPost;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class StudyPostResponseDto {

    private Long studyPostId;

    private Long userId;

    private Long studyId;

    private String link;

    public StudyPostResponseDto(StudyPost studyPost) {
        this.studyPostId= studyPost.getId();
        this.userId= studyPost.getUser().getId();
        this.studyId=studyPost.getStudy().getId();
        this.link= studyPost.getLink();
    }
}

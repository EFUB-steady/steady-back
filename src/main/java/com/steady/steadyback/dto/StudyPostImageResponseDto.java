package com.steady.steadyback.dto;

import com.steady.steadyback.domain.StudyPostImage;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class StudyPostImageResponseDto {
    private Long StudyPostImageId;
    private Long StudyPostId;
    private String image;

    public StudyPostImageResponseDto(StudyPostImage studyPostImage) {
        this.StudyPostImageId=studyPostImage.getId();
        this.StudyPostId=studyPostImage.getStudyPost().getId();
        this.image=studyPostImage.getImage();
    }
}
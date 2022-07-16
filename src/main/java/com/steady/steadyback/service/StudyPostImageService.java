package com.steady.steadyback.service;

import com.steady.steadyback.domain.StudyPostImage;
import com.steady.steadyback.domain.StudyPostImageRepository;
import com.steady.steadyback.dto.StudyPostImageResponseDto;
import com.steady.steadyback.util.errorutil.CustomException;
import com.steady.steadyback.util.errorutil.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StudyPostImageService {

    private final StudyPostImageRepository studyPostImageRepository;

    public StudyPostImageResponseDto findStudyPostImageById(Long studyPostImageId) {
        StudyPostImage studyPostImage = studyPostImageRepository.findById(studyPostImageId)
                .orElseThrow(() -> new CustomException(ErrorCode.STUDY_POST_IMAGE_NOT_FOUND));

        return new StudyPostImageResponseDto(studyPostImage);
    }
}

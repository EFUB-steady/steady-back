package com.steady.steadyback.controller;

import com.steady.steadyback.dto.StudyPostImageResponseDto;
import com.steady.steadyback.service.StudyPostImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/studyPostImage")
public class StudyPostImageController {

    private final StudyPostImageService studyPostImageService;

    @GetMapping("/{studyPostImageId}")
    public StudyPostImageResponseDto getStudyPostImageById(@PathVariable Long studyPostImageId) {
        return studyPostImageService.findStudyPostImageById(studyPostImageId);
    }
}

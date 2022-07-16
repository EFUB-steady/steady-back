package com.steady.steadyback.controller;

import com.steady.steadyback.dto.StudyPostResponseDto;
import com.steady.steadyback.service.StudyPostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/studyPosts")
public class StudyPostController {

    private final StudyPostService studyPostService;

    @GetMapping("/studies/{studyId}")
    public List<StudyPostResponseDto> getStudyPostListByStudyId(@PathVariable Long studyId) {
        return studyPostService.findStudyPostListByStudyId(studyId);
    }

    @GetMapping("/{studyPostId}")
    public StudyPostResponseDto getStudyPostById(@PathVariable Long studyPostId) {
        return studyPostService.findByStudyIdStudyPostId(studyPostId);
    }

}

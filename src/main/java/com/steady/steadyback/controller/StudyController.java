package com.steady.steadyback.controller;

import com.steady.steadyback.domain.User;
import com.steady.steadyback.dto.StudyResponseDto;
import com.steady.steadyback.dto.StudyRequestDto;
import com.steady.steadyback.dto.StudyGetResponseDto;
import com.steady.steadyback.service.StudyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.security.Principal;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/studies")
public class StudyController {
    private final StudyService studyService;

    @GetMapping("/{studyId}")
    public StudyGetResponseDto getStudyById(@PathVariable Long studyId) {
        return studyService.findStudyById(studyId);
    }

    @GetMapping()
    public List<StudyGetResponseDto> getStudyList() {
        return studyService.findStudyList();
    }

    @PostMapping()
    public ResponseEntity<StudyResponseDto> createStudy(@RequestBody StudyRequestDto studyRequestDto) {
        Long studyId = studyService.createStudy(studyRequestDto);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(studyId)
                .toUri();

        StudyResponseDto studyResponseDto = new StudyResponseDto(studyId, "SUCCESS");

        return ResponseEntity.created(location).body(studyResponseDto);
    }

    @DeleteMapping("/{studyId}")
    public StudyResponseDto deleteStudy(@PathVariable Long studyId) {
        studyService.deleteStudyById(studyId);
        StudyResponseDto studyResponseDto = new StudyResponseDto(studyId, "SUCCESS");

        return studyResponseDto;
    }

    @PutMapping("/{studyId}/descriptions")
    public StudyGetResponseDto updateStudyDescription(@PathVariable Long studyId, @RequestBody StudyRequestDto studyRequestDto) {
        Long id = studyService.updateStudyDescription(studyId, studyRequestDto);
        StudyGetResponseDto studyGetResponseDto = studyService.findStudyById(id);
        return studyGetResponseDto;
    }

    @PutMapping("/{studyId}/rules")
    public StudyGetResponseDto updateStudyRule(@PathVariable Long studyId, @RequestBody StudyRequestDto studyRequestDto) {
        Long id = studyService.updateStudyRule(studyId, studyRequestDto);
        StudyGetResponseDto studyGetResponseDto = studyService.findStudyById(id);
        return studyGetResponseDto;
    }
}

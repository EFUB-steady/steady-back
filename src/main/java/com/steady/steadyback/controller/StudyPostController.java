package com.steady.steadyback.controller;

import com.steady.steadyback.dto.StudyPostGetResponseDto;
import com.steady.steadyback.dto.StudyPostRequestDto;
import com.steady.steadyback.dto.StudyPostResponseDto;
import com.steady.steadyback.service.StudyPostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.net.URI;

import com.steady.steadyback.dto.StudyPostResponseDto;
import com.steady.steadyback.service.StudyPostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/studyPosts")
public class StudyPostController {

    private final StudyPostService studyPostService;

    @PostMapping
    public ResponseEntity<StudyPostResponseDto> createStudyPost(    @RequestPart("content") StudyPostRequestDto studyPostRequestDto,
                                                                    @RequestPart("imgUrl") List<MultipartFile> multipartFiles) throws IOException {
        StudyPostResponseDto studyPostResponseDto = studyPostService.createStudyPost(studyPostRequestDto, multipartFiles);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(studyPostResponseDto.getStudyPostId())
                .toUri();

        return ResponseEntity.created(location).body(studyPostResponseDto);
    }

    @GetMapping("/{studyPostId}") //????????? ????????? ?????? ??????
    public StudyPostGetResponseDto getStudyPostById(@PathVariable Long studyPostId) {
        return studyPostService.findByStudyIdStudyPostId(studyPostId);
    }

    @GetMapping("/studies/{studyId}/{date}") //?????????, ????????? ????????? ????????? ??????
    public List<StudyPostGetResponseDto> getStudyPostListByStudyId(@PathVariable Long studyId, @PathVariable String date) {
        return studyPostService.findStudyPostListByDateAndStudy(studyId, date);
    }

    @GetMapping("/users/{userId}/{date}") //??????, ????????? ????????? ????????? ??????
    public List<StudyPostGetResponseDto> getStudyPostListByUserAndDate(@PathVariable Long userId, @PathVariable String date) {
        return studyPostService.findStudyPostListByDateAndUser(userId, date);
    }

}

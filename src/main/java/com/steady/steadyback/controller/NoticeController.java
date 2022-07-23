package com.steady.steadyback.controller;

import com.steady.steadyback.dto.NoticeRequestDto;
import com.steady.steadyback.dto.NoticeResponseDto;
import com.steady.steadyback.service.NoticeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/notices")
public class NoticeController {

    private final NoticeService noticeService;

    @PostMapping
    public NoticeResponseDto createNotice(@RequestBody NoticeRequestDto noticeRequestDto) {

        return noticeService.createNotice(noticeRequestDto);
    }

    @GetMapping("/studies/{studyId}")
    public List<NoticeResponseDto> getNoticeListByStudyId(@PathVariable Long studyId) {
        return noticeService.findNoticeListByStudyId(studyId);
    }

    @PutMapping("/{noticeId}")
    public NoticeResponseDto updateNotice(@PathVariable Long noticeId, @RequestBody NoticeRequestDto noticeRequestDto) {
        Long id = noticeService.updateNotice(noticeId, noticeRequestDto);
        NoticeResponseDto noticeResponseDto = noticeService.findNoticeById(id);
        return noticeResponseDto;
    }

}

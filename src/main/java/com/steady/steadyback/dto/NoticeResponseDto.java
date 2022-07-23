package com.steady.steadyback.dto;

import com.steady.steadyback.domain.Notice;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class NoticeResponseDto {
    private Long noticeId;
    private Long studyId;
    private String content;

    public NoticeResponseDto(Notice notice) {
        this.noticeId = notice.getId();
        this.studyId = notice.getStudy().getId();
        this.content = notice.getContent();
    }
}

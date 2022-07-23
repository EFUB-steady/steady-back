package com.steady.steadyback.dto;

import com.steady.steadyback.domain.Notice;
import com.steady.steadyback.domain.Study;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class NoticeRequestDto {

    private Study study;
    private String content;

    public Notice toEntity()  {
        return Notice.builder()
                .study(study)
                .content(content)
                .build()
                ;
    }
}

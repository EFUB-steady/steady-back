package com.steady.steadyback.domain;

import com.steady.steadyback.dto.NoticeRequestDto;
import com.sun.istack.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter
@Table(name = "notice")
public class Notice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @NotNull
    @JoinColumn(name = "study_id")
    private Study study;

    @Column(length = 300)
    private String content;

    @Builder
    public Notice(Study study, String content) {
        this.study = study;
        this.content = content;
    }

    public void updateNotice(NoticeRequestDto noticeRequestDto) {
        this.study = noticeRequestDto.getStudy();
        this.content = noticeRequestDto.getContent();
    }
}

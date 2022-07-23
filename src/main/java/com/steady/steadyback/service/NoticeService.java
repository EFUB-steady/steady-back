package com.steady.steadyback.service;

import com.steady.steadyback.domain.Notice;
import com.steady.steadyback.domain.NoticeRepository;
import com.steady.steadyback.domain.Study;
import com.steady.steadyback.domain.StudyRepository;
import com.steady.steadyback.dto.NoticeRequestDto;
import com.steady.steadyback.dto.NoticeResponseDto;
import com.steady.steadyback.util.errorutil.CustomException;
import com.steady.steadyback.util.errorutil.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NoticeService {

    private final NoticeRepository noticeRepository;
    private final StudyRepository studyRepository;

    public NoticeResponseDto createNotice(NoticeRequestDto noticeRequestDto) {

        Study study = noticeRequestDto.getStudy();

        studyRepository.findById(study.getId())
                .orElseThrow(() -> new CustomException(ErrorCode.STUDY_NOT_FOUND));

        Notice notice = noticeRepository.save(noticeRequestDto.toEntity());

        NoticeResponseDto noticeResponseDto = new NoticeResponseDto(notice.getId(), notice.getStudy().getId(), notice.getContent());

        return noticeResponseDto;
    }


    public List<NoticeResponseDto> findNoticeListByStudyId(Long studyId) {

        studyRepository.findById(studyId)
                .orElseThrow(() -> new CustomException(ErrorCode.STUDY_NOT_FOUND));

        return noticeRepository.findAllByStudyId(studyId)
                .stream()
                .map(notice -> new NoticeResponseDto(notice))
                .collect(Collectors.toList());
    }

    public NoticeResponseDto findNoticeById(Long id) {
        Notice notice = noticeRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.NOTICE_NOT_FOUND));

        return new NoticeResponseDto(notice);
    }

    public Long updateNotice(Long id, NoticeRequestDto noticeRequestDto) {
        Notice notice = noticeRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.NOTICE_NOT_FOUND));

        notice.updateNotice(noticeRequestDto);
        noticeRepository.save(notice);
        return notice.getId();
    }
}

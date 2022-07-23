package com.steady.steadyback.service;

import com.steady.steadyback.domain.*;
import com.steady.steadyback.dto.StudyRequestDto;
import com.steady.steadyback.dto.StudyGetResponseDto;
import com.steady.steadyback.util.errorutil.CustomException;
import com.steady.steadyback.util.errorutil.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StudyService {
    private final StudyRepository studyRepository;

    public StudyGetResponseDto findStudyById(Long id) {
        Study study = studyRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.STUDY_NOT_FOUND));

        return new StudyGetResponseDto(study);
    }

    public Long createStudy(StudyRequestDto studyRequestDto) {
        if(studyRequestDto.getName().isEmpty()) {
            throw new CustomException(ErrorCode.CANNOT_EMPTY_CONTENT);
        }

        checkRule(studyRequestDto);

        UUID uuid = UUID.randomUUID();
        studyRequestDto.addUUID(uuid);

        Study study = studyRequestDto.toEntity();
        studyRepository.save(study);

        return study.getId();
    }

    public List<StudyGetResponseDto> findStudyList() {
        return studyRepository.findAll()
                .stream()
                .map(study -> new StudyGetResponseDto(study))
                .collect(Collectors.toList());
    }

    public void deleteStudyById(Long id) {
        studyRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.STUDY_NOT_FOUND));
        studyRepository.deleteById(id);
    }

    public Long updateStudyDescription(Long id, StudyRequestDto studyRequestDto) {
        Study study = studyRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.STUDY_NOT_FOUND));

        study.updateDescription(studyRequestDto);
        studyRepository.save(study);
        return study.getId();
    }

    public Long updateStudyRule(Long id, StudyRequestDto studyRequestDto) {
        Study study = studyRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.STUDY_NOT_FOUND));

        checkRule(studyRequestDto);

        study.updateRule(studyRequestDto);
        studyRepository.save(study);
        return study.getId();
    }

    private void checkRule(StudyRequestDto studyRequestDto) {
        if(studyRequestDto.getHour() < 0 || studyRequestDto.getHour() > 23 || studyRequestDto.getMinute() < 0 || studyRequestDto.getMinute() > 59) {
            throw new CustomException(ErrorCode.INVALID_VALUE);
        }

        if(studyRequestDto.getMoney() < studyRequestDto.getLateMoney()) {
            throw new CustomException(ErrorCode.BIGGER_LATE_MONEY);
        }
    }
}

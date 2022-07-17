package com.steady.steadyback.service;


import com.steady.steadyback.domain.Study;
import com.steady.steadyback.domain.StudyPost;
import com.steady.steadyback.domain.StudyPostRepository;
import com.steady.steadyback.domain.StudyRepository;
import com.steady.steadyback.dto.StudyPostResponseDto;
import com.steady.steadyback.util.errorutil.CustomException;
import com.steady.steadyback.util.errorutil.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StudyPostService {

    private final StudyPostRepository studyPostRepository;

    private final StudyRepository studyRepository;


    public StudyPostResponseDto findByStudyIdStudyPostId(Long studyPostId) {
        StudyPost studyPost= studyPostRepository.findById(studyPostId)
                .orElseThrow(()->new CustomException(ErrorCode.STUDY_POST_NOT_FOUND));

        return new StudyPostResponseDto(studyPost);
    }
    public List<StudyPostResponseDto> findStudyPostListByStudyId(Long studyId) {

        Study study = studyRepository.findById(studyId)
                .orElseThrow(()->new CustomException(ErrorCode.STUDY_NOT_FOUND));


        return studyPostRepository.findAllByStudyId(studyId)
                .stream()
                .map(studyPost->new StudyPostResponseDto(studyPost))
                .collect(Collectors.toList());
    }

}

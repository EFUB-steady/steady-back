package com.steady.steadyback.service;

import com.steady.steadyback.domain.*;
import com.steady.steadyback.dto.UserStudyFineResponseDto;
import com.steady.steadyback.dto.UserStudyGetResponseDto;
import com.steady.steadyback.util.errorutil.CustomException;
import com.steady.steadyback.util.errorutil.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserStudyService {

    private final UserRepository userRepository;
    private final StudyRepository studyRepository;
    private final UserStudyRepository userStudyRepository;

    @Transactional
    public void deleteUserStudyById(Long userId, Long studyId, User loginUser){
        User targetUser = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));

        Study study = studyRepository.findById(studyId)
                .orElseThrow(() -> new CustomException(ErrorCode.STUDY_NOT_FOUND));

        UserStudy userStudy = userStudyRepository.findByUserAndStudy(targetUser, study);

        //권한 확인, 로그인한 본인도 아니고 리더도 아니면 예외 처리
        if (userId!=loginUser.getId() && !userStudyRepository.findByUserAndStudy(loginUser, study).getLeader()){
            throw new CustomException(ErrorCode.UNAUTHORIZED_USER);
        }

        userStudyRepository.delete(userStudy);
        studyRepository.updatePeopleCount(study.getPeopleCount()-1, study.getId());
    }

    @Transactional
    public UserStudyGetResponseDto createUserStudy(Long userId, String token){
        //유효한 token인 지 검증 후 가입 처리
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));
        Study study = studyRepository.findByUuid(token);
        if (study==null){
            throw new CustomException(ErrorCode.STUDY_NOT_FOUND);
        }
        if (study.getPeopleCount()>=9){
            throw new CustomException(ErrorCode.OVER_9_MEMBER);
        }

        //이미 가입했다면
        UserStudy userAndStudy = userStudyRepository.findByUserAndStudy(user, study);
        if (userAndStudy!=null){
           throw new CustomException(ErrorCode.USER_STUDY_ALREADY_EXISTS);
        }

        //스터디 가입하기
        //같은 스터디에서 사용한 컬러 가져오기
        List<Color> usedColors = userStudyRepository.findByStudy(study)
                .stream()
                .map(UserStudy::getColor)
                .collect(Collectors.toList());

        //색 배정, 9가지 색 중 스터디원이 이미 가지지 않은 색으로
        Color color = null;
        for (Color value : Color.values()){
            if(!usedColors.contains(value)){
                color = value;
                break;
            }
        }

        UserStudy newUserStudy = UserStudy.builder()
                .user(user)
                .study(study)
                .color(color)
                .lastFine(0)
                .nowFine(0)
                .leader(false)
                .score(0)
                .build();

        UserStudy save = userStudyRepository.save(newUserStudy);
        studyRepository.updatePeopleCount(study.getPeopleCount()+1, study.getId());

        return new UserStudyGetResponseDto(save);
    }

    public List<UserStudyFineResponseDto> getFineList(Long userId){
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));
        List<UserStudyFineResponseDto> fines = userStudyRepository.findByUser(user)
                .stream()
                .map(userStudy -> new UserStudyFineResponseDto(userStudy))
                .collect(Collectors.toList());
        return fines;
    }
}

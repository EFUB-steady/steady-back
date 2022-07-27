package com.steady.steadyback.service;

import com.steady.steadyback.domain.*;
import com.steady.steadyback.dto.UserStudyFineResponseDto;
import com.steady.steadyback.dto.UserStudyGetResponseDto;
import com.steady.steadyback.dto.UserStudyRankingResponseDto;
import com.steady.steadyback.dto.UserStudyResponseDto;
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

        UserStudy userStudy = userStudyRepository.findByUserAndStudy(targetUser, study)
                .orElseThrow(() -> new CustomException(ErrorCode.INFO_NOT_FOUNT));

        UserStudy loginUserStudy = userStudyRepository.findByUserAndStudy(loginUser, study)
                .orElseThrow(() -> new CustomException(ErrorCode.INFO_NOT_FOUNT));

        //권한 확인, 로그인한 본인도 아니고 리더도 아니면 예외 처리
        if (userId!=loginUser.getId() && !loginUserStudy.getLeader()){
            throw new CustomException(ErrorCode.UNAUTHORIZED_USER);
        }

        userStudyRepository.delete(userStudy);
        studyRepository.updatePeopleCount(study.getPeopleCount()-1, study.getId());
    }

    @Transactional
    public UserStudyGetResponseDto createUserStudy(User user, String token){
        //유효한 token인 지 검증 후 가입 처리
        Study study = studyRepository.findByUuid(token);
        if (study==null){
            throw new CustomException(ErrorCode.STUDY_NOT_FOUND);
        }
        if (study.getPeopleCount()>=9){
            throw new CustomException(ErrorCode.OVER_9_MEMBER);
        }

        //이미 가입했다면
        if (userStudyRepository.existsByUserAndStudy(user, study)){
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
                .todayPost(0)
                .todayFine(0)
                .build();

        UserStudy save = userStudyRepository.save(newUserStudy);
        studyRepository.updatePeopleCount(study.getPeopleCount()+1, study.getId());

        return new UserStudyGetResponseDto(save);
    }

    public List<UserStudyFineResponseDto> getFineList(User user){
        List<UserStudyFineResponseDto> fines = userStudyRepository.findByUser(user)
                .stream()
                .map(userStudy -> new UserStudyFineResponseDto(userStudy))
                .collect(Collectors.toList());
        return fines;
    }

    public List<UserStudyRankingResponseDto> getRankingList(Long studyId){
        Study study = studyRepository.findById(studyId)
                .orElseThrow(() -> new CustomException(ErrorCode.STUDY_NOT_FOUND));
        List<UserStudyRankingResponseDto> rankingList = userStudyRepository.findByStudy(study)
                .stream()
                .map(userStudy -> new UserStudyRankingResponseDto(userStudy))
                .sorted()
                .collect(Collectors.toList());
        return rankingList;
    }

    public List<UserStudyGetResponseDto> getStudyListByUser(User user) {
        return userStudyRepository.findByUser(user)
                .stream()
                .map(userStudy -> new UserStudyGetResponseDto(userStudy))
                .collect(Collectors.toList());
    }
}

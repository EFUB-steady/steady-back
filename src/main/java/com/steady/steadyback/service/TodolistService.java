package com.steady.steadyback.service;

import com.steady.steadyback.domain.*;
import com.steady.steadyback.dto.TodolistResponseDto;
import com.steady.steadyback.util.errorutil.CustomException;
import com.steady.steadyback.util.errorutil.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TodolistService {

    private final UserRepository userRepository;
    private final UserStudyRepository userStudyRepository;
    private final StudyPostRepository studyPostRepository;

    //오늘의 투두리스트. 스터디 이름, 시, 분(study)
    @Transactional
    public List<TodolistResponseDto> findTodolist(Long userId) {
        List<TodolistResponseDto> todolist = new ArrayList<>();

        //유저별 스터디 가져오기 -> (로그인 유저로)
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));
        List<UserStudy> userStudyList = userStudyRepository.findByUser(user);

        for (UserStudy userStudy : userStudyList) {
            Study study = userStudy.getStudy();

            //오늘의 인증글이 올라오지 않은 스터디만
            LocalDate today = LocalDate.now();
            StudyPost studyPost = studyPostRepository.findByUserAndStudyAndDate(user, study, today);

            if(studyPost!=null){
                continue; //인증글이 이미 올라옴
            }

            int dayOfWeek = today.getDayOfWeek().getValue();
            //오늘에 해당하는 스터디만
            if(study.getMon() && dayOfWeek==1 ||
                    study.getTue() && dayOfWeek==2 ||
                    study.getWed() && dayOfWeek==3 ||
                    study.getThu() && dayOfWeek==4 ||
                    study.getFri() && dayOfWeek==5 ||
                    study.getSat() && dayOfWeek==6 ||
                    study.getSun() && dayOfWeek==7){
                TodolistResponseDto todolistResponseDto = new TodolistResponseDto(study);
                todolist.add(todolistResponseDto);
            }
        }
        return todolist;
    }

}

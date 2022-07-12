package com.steady.steadyback.service;

import com.steady.steadyback.domain.*;
import com.steady.steadyback.dto.TodolistResponseDto;
import com.steady.steadyback.util.errorutil.CustomException;
import com.steady.steadyback.util.errorutil.ErrorCode;
import lombok.RequiredArgsConstructor;
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

    //오늘의 투두리스트. 스터디 이름, 시, 분(study)
    @Transactional
    public List<TodolistResponseDto> findTodolist(Long userId) {
        int dayOfWeek = getDayOfWeek(); //요일 1~7

        ArrayList<TodolistResponseDto> todolist = new ArrayList<>();

        //유저별 스터디 가져오기
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));
        List<UserStudy> userStudyList = userStudyRepository.findByUser(user);

        //오늘에 해당하는 스터디만
        for (UserStudy userStudy : userStudyList) {
            Study study = userStudy.getStudy();
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

    public static int getDayOfWeek() {
        LocalDate localDate = LocalDate.now();
        return localDate.getDayOfWeek().
                getValue();
    }

}

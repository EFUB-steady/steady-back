package com.steady.steadyback.controller;

import com.steady.steadyback.dto.UserStudyGetResponseDto;
import com.steady.steadyback.dto.UserStudyResponseDto;
import com.steady.steadyback.service.UserStudyService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;

@RestController
@RequiredArgsConstructor
@RequestMapping("/studies")
public class UserStudyController {

    private final UserStudyService userStudyService;


    //로그인 컨트롤러에서 로그인 정상 처리 -> 세션에서 dest 값을 읽어 이전 경로로 리다이렉트
    @GetMapping("/join")
    public UserStudyGetResponseDto joinStudy(@PathParam("token") String token){
        //토큰 유효하면 가입 처리 후 스터디 페이지로 이동.
        return userStudyService.createUserStudy(token);
    }

    @DeleteMapping("/{userId}/leave/{studyId}")
    public UserStudyResponseDto leaveStudy(@PathVariable Long userId, @PathVariable Long studyId){
        userStudyService.deleteUserStudyById(userId, studyId);

        UserStudyResponseDto userStudyResponseDto = new UserStudyResponseDto(userId, studyId, "DELETED");
        return userStudyResponseDto;
    }
}

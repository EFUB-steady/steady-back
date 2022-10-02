package com.steady.steadyback.util.errorutil;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;

import static org.springframework.http.HttpStatus.*;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    //400 BAD_REQUEST : 잘못된 요청
    CANNOT_EMPTY_CONTENT(BAD_REQUEST, "내용이 비어있을 수 없습니다."),
    BIGGER_LATE_MONEY(BAD_REQUEST, "지각비가 결석비를 초과할 수 없습니다."),
    INVALID_VALUE(BAD_REQUEST, "올바르지 않은 값입니다."),
    OVER_2_IMAGES(BAD_REQUEST, "사진은 최대 2장까지 업로드 가능합니다."),
    CANNOT_DUPLICATE_EMAIL(BAD_REQUEST, "이미 가입된 이메일입니다"),
    INCORRECT_PASSWORD(BAD_REQUEST, "비밀번호가 일치하지 않습니다."),
    OVER_9_MEMBER(BAD_REQUEST, "스터디원은 최대 9명까지 추가 가능합니다."),

    //404 NOT_FOUND : Resource를 찾을 수 없음
    MEMBER_NOT_FOUND(NOT_FOUND, "해당 유저 정보를 찾을 수 없습니다."),
    STUDY_NOT_FOUND(NOT_FOUND, "해당 스터디 정보를 찾을 수 없습니다."),
    STUDY_POST_NOT_FOUND(NOT_FOUND, "해당 스터디 인증글 정보를 찾을 수 없습니다."),
    REPORT_NOT_FOUND(NOT_FOUND, "해당 신고 정보를 찾을 수 없습니다."),
    STUDY_POST_IMAGE_NOT_FOUND(NOT_FOUND, "해당 이미지 정보를 찾을 수 없습니다."),
    STUDY_POST_LIST_NOT_FOUND(NOT_FOUND, "인증글 정보를 찾을 수 없습니다."),
    TOKEN_NOT_FOUND(NOT_FOUND, "존재하지 않는 토큰입니다."),
    NOTICE_NOT_FOUND(NOT_FOUND, "해당 공지사항 정보를 찾을 수 없습니다."),

    UNAUTHORIZED_USER(UNAUTHORIZED, "권한이 없습니다."),
    NON_LOGIN(UNAUTHORIZED, "로그인 후 이용 가능합니다."),
    INFO_NOT_FOUNT(NOT_FOUND, "해당 정보를 찾을 수 없습니다."),

    USER_STUDY_ALREADY_EXISTS(CONFLICT, "이미 가입한 스터디원입니다."),

    INVALID_TOKEN(BAD_REQUEST, "토큰이 만료됐거나 유효하지 않습니다."),
    INVALID_REFRESH_TOKEN(BAD_REQUEST, "리프레시 토큰이 유효하지 않습니다. 재로그인 해주세요."),
    REFRESH_TOKEN_DOESNT_MATCH(BAD_REQUEST, "리프레시 토큰이 매치되지 않습니다.")
    ;

    private final HttpStatus httpStatus;
    private final String detail;

}
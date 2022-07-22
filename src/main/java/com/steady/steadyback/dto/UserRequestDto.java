package com.steady.steadyback.dto;

import com.steady.steadyback.domain.User;
import com.sun.istack.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;

@Getter
@NoArgsConstructor
public class UserRequestDto {
    private String nickname;


    private String email;


    private String password;


    private String phone;


    private String name;

    private String role;


    @Builder UserRequestDto(User user) {
        this.nickname= user.getNickname();
        this.name= user.getName();
        this.phone= user.getPhone();
        this.password= user.getPassword();
        this.email= user.getEmail();
        this.role = "USER";
    }
}

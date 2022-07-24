package com.steady.steadyback.dto;

import com.steady.steadyback.domain.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserResponseDto {
    private Long userId;
    private String nickname;
    private String email;
    private String phone;
    private String name;

    private String message;

    public UserResponseDto(User user) {
        this.userId = user.getId();
        this.nickname = user.getNickname();
        this.email = user.getEmail();
        this.phone = user.getPhone();
        this.name = user.getName();
    }

    public UserResponseDto(User user, String message) {
        this.userId = user.getId();
        this.nickname = user.getNickname();
        this.email = user.getEmail();
        this.password = user.getPassword();
        this.phone = user.getPhone();
        this.name = user.getName();
        this.message= message ;
    }
}

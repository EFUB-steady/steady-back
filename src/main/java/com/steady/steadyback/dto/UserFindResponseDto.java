package com.steady.steadyback.dto;

import com.steady.steadyback.domain.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserFindResponseDto {

    private Long userId;

    private String email;

    private String message;

    @Builder
    public UserFindResponseDto(User user, String message) {
        this.userId= user.getId();
        this.email= user.getEmail();
        this.message= message;
    }
}

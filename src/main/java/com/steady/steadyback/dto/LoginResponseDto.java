package com.steady.steadyback.dto;

import com.steady.steadyback.domain.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LoginResponseDto {
    private Long userId;
    private String email;
    private String accessToken;
    private String refreshToken;

    @Builder
    public LoginResponseDto(User user, String accessToken, String refreshToken){
        this.userId = user.getId();
        this.email = user.getEmail();
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }
}

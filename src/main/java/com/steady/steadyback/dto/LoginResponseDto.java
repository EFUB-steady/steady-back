package com.steady.steadyback.dto;

import com.steady.steadyback.domain.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LoginResponseDto {
    String email;
    String token;

    @Builder
    public LoginResponseDto(User user, String token){
        this.email = user.getEmail();
        this.token = token;
    }
}

package com.steady.steadyback.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserDeleteResponseDto {
    Long userId;
    String message;

    public UserDeleteResponseDto(Long userId, String message) {
        this.userId = userId;
        this.message = message;
    }
}

package com.steady.steadyback.dto;

import com.steady.steadyback.domain.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;


@Getter
@NoArgsConstructor
public class SignupRequestDto {
    private String name;
    private String nickname;
    private String email;
    private String password;
    private String phone;

    @Builder
    public SignupRequestDto(User user) {
        this.name = user.getName();
        this.nickname = user.getNickname();
        this.email = user.getEmail();
        this.password = user.getPassword();
        this.phone = user.getPhone();
    }

    public void encryptPassword(PasswordEncoder passwordEncoder) {

        this.password = passwordEncoder.encode(password);
    }

    public User toEntity(SignupRequestDto signupRequestDto) {
        return User.builder()
                .name(signupRequestDto.getName())
                .nickname(signupRequestDto.getNickname())
                .email(signupRequestDto.getEmail())
                .password(signupRequestDto.getPassword())
                .phone(signupRequestDto.getPhone())
                .build();
    }
}

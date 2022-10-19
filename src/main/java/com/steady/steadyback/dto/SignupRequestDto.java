package com.steady.steadyback.dto;

import com.steady.steadyback.domain.User;
import com.steady.steadyback.util.errorutil.CustomException;
import com.steady.steadyback.util.errorutil.ErrorCode;
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
    private Long kakaoId;

    @Builder
    public SignupRequestDto(User user) {
        this.name = user.getName();
        this.nickname = user.getNickname();
        this.email = user.getEmail();
        this.password = user.getPassword();
        this.phone = user.getPhone();
        this.kakaoId = user.getKakaoId();
    }

    public void encryptPassword(PasswordEncoder passwordEncoder) {

        if(password.isEmpty())
            throw new CustomException(ErrorCode.CANNOT_EMPTY_CONTENT);
        else
            this.password = passwordEncoder.encode(password);
    }

    public User toEntity(SignupRequestDto signupRequestDto) {
        return User.builder()
                .name(signupRequestDto.getName())
                .nickname(signupRequestDto.getNickname())
                .email(signupRequestDto.getEmail())
                .password(signupRequestDto.getPassword())
                .phone(signupRequestDto.getPhone())
                .kakaoId(signupRequestDto.kakaoId)
                .build();
    }
}

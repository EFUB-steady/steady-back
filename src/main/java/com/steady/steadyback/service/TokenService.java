package com.steady.steadyback.service;

import com.steady.steadyback.config.JwtTokenProvider;
import com.steady.steadyback.domain.User;
import com.steady.steadyback.domain.UserRepository;
import com.steady.steadyback.dto.RefreshTokenResponseDto;
import com.steady.steadyback.util.errorutil.CustomException;
import com.steady.steadyback.util.errorutil.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class TokenService {
    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final RedisTemplate<String, String> redisTemplate;

    @Transactional
    public void updateRefreshToken(String userEmail, String refreshToken){
        // Redis에 저장 - 만료 시간 설정을 통해 자동 삭제 처리
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));
        redisTemplate.opsForValue().set(
                userEmail,
                refreshToken,
                60 * 60 * 24 * 14 * 1000L,
                TimeUnit.MILLISECONDS
        );
    }

    @Transactional
    public RefreshTokenResponseDto refreshToken(String userEmail, String refreshToken) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));

        //refresh token 검증
        if (!jwtTokenProvider.validateToken(refreshToken)) {
            throw new CustomException(ErrorCode.INVALID_REFRESH_TOKEN);
        }

        String refreshTokenInRedis = redisTemplate.opsForValue().get(user.getEmail());
        if (!refreshToken.equals(refreshTokenInRedis)) {
            throw new CustomException(ErrorCode.REFRESH_TOKEN_DOESNT_MATCH);
        }

        //토큰 재발행 & redis 업데이트
        String newAccessToken = jwtTokenProvider.createAccessToken(user.getEmail(), user.getRole());
        String newRefreshToken = jwtTokenProvider.createRefreshToken(user.getEmail(), user.getRole());
        updateRefreshToken(user.getEmail(), newRefreshToken);

        return new RefreshTokenResponseDto(newAccessToken, newRefreshToken);
    }
}

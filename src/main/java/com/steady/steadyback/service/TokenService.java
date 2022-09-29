package com.steady.steadyback.service;

import com.steady.steadyback.config.JwtTokenProvider;
import com.steady.steadyback.domain.RefreshToken;
import com.steady.steadyback.domain.RefreshTokenRepository;
import com.steady.steadyback.domain.User;
import com.steady.steadyback.domain.UserRepository;
import com.steady.steadyback.dto.RefreshTokenResponseDto;
import com.steady.steadyback.util.errorutil.CustomException;
import com.steady.steadyback.util.errorutil.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TokenService {
    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;

    @Transactional
    public void updateRefreshToken(String userEmail, String refreshToken){
        //해당 이메일에 저장되어있던 리프레시 토큰 삭제
        if(refreshTokenRepository.existsByUserEmail(userEmail)){
            refreshTokenRepository.deleteByUserEmail(userEmail);
        }
        refreshTokenRepository.save(new RefreshToken(refreshToken, userEmail));
    }

    @Transactional
    public RefreshTokenResponseDto refreshToken(String userEmail, String refreshToken){
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));
        RefreshToken refreshToken1 = refreshTokenRepository.findByUserEmail(userEmail)
                .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));

        //refresh token db와 일치하는지 && 유효성 검사
        if((refreshToken1.getRefreshToken()).equals(refreshToken) && jwtTokenProvider.validateToken(refreshToken)){
            //새로 발급
            String accessToken = jwtTokenProvider.createAccessToken(user.getEmail(), user.getRole());
            String newRefreshToken = jwtTokenProvider.createRefreshToken(user.getEmail(), user.getRole());

            updateRefreshToken(userEmail, newRefreshToken);

            return new RefreshTokenResponseDto(accessToken, newRefreshToken);
        }
        else throw new CustomException(ErrorCode.INVALID_REFRESH_TOKEN);
    }
}

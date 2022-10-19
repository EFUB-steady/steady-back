package com.steady.steadyback.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.steady.steadyback.config.JwtTokenProvider;

import com.steady.steadyback.domain.User;
import com.steady.steadyback.domain.UserRepository;
import com.steady.steadyback.domain.oauth.KakaoProfile;
import com.steady.steadyback.domain.oauth.OAuthToken;
import com.steady.steadyback.dto.*;
import com.steady.steadyback.service.TokenService;
import com.steady.steadyback.service.UserService;
import com.steady.steadyback.util.errorutil.CustomException;
import com.steady.steadyback.util.errorutil.ErrorCode;
import lombok.RequiredArgsConstructor;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;

    @Value("${spring.security.oauth2.client.registration.kakao.client-id}")
    private String kakaoClientId;

    @Value("${spring.security.oauth2.client.registration.kakao.redirect-uri}")
    private String kakaoRedirectUri;

    @Value("${cos.key}")
    private String cosKey;

    @GetMapping("/auth/kakao/callback")
    public LoginResponseDto kakaoCallback(String code) {

        //1. 응답받은 코드로 토큰 발급 요청
        RestTemplate rt = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", kakaoClientId);
        params.add("redirect_uri", kakaoRedirectUri);
        params.add("code", code);

        HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest = new HttpEntity<>(params, headers);

        ResponseEntity<String> response = rt.exchange(
                "https://kauth.kakao.com/oauth/token",
                HttpMethod.POST,
                kakaoTokenRequest,
                String.class
        );

        //발급받은 토큰 정보 oauthToken에 담기
        ObjectMapper objectMapper = new ObjectMapper();
        OAuthToken oauthToken = null;
        try {
            oauthToken = objectMapper.readValue(response.getBody(), OAuthToken.class);
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }


        //2. 발급받은 토큰으로 사용자 정보 조회
        RestTemplate rt2 = new RestTemplate();

        HttpHeaders headers2 = new HttpHeaders();
        headers2.add("Authorization", "Bearer " + oauthToken.getAccess_token());
        headers2.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        HttpEntity<MultiValueMap<String, String>> kakaoProfileRequest2 = new HttpEntity<>(headers2);

        ResponseEntity<String> response2 = rt2.exchange(
                "https://kapi.kakao.com/v2/user/me",
                HttpMethod.POST,
                kakaoProfileRequest2,
                String.class
        );

        //사용자 정보 kakaoProfile에 담기
        ObjectMapper objectMapper2 = new ObjectMapper();
        KakaoProfile kakaoProfile = null;
        try {
            kakaoProfile = objectMapper2.readValue(response2.getBody(), KakaoProfile.class);
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return kakaoLogin(kakaoProfile);
    }


    @PostMapping("/kakao/login")
    public LoginResponseDto kakaoLogin(@RequestBody KakaoProfile kakaoProfile){

        //User 저장
        String garbagePassword = kakaoProfile.getId().toString()+cosKey; // 임의의 비밀번호

        User kakaoUser = User.builder()
                .name(kakaoProfile.getKakao_account().profile.nickname)
                .nickname(kakaoProfile.getKakao_account().profile.nickname)
                .email(kakaoProfile.getKakao_account().getEmail()+"_"+kakaoProfile.getId())
                .password(garbagePassword.toString())
                .kakaoId(kakaoProfile.getId())
                .build();

        SignupRequestDto signupRequestDto = new SignupRequestDto(kakaoUser);


        if(!userService.checkDuplicateUsers(signupRequestDto)) {
            signup(signupRequestDto); //기존 회원이 아니면 자동 회원가입을 진행
        }

        LoginRequestDto loginRequestDto = new LoginRequestDto(kakaoUser);

        return login(loginRequestDto);
    }


    @PostMapping("/signup")
    public ResponseEntity<Object> signup(@RequestBody SignupRequestDto signupRequestDto){
        if (userService.checkDuplicateUsers(signupRequestDto))
            throw new CustomException(ErrorCode.CANNOT_DUPLICATE_EMAIL);
        signupRequestDto.encryptPassword(passwordEncoder);
        return userService.joinUser(signupRequestDto);
    }


    @PostMapping("/login")
    public LoginResponseDto login(@RequestBody LoginRequestDto loginRequestDto) {
        User user = userService.findUserByEmail(loginRequestDto.getEmail().trim());

        if (!userRepository.existsByEmail(loginRequestDto.getEmail().trim())) {
            throw new CustomException(ErrorCode.MEMBER_NOT_FOUND);
        }

        if (!passwordEncoder.matches(loginRequestDto.getPassword(), user.getPassword())){
            throw new CustomException(ErrorCode.INCORRECT_PASSWORD);
        }

        String access = jwtTokenProvider.createAccessToken(user.getEmail(), user.getRole());
        String refresh = jwtTokenProvider.createRefreshToken(user.getEmail(), user.getRole());

        tokenService.updateRefreshToken(loginRequestDto.getEmail(), refresh); //리프레시 토큰 저장

        LoginResponseDto loginResponseDto = LoginResponseDto.builder()
                .user(user)
                .accessToken(access)
                .refreshToken(refresh)
                .build();
        return loginResponseDto;
    }

    @PostMapping("/refresh/token")
    public RefreshTokenResponseDto refreshToken(@RequestHeader(value="KEY-EMAIL") String email,
                                                @RequestHeader(value="REFRESH-TOKEN") String refreshToken) {
        RefreshTokenResponseDto refreshTokenResponseDto = tokenService.refreshToken(email, refreshToken);
        return refreshTokenResponseDto;
    }

    @DeleteMapping("/{userId}")
    public UserDeleteResponseDto deleteUser(@PathVariable Long userId) {

        userService.deleteUserById(userId);
        UserDeleteResponseDto userDeleteResponseDto = new UserDeleteResponseDto(userId, "SUCCESS");
        return userDeleteResponseDto;
    }

    @GetMapping("/{userId}")
    public UserResponseDto getUserById(@PathVariable Long userId) {
        return userService.findUserById(userId);
    }

    @PostMapping("/findId")
    public UserFindResponseDto getUserIdByNameAndPhone(@RequestBody Map<String,String> param) {
        String name = param.get("name");
        String phone = param.get("phone");
        return userService.findUserIdByNameAndPhone(name, phone);

    }

    @PostMapping("/findPw")
    public UserFindResponseDto findPwPOST(@RequestBody Map<String,String> param) {
        String email = param.get("email");
        User user= userRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));
        String pw = "";
        for(int i=0; i<12; i++) {
            pw+=(char) ((Math.random()*26) +97 );
        }
        UserFindPwRequestDto userFindPwRequestDto= new UserFindPwRequestDto(user,pw);
        userFindPwRequestDto.encryptPassword(passwordEncoder);
        return userService.findPw(user, userFindPwRequestDto, pw);
    }


    @GetMapping
    public List<UserResponseDto> getUserList() {

        return userService.findUserList();
    }

    @PatchMapping("/{userId}")
    public UserUpdateResponseDto updateUser(@PathVariable Long userId, @RequestBody UserUpdateRequestDto updateRequestDto){
        updateRequestDto.encryptPassword(passwordEncoder);
        return userService.updateUser(userId, updateRequestDto);
    }

}

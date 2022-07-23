package com.steady.steadyback.controller;

import com.steady.steadyback.config.JwtTokenProvider;
import com.steady.steadyback.domain.User;
import com.steady.steadyback.domain.UserRepository;
import com.steady.steadyback.dto.*;
import com.steady.steadyback.service.UserService;
import com.steady.steadyback.util.errorutil.CustomException;
import com.steady.steadyback.util.errorutil.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    @PostMapping("/signup")
    public ResponseEntity<Object> signup(@RequestBody SignupRequestDto signupRequestDto){
        if (userService.checkDuplicateUsers(signupRequestDto))
            throw new CustomException(ErrorCode.CANNOT_DUPLICATE_EMAIL);
        signupRequestDto.encryptPassword(passwordEncoder);
        return userService.joinUser(signupRequestDto);
    }


    @PostMapping("/login")
    public LoginResponseDto login(@RequestBody LoginRequestDto loginRequestDto) {
        User user = userService.findUserByEmail(loginRequestDto.getEmail());

        if (!userRepository.existsByEmail(loginRequestDto.getEmail())) {
            throw new CustomException(ErrorCode.MEMBER_NOT_FOUND);
        }

        if (!passwordEncoder.matches(loginRequestDto.getPassword(), user.getPassword())){
            throw new CustomException(ErrorCode.INCORRECT_PASSWORD);
        }
        String token = jwtTokenProvider.createToken(user.getEmail(), user.getRole());
        LoginResponseDto loginResponseDto = LoginResponseDto.builder()
                .user(user)
                .token(token)
                .build();
        return loginResponseDto;
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

    @GetMapping
    public List<UserResponseDto> getUserList() {

        return userService.findUserList();
    }

}

package com.steady.steadyback.controller;

import com.steady.steadyback.config.JwtTokenProvider;

import com.steady.steadyback.domain.User;
import com.steady.steadyback.domain.UserRepository;
import com.steady.steadyback.dto.*;
import com.steady.steadyback.service.UserService;
import com.steady.steadyback.util.errorutil.CustomException;
import com.steady.steadyback.util.errorutil.ErrorCode;
import lombok.RequiredArgsConstructor;

import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.tags.Param;

import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.InputStream;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;

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

        return userService.findPw(user);
    }


    @GetMapping
    public List<UserResponseDto> getUserList() {

        return userService.findUserList();
    }

    @PatchMapping("/{userId}")
    public UserResponseDto updateUser(@PathVariable Long userId, @RequestBody SignupRequestDto updateRequestDto){
        if (userService.checkDuplicateUsers(updateRequestDto))
            throw new CustomException(ErrorCode.CANNOT_DUPLICATE_EMAIL);
        updateRequestDto.encryptPassword(passwordEncoder);
        return userService.updateUser(userId, updateRequestDto);
    }

}

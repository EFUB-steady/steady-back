package com.steady.steadyback.service;

import com.steady.steadyback.domain.User;
import com.steady.steadyback.domain.UserRepository;
import com.steady.steadyback.dto.*;
import com.steady.steadyback.util.errorutil.CustomException;
import com.steady.steadyback.util.errorutil.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String sender;

    private static final String FROM_ADDRESS = "bje5774@gmail.com";
    @Transactional
    public ResponseEntity<Object> joinUser(SignupRequestDto signupRequestDto){

        if (signupRequestDto.getName().isEmpty()||signupRequestDto.getNickname().isEmpty()||signupRequestDto.getEmail().isEmpty()||signupRequestDto.getPassword().isEmpty()||signupRequestDto.getPhone().isEmpty())
            throw new CustomException(ErrorCode.CANNOT_EMPTY_CONTENT);

        User user = signupRequestDto.toEntity(signupRequestDto);

        userRepository.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(user.getName()+"님이 성공적으로 가입되었습니다.");
    }


    public void deleteUserById(Long id) {
        userRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));
        userRepository.deleteById(id);
    }


    public UserResponseDto findUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));

        return new UserResponseDto(user);
    }


    public UserFindResponseDto findUserIdByNameAndPhone(String name, String phone){
        User user = userRepository.findByNameAndPhone(name, phone);
        if(user==null) throw new CustomException(ErrorCode.MEMBER_NOT_FOUND);
        else {
            return new UserFindResponseDto(user, "email이 성공적으로 조회 되었습니다.");
        }
    }

    public UserFindResponseDto findPw(User user, UserFindPwRequestDto userFindPwRequestDto, String pw) {

        user.updatePw(userFindPwRequestDto);
        userRepository.save(user);
        sendEmail(user, pw);

        return new UserFindResponseDto(user,  "이메일이 성공적으로 전송되었습니다.");
    }

    public void sendEmail(User user, String pw)  {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(sender);
        message.setTo(user.getEmail());
        message.setSubject("steady 임시 비밀번호 입니다.");
        String msg = "";

        msg += user.getName() + "님의 임시 비밀번호 입니다. 비밀번호를 변경하여 사용하세요.";
        msg += "\n 임시 비밀번호 : ";
        msg += pw ;

        message.setText(msg);

        javaMailSender.send(message);
    }

    public User findUserByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));

        return user;
    }


    public List<UserResponseDto> findUserList() {
        return userRepository.findAll()
                .stream()
                .map(user -> new UserResponseDto(user))
                .collect(Collectors.toList());
    }


    public Boolean checkDuplicateUsers(SignupRequestDto signupRequestDto){
        return userRepository.existsByEmail(signupRequestDto.getEmail());
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("등록되지 않은 사용자입니다."));

    }

    @Transactional
    public UserUpdateResponseDto updateUser(Long userId, UserUpdateRequestDto updateRequestDto){
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));
        user.update(updateRequestDto);

        return new UserUpdateResponseDto(userRepository.save(user), "정보가 수정되었습니다.");
    }


}

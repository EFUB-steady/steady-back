package com.steady.steadyback.service;

import com.steady.steadyback.domain.User;
import com.steady.steadyback.domain.UserRepository;
import com.steady.steadyback.dto.SignupRequestDto;
import com.steady.steadyback.dto.UserResponseDto;
import com.steady.steadyback.util.errorutil.CustomException;
import com.steady.steadyback.util.errorutil.ErrorCode;
import lombok.RequiredArgsConstructor;
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

    @Transactional
    public ResponseEntity<Object> joinUser(SignupRequestDto signupRequestDto){

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


}

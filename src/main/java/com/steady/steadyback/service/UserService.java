package com.steady.steadyback.service;

import com.steady.steadyback.domain.User;
import com.steady.steadyback.domain.UserRepository;
import com.steady.steadyback.dto.UserFindResponseDto;
import com.steady.steadyback.dto.UserRequestDto;
import com.steady.steadyback.dto.UserResponseDto;
import com.steady.steadyback.util.errorutil.CustomException;
import com.steady.steadyback.util.errorutil.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String sender;

    private static final String FROM_ADDRESS = "bje5774@gmail.com";
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

    public UserFindResponseDto findPw(User user) {
        String pw = "";
        for(int i=0; i<12; i++) {
            pw+=(char) ((Math.random()*26) +97 );
        }
        user.updatePw(pw);

        sendEmail(user);

        return new UserFindResponseDto(user, "이메일이 성공적으로 전송되었습니다.");
    }

    public void sendEmail(User user) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(sender);
        message.setTo(user.getEmail());
        message.setSubject("steady 임시 비밀번호 입니다.");
        String msg = "";

            msg += user.getName() + "님의 임시 비밀번호 입니다. 비밀번호를 변경하여 사용하세요.";
            msg += "\n 임시 비밀번호 : ";
            msg += user.getPassword() ;

        message.setText(msg);

        javaMailSender.send(message);
    }


}

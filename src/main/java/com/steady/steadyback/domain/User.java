package com.steady.steadyback.domain;

import com.steady.steadyback.dto.StudyRequestDto;
import com.steady.steadyback.dto.UserRequestDto;
import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(length = 20)
    private String nickname;

    @NotNull
    @Column(length = 50)
    private String email;

    @NotNull
    @Column(length = 50)
    private String password;

    @NotNull
    @Column(length = 13)
    private String phone;

    @NotNull
    @Column(length = 8)
    private String name;

    @Column
    private String role;

    @Builder
    public User(String nickname, String email, String password, String phone, String name) {
        this.nickname = nickname;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.name = name;
        this.role = "USER";

    }

    public void updateUser(UserRequestDto userRequestDto) {
        //비번 암호화
        this.nickname = userRequestDto.getNickname();
        this.email = userRequestDto.getEmail();
        this.password = userRequestDto.getPassword();
        this.phone = userRequestDto.getPhone();
        this.name = userRequestDto.getName();
        this.role = "USER";
    }

    public void updatePw (String pw) {
        this.password= pw;
    }

}

package com.steady.steadyback.domain;

import com.steady.steadyback.dto.*;
import com.sun.istack.NotNull;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.Collections;

@Entity
@NoArgsConstructor
@Getter
@Table(name = "user")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column
    private String name;

    @NotNull
    @Column(length = 20)
    private String nickname;

    @NotNull
    @Column(unique = true, length = 50)
    private String email;

    @NotNull
    @Column(length = 255)
    private String password;

//    @NotNull
    @Column(length = 13)
    private String phone;

    @Column
    private String role;

    @Column
    private Long kakaoId;

    @Builder
    public User(String name,String nickname, String email, String password, String phone, Long kakaoId) {
        this.name = name;
        this.nickname = nickname;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.name = name;
        this.kakaoId = kakaoId;
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

    public void updatePw (UserFindPwRequestDto userFindPwRequestDto) {
        this.password= userFindPwRequestDto.getPassword();
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        return Collections.singletonList(new SimpleGrantedAuthority(this.role));
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public void update(UserUpdateRequestDto updateRequestDto){
        this.name = updateRequestDto.getName();
        this.nickname = updateRequestDto.getNickname();
        this.password = updateRequestDto.getPassword();
        this.phone = updateRequestDto.getPhone();
    }

}
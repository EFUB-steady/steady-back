package com.steady.steadyback.domain;

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

    @Builder
    public User(Long id, String nickname, String email, String password, String phone, String name) {
        this.id = id;
        this.nickname = nickname;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.name = name;
    }

}

package com.steady.steadyback.domain;

import com.sun.istack.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Table(name = "refresh_token")
@Entity
@NoArgsConstructor
@Getter
public class RefreshToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="refresh_token_id")
    private Long id;

    @NotNull
    @Column
    private String refreshToken;

    @Column(length = 50)
    @NotNull
    private String userEmail;

    @Builder
    public RefreshToken(String refreshToken, String userEmail){
        this.refreshToken = refreshToken;
        this.userEmail = userEmail;
    }

}

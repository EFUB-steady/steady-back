package com.steady.steadyback.domain;

import com.sun.istack.NotNull;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Table(name = "alarm")
public class Alarm {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @NotNull
    @Column(name = "read_flag")
    private Boolean readFlag;

    @NotNull
    @Column(length = 100)
    private String message;

    @NotNull
    @Column
    private Integer type;

    @Builder
    public Alarm(User user, Boolean readFlag, String message, Integer type) {
        this.user = user;
        this.readFlag = readFlag;
        this.message = message;
        this.type = type;
    }
}

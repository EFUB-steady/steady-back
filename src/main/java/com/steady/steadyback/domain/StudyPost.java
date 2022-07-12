package com.steady.steadyback.domain;

import com.sun.istack.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter
@Table(name = "study_post")
public class StudyPost {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @NotNull
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @NotNull
    @JoinColumn(name = "study_id")
    private Study study;

    @Column(length = 100)
    private String link;

    @Builder
    public StudyPost(Long id, User user, Study study, String link) {
        this.id = id;
        this.user = user;
        this.study = study;
        this.link = link;
    }
}

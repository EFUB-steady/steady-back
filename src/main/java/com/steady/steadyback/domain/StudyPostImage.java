package com.steady.steadyback.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter
@Table(name= "study_post_image")
public class StudyPostImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name= "study_post_id")
    private StudyPost studyPost;

    @Column(length = 100)
    private String image;

    @Builder
    public StudyPostImage (Long id, StudyPost studyPost, String image){
        this.id= id;
        this.studyPost=studyPost;
        this.image= image;
    }
}

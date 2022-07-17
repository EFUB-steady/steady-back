package com.steady.steadyback.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface StudyPostRepository extends JpaRepository<StudyPost, Long> {
    public StudyPost findByUserAndStudyAndDate(User user, Study study, LocalDate date);
}

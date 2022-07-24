package com.steady.steadyback.domain;

import com.steady.steadyback.dto.StudyPostImageResponseDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface StudyPostRepository extends JpaRepository<StudyPost, Long> {
    public List<StudyPost> findAllByStudyId(Long studyId);
    public List<StudyPost> findByUserAndDate(User user, LocalDate date);
    public List<StudyPost> findByStudyAndDate(Study study, LocalDate date);
    public StudyPost findByUserAndStudyAndDate(User user, Study study, LocalDate date);
}

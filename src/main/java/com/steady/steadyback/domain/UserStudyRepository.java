package com.steady.steadyback.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import java.util.List;

@Repository
public interface UserStudyRepository extends JpaRepository<UserStudy, UserStudyID> {
    public Optional<UserStudy> findByUserAndStudy(User user, Study study);
    public List<UserStudy> findByUser(User user);
    public List<UserStudy> findByStudy(Study study);
    public Boolean existsByUserAndStudy(User user, Study study);
    public Boolean existsByUserAndStudyAndLeaderIsTrue(User user, Study study);

    @Modifying
    @Query("update UserStudy u set u.lastFine = :lastFine , u.nowFine = 0  where u.user.id=:userId and u.study.id=:studyId")
    public void updateLastFine(@Param("lastFine") Integer lastFine, @Param("userId") Long userId, @Param("studyId") Long studyId);

    @Modifying
    @Query("update UserStudy u set u.score = :score where u.user.id=:userId and u.study.id=:studyId")
    public void updateScore(@Param("userId") Long userId, @Param("studyId") Long studyId, @Param("score") Integer score);

}

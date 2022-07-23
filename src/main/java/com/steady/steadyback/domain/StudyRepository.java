package com.steady.steadyback.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface StudyRepository extends JpaRepository<Study, Long> {
    public Study findByUuid(String uuid);
    @Modifying
    @Query("update Study s set s.peopleCount = :peopleCount where s.id = :studyId")
    public void updatePeopleCount(@Param("peopleCount") Integer peopleCount, @Param("studyId") Long studyId);
}

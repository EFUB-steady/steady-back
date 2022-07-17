package com.steady.steadyback.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserStudyRepository extends JpaRepository<UserStudy, UserStudyID> {
    public UserStudy findByUserAndStudy(User user, Study study);
    public List<UserStudy> findByUser(User user);
}

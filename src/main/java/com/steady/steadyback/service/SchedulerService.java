package com.steady.steadyback.service;

import com.steady.steadyback.domain.UserStudyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SchedulerService {

    private final UserStudyRepository userStudyRepository;

    @Transactional
    @Scheduled(cron = "0 0 0 ? * MON")
    public void calculateFine(){
        //lastFine update, nowFine 0으로 초기화
        userStudyRepository.findAll()
                .stream()
                .forEach(userStudy -> userStudyRepository.updateLastFine(userStudy.refreshNowFineAndGetLastFine(), userStudy.getUser().getId(), userStudy.getStudy().getId()));
    }
}

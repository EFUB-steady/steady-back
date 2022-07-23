package com.steady.steadyback.dto;

import com.steady.steadyback.domain.Study;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@NoArgsConstructor
public class StudyRequestDto {
    private String name;
    private String description;
    private String uuid;
    private Boolean mon;
    private Boolean tue;
    private Boolean wed;
    private Boolean thu;
    private Boolean fri;
    private Boolean sat;
    private Boolean sun;
    private String account;
    private Integer hour;
    private Integer minute;
    private Integer money;
    private Integer lateMoney;
    private Integer peopleCount;

    @Builder
    public StudyRequestDto(String name, String description, Boolean mon, Boolean tue, Boolean wed, Boolean thu, Boolean fri, Boolean sat, Boolean sun,
                           String account, Integer hour, Integer minute, Integer money, Integer lateMoney) {
        this.name = name;
        this.description = description;
        this.mon = mon;
        this.tue = tue;
        this.wed = wed;
        this.thu = thu;
        this.fri = fri;
        this.sat = sat;
        this.sun = sun;
        this.account = account;
        this.hour = hour;
        this.minute = minute;
        this.money = money;
        this.lateMoney = lateMoney;
    }

    public StudyRequestDto(Study study) {
        this.name = study.getName();
        this.description = study.getDescription();
        this.mon = study.getMon();
        this.tue = study.getTue();
        this.wed = study.getWed();
        this.thu = study.getThu();
        this.fri = study.getFri();
        this.sat = study.getSat();
        this.sun = study.getSun();
        this.account = study.getAccount();
        this.hour = study.getHour();
        this.minute = study.getMinute();
        this.money = study.getMoney();
        this.lateMoney = study.getLateMoney();
    }

    public void addUUID(UUID uuid) {
        this.uuid = uuid.toString();
    }

    public Study toEntity() {
        return Study.builder()
                .name(name)
                .uuid(uuid)
                .description(description)
                .mon(mon)
                .tue(tue)
                .wed(wed)
                .thu(thu)
                .fri(fri)
                .sat(sat)
                .sun(sun)
                .account(account)
                .hour(hour)
                .minute(minute)
                .money(money)
                .lateMoney(lateMoney)
                .peopleCount(peopleCount)
                .build();
    }
}

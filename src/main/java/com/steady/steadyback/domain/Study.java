package com.steady.steadyback.domain;

import com.steady.steadyback.dto.StudyGetResponseDto;
import com.steady.steadyback.dto.StudyRequestDto;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter
@DynamicInsert
@Table(name = "study")
public class Study {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(length = 20)
    private String name;

    @NotNull
    @Column(length = 200)
    private String uuid;

    @Column(length = 100)
    private String description;

    @NotNull
    @Column
    private Boolean mon;

    @NotNull
    @Column
    private Boolean tue;

    @NotNull
    @Column
    private Boolean wed;

    @NotNull
    @Column
    private Boolean thu;

    @NotNull
    @Column
    private Boolean fri;

    @NotNull
    @Column
    private Boolean sat;

    @NotNull
    @Column
    private Boolean sun;

    @Column(length = 20)
    private String account;

    @NotNull
    @Column
    private Integer hour;

    @NotNull
    @Column
    private Integer minute;

    @NotNull
    @Column
    private Integer money;

    @NotNull
    @Column(name = "latemoney")
    private Integer lateMoney;

    @NotNull
    @Column(name = "people_count")
    private Integer peopleCount;

    @Builder
    public Study(Long id, String name, String uuid, String description, Boolean mon, Boolean tue, Boolean wed, Boolean thu, Boolean fri, Boolean sat, Boolean sun, String account, Integer hour, Integer minute, Integer money, Integer lateMoney, Integer peopleCount) {
        this.id = id;
        this.name = name;
        this.uuid = uuid;
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
        this.peopleCount = peopleCount;
    }

    public void updateDescription(StudyRequestDto studyRequestDto) {
        this.name = studyRequestDto.getName();
        this.description = studyRequestDto.getDescription();
    }

    public void updateRule(StudyRequestDto studyRequestDto) {
        this.mon = studyRequestDto.getMon();
        this.tue = studyRequestDto.getTue();
        this.wed = studyRequestDto.getWed();
        this.thu = studyRequestDto.getThu();
        this.fri = studyRequestDto.getFri();
        this.sat = studyRequestDto.getSat();
        this.sun = studyRequestDto.getSun();
        this.account = studyRequestDto.getAccount();
        this.hour = studyRequestDto.getHour();
        this.minute = studyRequestDto.getMinute();
        this.money = studyRequestDto.getMoney();
        this.lateMoney = studyRequestDto.getLateMoney();
    }
}

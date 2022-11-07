package com.steady.steadyback.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.steady.steadyback.domain.*;
import com.steady.steadyback.dto.LoginRequestDto;
import com.steady.steadyback.dto.LoginResponseDto;
import com.steady.steadyback.dto.SignupRequestDto;
import com.steady.steadyback.dto.StudyRequestDto;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class UserStudyControllerTest {


    User user= new User();
    StudyRequestDto studyRequestDto= new StudyRequestDto();

    @Autowired
    StudyRepository studyRepository;
    String accesstocken;

    @Autowired
    private MockMvc mvc;

    @BeforeAll
    public void setup () {

        studyRequestDto = StudyRequestDto.builder()
                .name("name2")
                .summary("summary")
                .description("설명")
                .mon(true)
                .tue(true)
                .wed(false)
                .thu(true)
                .fri(false)
                .sat(false)
                .sun(false)
                .accountBank("신한")
                .account("123-123")
                .accountName("이름")
                .hour(12)
                .minute(30)
                .money(2000)
                .lateMoney(1000)
                .build();

        studyRequestDto.addUUID(UUID.randomUUID());

        accesstocken = studyRequestDto.getUuid();

    }

    @Test
    @Transactional
    @WithUserDetails(value = "test5@efub.com", userDetailsServiceBeanName = "userService")
    public void createStudy() throws Exception {
        mvc.perform(post("/studies")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(studyRequestDto)))
                .andExpect(status().is2xxSuccessful())
                .andDo(print());
    }

    @Test//get
    @WithUserDetails(value = "test5@efub.com", userDetailsServiceBeanName = "userService")
    public void joinStudy() throws Exception{
        mvc.perform(get("studies/join/"+accesstocken))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test//delete
    public void leaveStudy() throws Exception{
        Long id= user.getId();
        Study study=studyRepository.findByUuid(accesstocken);
        Long studyId= study.getId();
        mvc.perform(delete("/studies/" + id+"/leave/"+studyId)) //.with(user(user)))
                .andExpect(status().is2xxSuccessful())
                .andDo(print());
    }

    @Test
    public void getRankingList() throws Exception{
        mvc.perform(get("studies/ranking/1"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @WithUserDetails(value = "test5@efub.com", userDetailsServiceBeanName = "userService")
    public void getFineList() throws Exception {

        mvc.perform(get("studies/fines"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @WithUserDetails(value = "test5@efub.com", userDetailsServiceBeanName = "userService")
    public void getStudyListByUser() throws Exception{
        mvc.perform(get("studies/my-study"))
                .andExpect(status().isOk())
                .andDo(print());
    }

}
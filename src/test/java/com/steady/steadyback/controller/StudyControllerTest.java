package com.steady.steadyback.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.steady.steadyback.domain.Study;
import com.steady.steadyback.domain.StudyRepository;
import com.steady.steadyback.dto.StudyRequestDto;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
class StudyControllerTest {

    @Autowired
    private StudyRepository studyRepository;

    @Autowired
    private MockMvc mvc;

    private static StudyRequestDto studyRequestDto = new StudyRequestDto();

    @BeforeAll
    public static void setStudy() {
        studyRequestDto = StudyRequestDto.builder()
                .name("name")
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
    }

    @Test
    @WithMockUser(roles = "USER")
    public void 스터디id로조회하기() throws Exception {
        mvc.perform(get("/studies/53"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    public void 로그인안하고조회() throws Exception {
        mvc.perform(get("/studies/53"))
                .andExpect(status().isUnauthorized())
                .andDo(print());
    }

    @Test
    @WithMockUser(roles = "USER")
    public void 스터디전체조회() throws Exception {
        mvc.perform(get("/studies"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    public void 로그인안하고전체조회() throws Exception {
        mvc.perform(get("/studies"))
                .andExpect(status().isUnauthorized())
                .andDo(print());
    }



    @Test
    @Transactional
    @WithUserDetails(value = "test5@efub.com", userDetailsServiceBeanName = "userService")
    public void 스터디생성() throws Exception {
        mvc.perform(post("/studies")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(studyRequestDto)))
                .andExpect(status().is2xxSuccessful())
                .andDo(print());
    }



    @Test
    @WithUserDetails(value = "test5@efub.com", userDetailsServiceBeanName = "userService")
    @Transactional
    public void 스터디삭제() throws Exception {
        MvcResult mvcResult = mvc.perform(post("/studies")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(studyRequestDto)))
                .andReturn();


        JSONObject jsonObject = new JSONObject(mvcResult.getResponse().getContentAsString());
        String id = jsonObject.get("studyId").toString();

        mvc.perform(delete("/studies/" + id)) //.with(user(user)))
                .andExpect(status().is2xxSuccessful())
                .andDo(print());
    }

    @Test
    @WithUserDetails(value = "test5@efub.com", userDetailsServiceBeanName = "userService")
    @Transactional
    public void 스터디설명수정() throws Exception {
        MvcResult mvcResult = mvc.perform(post("/studies") //.with(user(user))
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(studyRequestDto)))
                .andReturn();

        JSONObject jsonObject = new JSONObject(mvcResult.getResponse().getContentAsString());
        String id = jsonObject.get("studyId").toString();

        studyRequestDto.setSummary("modified summary");
        studyRequestDto.setDescription("modified description");

        mvc.perform(put("/studies/" + id + "/descriptions")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(studyRequestDto)))
                .andExpect(status().is2xxSuccessful())
                .andDo(print());

    }


    @Test
    @WithMockUser(roles = "USER")
    @Transactional
    public void 스터디설명수정_권한없음() throws Exception {
        Study study = studyRepository.save(studyRequestDto.toEntity());

        String id = study.getId().toString();

        studyRequestDto.setName("modified name");
        studyRequestDto.setSummary("modified summary");
        studyRequestDto.setDescription("modified description");

        mvc.perform(put("/studies/" + id + "/descriptions")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(studyRequestDto)))
                .andExpect(status().isUnauthorized())
                .andDo(print());

    }

    @Test
    @WithUserDetails(value = "test5@efub.com", userDetailsServiceBeanName = "userService")
    @Transactional
    public void 스터디규칙수정() throws Exception {
        MvcResult mvcResult = mvc.perform(post("/studies") //.with(user(user))
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(studyRequestDto)))
                .andReturn();

        JSONObject jsonObject = new JSONObject(mvcResult.getResponse().getContentAsString());
        String id = jsonObject.get("studyId").toString();

        studyRequestDto.setHour(13);
        studyRequestDto.setMinute(20);
        studyRequestDto.setAccount("234-234");
        studyRequestDto.setFri(!studyRequestDto.getFri());
        studyRequestDto.setMoney(10000);

        mvc.perform(put("/studies/" + id + "/rules")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(studyRequestDto)))
                .andExpect(status().is2xxSuccessful())
                .andDo(print());

    }
}
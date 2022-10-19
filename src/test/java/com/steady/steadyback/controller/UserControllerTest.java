package com.steady.steadyback.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.steady.steadyback.domain.User;
import com.steady.steadyback.domain.UserRepository;
import com.steady.steadyback.dto.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {
    
    @Autowired
    private MockMvc mvc;

    
    private static SignupRequestDto signupRequestDto= new SignupRequestDto();
    private static LoginRequestDto loginRequestDto=new LoginRequestDto();

    private static UserUpdateRequestDto updateRequestDto= new UserUpdateRequestDto();


    @Autowired
    UserRepository userRepository;
    UserController userController;
    User user;

    User changeuser;

    @BeforeAll
    public void setUp() {
        user = User.builder()
                .name("이름")
                .email("kim@email.com")
                .nickname("안녕")
                .password("1234")
                .phone("010-3333-2222")
                .build();
        
        signupRequestDto = SignupRequestDto.builder()
                .user(user)
                .build();
        changeuser= User.builder()
                .name("이름바꿔야지")
                .nickname("닉네임바꿔야지")
                .password("비번도바꿔야지")
                .phone("번호도바꿔야지")
                .build();
        
    }
    @Test
    public void getUserTest() throws Exception {
        given(this.userController.getUserById(1L)).willReturn(new UserResponseDto(this.user));

    }

    @Test//post
    @Transactional
    public void signup() throws Exception{
        mvc.perform(post("/users/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(signupRequestDto)))
                .andExpect(status().is2xxSuccessful())
                .andDo(print());
        
    }



    @Test
    @Transactional
    public void login() throws Exception{

        loginRequestDto= LoginRequestDto.builder()
                .user(user)
                .build();

        mvc.perform(post("/users/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(loginRequestDto)))
                .andExpect(status().is2xxSuccessful())
                .andDo(print());

    }
    @Test
    @Transactional
    public void deleteUser() throws Exception {
        Long id= user.getId();

        mvc.perform(delete("/users/" + id)) //.with(user(user)))
                .andExpect(status().is2xxSuccessful())
                .andDo(print());
    }

    @Test
    public void getUserById() throws Exception{
        Long id= user.getId();
        mvc.perform(get("/users/"+id))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @Transactional
    public void getUserIdByNameAndPhone() throws Exception{


        Map<String,String> map= new HashMap<>();
        map.put("name",user.getName());
        map.put("phone", user.getPhone());

        String content= new ObjectMapper().writeValueAsString(map);
        mvc.perform(get("/users/findId")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content))
                .andExpect(status().is2xxSuccessful())
                .andDo(print());
    }

    @Test
    @Transactional
    public void findPwPOST() throws Exception{

        Map<String, String> map= new HashMap<>();
        map.put("email",user.getEmail());

        mvc.perform(post("/users/findPw")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(map)))
                .andExpect(status().is2xxSuccessful())
                .andDo(print());
    }

    @Test
    public void getUserList() throws Exception{
        mvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @WithUserDetails(value = "kim@email.com", userDetailsServiceBeanName = "userService")
    @Transactional
    public void updateUser() throws Exception{
        Long id=user.getId();
        updateRequestDto= UserUpdateRequestDto.builder()
                .user(changeuser)
                .build();

        mvc.perform(put("/users/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(updateRequestDto)))
                .andExpect(status().is2xxSuccessful())
                .andDo(print());

    }
}
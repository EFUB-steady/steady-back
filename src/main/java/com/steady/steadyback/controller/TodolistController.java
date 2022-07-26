package com.steady.steadyback.controller;

import com.steady.steadyback.domain.User;
import com.steady.steadyback.dto.TodolistResponseDto;
import com.steady.steadyback.service.TodolistService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/todos")
public class TodolistController {
    private final TodolistService todolistService;

    @GetMapping()
    public List<TodolistResponseDto> getTodoList(@AuthenticationPrincipal User user){
        return todolistService.findTodolist(user);
    }
}

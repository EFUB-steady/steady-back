package com.steady.steadyback.controller;

import com.steady.steadyback.dto.TodolistResponseDto;
import com.steady.steadyback.service.TodolistService;
import lombok.RequiredArgsConstructor;
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

    @GetMapping("/{userId}")
    public List<TodolistResponseDto> getTodoList(@PathVariable Long userId){
        return todolistService.findTodolist(userId);
    }
}

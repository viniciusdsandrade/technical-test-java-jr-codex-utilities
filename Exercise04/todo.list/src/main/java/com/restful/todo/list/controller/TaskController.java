package com.restful.todo.list.controller;

import com.restful.todo.list.service.TaskService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/tasks")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Tag(name = "Carro Controller", description = "Controller para gerenciamento de carros")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }
}

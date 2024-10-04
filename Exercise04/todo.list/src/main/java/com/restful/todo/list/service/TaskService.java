package com.restful.todo.list.service;

import com.restful.todo.list.dto.TaskCreateDTO;
import com.restful.todo.list.dto.TaskDetailDTO;
import com.restful.todo.list.dto.TaskUpdateDTO;
import com.restful.todo.list.entity.Task;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TaskService {

    @Transactional(rollbackOn = Exception.class)
    Task createTask(@Valid TaskCreateDTO taskCreateDTO);

    Task getTaskById(Long id);

    Page<TaskDetailDTO> getAllTasks(Pageable pageable);

    @Transactional(rollbackOn = Exception.class)
    Task updateTask(@Valid TaskUpdateDTO taskUpdateDTO);

    void deleteTask(Long id);
}

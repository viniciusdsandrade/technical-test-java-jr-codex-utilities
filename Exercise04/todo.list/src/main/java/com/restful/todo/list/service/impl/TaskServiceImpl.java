package com.restful.todo.list.service.impl;

import com.restful.todo.list.dto.TaskCreateDTO;
import com.restful.todo.list.dto.TaskDetailDTO;
import com.restful.todo.list.dto.TaskUpdateDTO;
import com.restful.todo.list.entity.Task;
import com.restful.todo.list.repository.TaskRepository;
import com.restful.todo.list.service.TaskService;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import static org.slf4j.LoggerFactory.getLogger;

@Service("taskService")
public class TaskServiceImpl implements TaskService {

    @Schema(description = "Logger para a classe CarroServiceImpl.")
    private static final Logger log = getLogger(TaskServiceImpl.class);

    private final TaskRepository taskRepository;

    public TaskServiceImpl(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Override
    @Transactional
    public Task createTask(@Valid TaskCreateDTO taskCreateDTO) {
        log.info("Criando nova tarefa com dados: {}", taskCreateDTO);

        Task task = new Task();
        task.setDescription(taskCreateDTO.description());
        task.setDone(taskCreateDTO.done());
        Task savedTask = taskRepository.save(task);
        log.info("Tarefa criada com sucesso: {}", savedTask);

        return savedTask;
    }

    @Override
    public Task getTaskById(Long id) {
        log.info("Buscando tarefa com ID: {}", id);
        return taskRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Tarefa não encontrada com ID: " + id));
    }

    @Override
    public Page<TaskDetailDTO> getAllTasks(Pageable pageable) {
        log.info("Buscando todas as tarefas.");
        Page<Task> tasks = taskRepository.findAll(pageable);
        log.info("Tarefas encontradas: {}", tasks.getTotalElements());
        return tasks.map(TaskDetailDTO::new);
    }

    @Override
    @Transactional
    public Task updateTask(@Valid TaskUpdateDTO taskUpdateDTO) {
        log.info("Atualizando tarefa com dados: {}", taskUpdateDTO);

        // Verificar se a tarefa existe
        Task task = taskRepository.findById(taskUpdateDTO.id())
                .orElseThrow(() -> new EntityNotFoundException("Tarefa não encontrada com ID: " + taskUpdateDTO.id()));
        log.info("Tarefa encontrada para atualização: {}", task);

        task.update(taskUpdateDTO);

        // Salvar a tarefa atualizada
        Task updatedTask = taskRepository.save(task);
        log.info("Tarefa atualizada com sucesso: {}", updatedTask);

        return updatedTask;
    }

    @Override
    @Transactional
    public void deleteTask(Long id) {
        log.info("Excluindo tarefa com ID: {}", id);
        if (!taskRepository.existsById(id)) {
            log.warn("Tarefa não encontrada com ID: {}", id);
            throw new EntityNotFoundException("Tarefa não encontrada com ID: " + id);
        }
        taskRepository.deleteById(id);
        log.info("Tarefa excluída com sucesso com ID: {}", id);
    }
}
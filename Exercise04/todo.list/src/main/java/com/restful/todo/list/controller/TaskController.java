package com.restful.todo.list.controller;

import com.restful.todo.list.dto.TaskCreateDTO;
import com.restful.todo.list.dto.TaskDetailDTO;
import com.restful.todo.list.dto.TaskUpdateDTO;
import com.restful.todo.list.entity.Task;
import com.restful.todo.list.service.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

import static org.springframework.data.domain.Sort.by;
import static org.springframework.http.ResponseEntity.*;

@RestController
@RequestMapping("/api/v1/task")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Tag(name = "Task Controller", description = "Controller para gerenciamento de tarefas da Todo List")
public class TaskController {

    @Schema(description = "Controller para gerenciamento de tarefas.")
    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    /**
     * Endpoint para cadastrar uma nova tarefa.
     *
     * @param taskCreateDTO    Dados da tarefa a ser cadastrada.
     * @param uriBuilder Builder para construir a URI de localização da tarefa criada.
     * @return ResponseEntity com o status 201 (Created) e os dados da tarefa criada.
     */
    @Transactional
    @PostMapping
    @Operation(summary = "Cadastrar uma nova tarefa")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Tarefa criada com sucesso."),
            @ApiResponse(responseCode = "400", description = "Dados de cadastro inválidos.")
    })
    public ResponseEntity<TaskCreateDTO> cadastrar(
            @RequestBody @Valid TaskCreateDTO taskCreateDTO,
            UriComponentsBuilder uriBuilder
    ) {
        Task task = taskService.createTask(taskCreateDTO);
        URI uri = uriBuilder.path("/api/v1/task/{id}").buildAndExpand(task.getId()).toUri();
        TaskCreateDTO createdTaskCreateDTO = new TaskCreateDTO(task);
        return created(uri).body(createdTaskCreateDTO);
    }

    /**
     * Endpoint para detalhar uma tarefa específica.
     *
     * @param id ID da tarefa a ser detalhada.
     * @return ResponseEntity com o status 200 (OK) e os dados detalhados da tarefa.
     */
    @GetMapping("/{id}")
    @Operation(summary = "Detalhar uma tarefa")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tarefa encontrada."),
            @ApiResponse(responseCode = "404", description = "Tarefa não encontrada.")
    })
    public ResponseEntity<TaskCreateDTO> detalhar(@PathVariable Long id) {
        Task task = taskService.getTaskById(id);
        TaskCreateDTO detalhado = new TaskCreateDTO(task);
        return ok(detalhado);
    }

    /**
     * Endpoint para listar todas as tarefas com paginação.
     *
     * @param page   Número da página (zero-based).
     * @param size   Tamanho da página.
     * @param limit  Limite de itens por página (opcional).
     * @param offset Offset inicial (opcional).
     * @param sort   Campo para ordenação.
     * @return ResponseEntity com o status 200 (OK) e a página de tarefas.
     */
    @GetMapping("/all")
    @Operation(summary = "Listar todas as tarefas", description = "Retorna uma lista paginada de todas as tarefas.")
    @ApiResponse(responseCode = "200", description = "Lista de tarefas.")
    public ResponseEntity<Page<TaskDetailDTO>> listarTodos(
            @RequestParam(value = "page", required = false, defaultValue = "0") int page,
            @RequestParam(value = "size", required = false, defaultValue = "10") int size,
            @RequestParam(value = "limit", required = false) Integer limit,
            @RequestParam(value = "offset", required = false) Integer offset,
            @RequestParam(value = "sort", required = false, defaultValue = "id") String sort
    ) {

        if (limit != null && offset != null) {
            page = offset / limit;
            size = limit;
        }

        Pageable paginacao = PageRequest.of(page, size, by(sort));
        Page<TaskDetailDTO> tarefas = taskService.getAllTasks(paginacao);
        return ok(tarefas);
    }

    /**
     * Endpoint para atualizar uma tarefa existente.
     *
     * @param taskCreateDTO Dados da tarefa a ser atualizada.
     * @return ResponseEntity com o status 200 (OK) e os dados atualizados da tarefa.
     */
    @Transactional
    @PatchMapping
    @Operation(summary = "Atualizar uma tarefa", description = "Atualiza parte ou todos os dados de uma tarefa.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tarefa atualizada com sucesso."),
            @ApiResponse(responseCode = "400", description = "Dados de atualização inválidos."),
            @ApiResponse(responseCode = "404", description = "Tarefa não encontrada.")
    })
    public ResponseEntity<TaskDetailDTO> atualizar(@RequestBody @Valid TaskUpdateDTO taskCreateDTO) {
        Task tarefaAtualizada = taskService.updateTask(taskCreateDTO);
        return ok(new TaskDetailDTO(tarefaAtualizada));
    }

    /**
     * Endpoint para excluir (desativar) uma tarefa.
     *
     * @param id ID da tarefa a ser excluída.
     * @return ResponseEntity com o status 204 (No Content).
     */
    @Transactional
    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir uma tarefa", description = "Exclui (desativa) uma tarefa existente.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Tarefa excluída com sucesso."),
            @ApiResponse(responseCode = "404", description = "Tarefa não encontrada.")
    })
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        taskService.deleteTask(id);
        return noContent().build();
    }
}
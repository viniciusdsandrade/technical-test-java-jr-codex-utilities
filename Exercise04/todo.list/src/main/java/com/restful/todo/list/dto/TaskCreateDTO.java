package com.restful.todo.list.dto;

import com.restful.todo.list.entity.Task;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "Detalhes da tarefa.")
public record TaskCreateDTO(

        @Schema(description = "Descrição da tarefa.")
        @NotBlank(message = "A descrição da tarefa é obrigatória.")
        String description,

        @Schema(description = "Indica se a tarefa está concluída.")
        boolean done
) {
    public TaskCreateDTO(Task task) {
        this(
                task.getDescription(),
                task.isDone()
        );
    }
}

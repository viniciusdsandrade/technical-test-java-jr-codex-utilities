package com.restful.todo.list.dto;

import com.restful.todo.list.entity.Task;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Detalhes da tarefa.")
public record TaskDetailDTO(

        @Schema(description = "Descrição da tarefa.")
        String description,

        @Schema(description = "Indica se a tarefa está concluída.")
        Boolean done
) {

    public TaskDetailDTO(Task task) {
        this(
                task.getDescription(),
                task.isDone()
        );
    }
}

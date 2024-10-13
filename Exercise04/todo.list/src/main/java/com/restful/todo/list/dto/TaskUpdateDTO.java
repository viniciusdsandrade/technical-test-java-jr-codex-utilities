package com.restful.todo.list.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Schema(description = "Dados para atualizar uma tarefa existente.")
public record TaskUpdateDTO(

        @Schema(description = "ID da tarefa a ser atualizada.", example = "1")
        @NotNull(message = "ID é obrigatório.")
        Long id,

        @Size(min = 3, max = 100, message = "A descrição deve ter entre 3 e 100 caracteres.")
        @Schema(description = "Descrição da tarefa.", example = "Comprar leite")
        String description,

        @Schema(description = "Indica se a tarefa está concluída.", example = "true")
        Boolean done
) {
}
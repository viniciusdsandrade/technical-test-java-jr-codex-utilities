package com.restful.todo.list.dto;

public record TaskDTO(
        Long id,
        String description,
        boolean done
) {
}

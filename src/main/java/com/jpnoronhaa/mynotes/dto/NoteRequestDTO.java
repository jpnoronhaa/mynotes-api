package com.jpnoronhaa.mynotes.dto;
import jakarta.validation.constraints.NotBlank;

public record NoteRequestDTO(
        @NotBlank(message = "Content cannot be empty")
        String markdownContent
) {}

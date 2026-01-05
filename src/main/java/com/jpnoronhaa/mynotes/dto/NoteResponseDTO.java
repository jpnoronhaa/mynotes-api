package com.jpnoronhaa.mynotes.dto;

public record NoteResponseDTO(
        Long id,
        String markdownContent,
        java.time.LocalDateTime createdAt
) {}

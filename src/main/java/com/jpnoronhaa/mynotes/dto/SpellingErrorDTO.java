package com.jpnoronhaa.mynotes.dto;

import java.util.List;

public record SpellingErrorDTO(
        String message,
        String match,
        List<String> suggestions,
        int startPosition,
        int endPosition
) {
}

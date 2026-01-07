package com.jpnoronhaa.mynotes.controller;

import com.jpnoronhaa.mynotes.dto.SpellingErrorDTO;
import com.jpnoronhaa.mynotes.service.SpellCheckService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/spell-checker")
@RequiredArgsConstructor
@Tag(name = "SpellChecker", description = "Extra functionality of spellchecker")
public class SpellCheckController {
    private final SpellCheckService spellCheckService;

    @Operation(summary = "Spell checker", description = "Check the spelling of a plain text (that is not Markdown). Lang accepts 'pt-br' or 'en' (Default: en).")
    @PostMapping(consumes = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<List<SpellingErrorDTO>> spellChecker(
            @RequestBody String content,
            @RequestParam(defaultValue = "en", required = false) String lang
    ) {
        List<SpellingErrorDTO> errors = spellCheckService.checkMarkdownSpelling(content, lang);
        return ResponseEntity.ok(errors);
    }
}

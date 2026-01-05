package com.jpnoronhaa.mynotes.controller;

import com.jpnoronhaa.mynotes.dto.NoteRequestDTO;
import com.jpnoronhaa.mynotes.dto.NoteResponseDTO;
import com.jpnoronhaa.mynotes.service.NoteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notes")
@RequiredArgsConstructor
@Tag(name = "Notes", description = "Note management in Markdown")
public class NoteController {

    private final NoteService noteService;

    @Operation(summary = "Create new note", description = "Receives a markdown and saves it to the database.")
    @PostMapping(consumes = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<NoteResponseDTO> createFromText(@RequestBody String content) {
        NoteRequestDTO requestDTO = new NoteRequestDTO(content);
        NoteResponseDTO response = noteService.createNote(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(summary = "List notes", description = "Returns all saved notes")
    @GetMapping
    public ResponseEntity<List<NoteResponseDTO>> listAll() {
        return ResponseEntity.ok(noteService.listAllNotes());
    }

    @Operation(summary = "Search note by id")
    @GetMapping("/{id}")
    public ResponseEntity<NoteResponseDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(noteService.findById(id));
    }

    @Operation(summary = "Update note", description = "Update a note and saves it to the database.")
    @PutMapping("/{id}")
    public ResponseEntity<NoteResponseDTO> update(@PathVariable Long id, @RequestBody @Valid NoteRequestDTO request) {
        return ResponseEntity.ok(noteService.updateNote(id, request));
    }

    @Operation(summary = "Delete note")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        noteService.deleteNote(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Render HTML", description = "Converts the Markdown from the note and returns pure HTML.")
    @GetMapping(value = "/{id}/html", produces = MediaType.TEXT_HTML_VALUE)
    public ResponseEntity<String> getNoteHtml(@PathVariable Long id) {
        String htmlContent = noteService.renderHtml(id);
        return ResponseEntity.ok(htmlContent);
    }
}
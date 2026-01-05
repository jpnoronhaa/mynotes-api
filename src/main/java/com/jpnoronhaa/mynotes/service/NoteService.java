package com.jpnoronhaa.mynotes.service;

import com.jpnoronhaa.mynotes.domain.Note;
import com.jpnoronhaa.mynotes.dto.NoteRequestDTO;
import com.jpnoronhaa.mynotes.dto.NoteResponseDTO;
import com.jpnoronhaa.mynotes.repository.NoteRepository;
import lombok.RequiredArgsConstructor;
import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NoteService {

    private final NoteRepository noteRepository;
    private final Parser parser = Parser.builder().build();
    private final HtmlRenderer renderer = HtmlRenderer.builder().build();

    @Transactional
    public NoteResponseDTO createNote(NoteRequestDTO request) {
        Note note = Note.builder()
                .content(request.markdownContent())
                .build();

        Note savedNote = noteRepository.save(note);

        return toDTO(savedNote);
    }

    @Transactional(readOnly = true)
    public List<NoteResponseDTO> listAllNotes() {
        return noteRepository.findAll().stream()
                .map(this::toDTO)
                .toList();
    }

    @Transactional(readOnly = true)
    public NoteResponseDTO findById(Long id) {
        Note note = findNoteOrThrow(id);
        return toDTO(note);
    }

    @Transactional
    public void deleteNote(Long id) {
        if (!noteRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Note not found");
        }
        noteRepository.deleteById(id);
    }

    @Transactional
    public NoteResponseDTO updateNote(Long id, NoteRequestDTO request) {
        Note note = findNoteOrThrow(id);
        note.setContent(request.markdownContent());
        return toDTO(note);
    }

    @Transactional(readOnly = true)
    public String renderHtml(Long id) {
        Note note = findNoteOrThrow(id);
        Node document = parser.parse(note.getContent());
        return renderer.render(document);
    }

    private Note findNoteOrThrow(Long id) {
        return noteRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Note not found"));
    }

    private NoteResponseDTO toDTO(Note note) {
        return new NoteResponseDTO(
                note.getId(),
                note.getContent(),
                note.getCreatedAt()
        );
    }
}
package com.jpnoronhaa.mynotes.repository;

import com.jpnoronhaa.mynotes.domain.Note;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NoteRepository extends JpaRepository<Note, Long> {
}

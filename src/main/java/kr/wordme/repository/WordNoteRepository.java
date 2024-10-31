package kr.wordme.repository;

import kr.wordme.model.entity.WordNote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface WordNoteRepository extends JpaRepository<WordNote, UUID> {
}

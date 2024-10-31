package kr.wordme.repository;

import kr.wordme.model.entity.WordNoteWord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface WordNoteWordRepository extends JpaRepository<WordNoteWord, UUID> {
}

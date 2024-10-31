package kr.wordme.repository;

import kr.wordme.model.entity.WordNoteWordMeaning;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WordNoteWordMeaningRepository extends JpaRepository<WordNoteWordMeaning, Integer> {

}

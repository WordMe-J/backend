package kr.wordme.repository;

import kr.wordme.model.entity.WordCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WordCategoryRepository extends JpaRepository<WordCategory, Long> {
}

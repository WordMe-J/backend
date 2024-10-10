package kr.wordme.repository;

import org.springframework.stereotype.Repository;

@Repository
public interface CardSetRepository extends JpaRepository<CardSet, Long> {

    @Query(value = "SELECT * FROM card_set WHERE category = :category ORDER BY created_at LIMIT :startIndex, :endIndex", nativeQuery = true)
    List<CardSet> findByCategoryWithPagination(
            @Param("category") String category,
            @Param("startIndex") int startIndex,
            @Param("endIndex") int endIndex);
}
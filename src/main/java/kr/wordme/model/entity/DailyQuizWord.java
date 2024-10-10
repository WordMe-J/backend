package kr.wordme.model.entity;

import jakarta.persistence.*;

import java.sql.Timestamp;

@Entity
@Table(name = "daily_quiz_word")
public class DailyQuizWord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "word_id", nullable = false)
    private Word word;

    @ManyToOne
    @JoinColumn(name = "word_category_id", nullable = false)
    private WordCategory wordCategory;

    @Column(name = "quiz_date")
    private Timestamp quizDate;

    // Getters and Setters
}

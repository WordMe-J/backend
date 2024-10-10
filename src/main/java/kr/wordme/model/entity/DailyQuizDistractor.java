package kr.wordme.model.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "daily_quiz_distractor")
public class DailyQuizDistractor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "daily_quiz_word_id", nullable = false)
    private DailyQuizWord dailyQuizWord;

    @Column(name = "distractor_text", length = 255)
    private String distractorText;

    @Column(name = "distractor_number")
    private Integer distractorNumber;

    @Column(name = "answer_sign")
    private Boolean answerSign;

    // Getters and Setters
}

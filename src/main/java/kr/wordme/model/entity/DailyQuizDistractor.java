package kr.wordme.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "daily_quiz_distractor")
@NoArgsConstructor
@Getter
public class DailyQuizDistractor {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "daily_quiz_word_id")
	private DailyQuizWord dailyQuizWord;

	@Column(name = "distractor_text")
	private String distractorText;

	@Column(name = "distractor_number")
	private Integer distractorNumber;

	@Column(name = "answer_sign", columnDefinition = "TINYINT(1)", nullable = false)
	private Boolean answerSign;
}

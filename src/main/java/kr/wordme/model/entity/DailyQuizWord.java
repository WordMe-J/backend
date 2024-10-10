package kr.wordme.model.entity;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "daily_quiz_word")
@NoArgsConstructor
@Getter
public class DailyQuizWord {

	@Id
	private UUID id;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "word_id")
	private Word word;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "word_category_id")
	private WordCategory wordCategory;

	@Column(name = "quiz_date")
	private Timestamp quizDate;

	@OneToMany(mappedBy = "dailyQuizWord", fetch = FetchType.LAZY)
	private List<DailyQuizDistractor> dailyQuizDistractors;
}

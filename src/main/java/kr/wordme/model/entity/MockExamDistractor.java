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
@Table(name = "mock_exam_distractor")
@NoArgsConstructor
@Getter
public class MockExamDistractor {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "mock_exam_question_id")
	private MockExamQuestion mockExamQuestion;

	@Column(name = "distractor_text", columnDefinition = "TEXT")
	private String distractorText;

	@Column(name = "distractor_number")
	private int distractorNumber;

	@Column(name = "answer_sign", columnDefinition = "TINYINT(1)")
	private Boolean answerSign;
}
